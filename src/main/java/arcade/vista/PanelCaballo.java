package arcade.vista;

import arcade.juegos.caballo.ControladorCaballo;
import arcade.juegos.caballo.ResultadoCaballo;
import arcade.persistencia.Partida;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PanelCaballo extends JPanel {

    private final int N;
    private final JButton[][] celdas;
    private final boolean[][] visitadas;
    private final JPanel panelTablero;
    private final JLabel mensajeLabel;
    private final JLabel contadorLabel;

    private int filaActual = -1, colActual = -1;
    private int movimientos = 0;

    private boolean resolviendo = false;
    private JButton btnReiniciar;
    private JButton btnResolverAuto;
    private final Runnable volverAccion;




    public PanelCaballo(int N, Runnable volverAccion) {

        this.N = N;
        this.celdas = new JButton[N][N];
        this.visitadas = new boolean[N][N];
        this.volverAccion = volverAccion;

        setLayout(new BorderLayout());

        mensajeLabel = new JLabel("Selecciona una casilla para comenzar", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mensajeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mensajeLabel, BorderLayout.NORTH);

        panelTablero = new JPanel(new GridLayout(N, N));
        panelTablero.setPreferredSize(new Dimension(500, 500));
        panelTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        inicializarTablero();
        add(panelTablero, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());

        JButton btnVolver = new JButton("Volver al menú");
        btnVolver.addActionListener(e -> volverAccion.run());
        panelInferior.add(btnVolver);


        btnReiniciar = new JButton("Reiniciar recorrido");
        btnReiniciar.addActionListener(e -> reiniciarRecorrido());
        panelInferior.add(btnReiniciar);

        btnResolverAuto = new JButton("Resolver automáticamente");
        btnResolverAuto.addActionListener(e -> resolverAutomaticamente());
        panelInferior.add(btnResolverAuto);

        contadorLabel = new JLabel("Movimientos: 0");
        panelInferior.add(contadorLabel);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void inicializarTablero() {
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                JButton celda = new JButton();
                celda.setFont(new Font("SansSerif", Font.BOLD, 16));
                celda.setBackground((fila + col) % 2 == 0 ? Color.WHITE : new Color(200, 200, 200));
                final int f = fila;
                final int c = col;
                celda.addActionListener(e -> manejarClick(f, c));
                celdas[fila][col] = celda;
                panelTablero.add(celda);
            }
        }
    }

    private void manejarClick(int fila, int col) {

        if (resolviendo) return;

        if (filaActual == -1 && colActual == -1) {
            colocarCaballo(fila, col);
            mensajeLabel.setText("Selecciona el siguiente movimiento válido.");
        } else {
            if (esMovimientoValido(fila, col) && !visitadas[fila][col]) {
                colocarCaballo(fila, col);
                if (movimientos == N * N) {
                    mensajeLabel.setText("¡Recorrido completo!");
                    colorearCasillasFinal(true);
                    guardarPartida(true);
                    bloquearTablero();
                } else if (obtenerMovimientosValidos(fila, col).isEmpty()) {
                    mensajeLabel.setText("¡No hay más movimientos posibles!");
                    colorearCasillasFinal(false);
                    guardarPartida(false);
                    bloquearTablero();
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Movimiento inválido. Solo podés moverte como un caballo.",
                        "Movimiento no permitido",
                        JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void colocarCaballo(int fila, int col) {
        if (filaActual != -1 && colActual != -1) {
            celdas[filaActual][colActual].setText(String.valueOf(movimientos));
            celdas[filaActual][colActual].setForeground(Color.BLACK);
        }

        filaActual = fila;
        colActual = col;
        movimientos++;
        visitadas[fila][col] = true;
        celdas[fila][col].setText("♞");
        celdas[fila][col].setForeground(Color.BLACK);

        contadorLabel.setText("Movimientos: " + movimientos);

        actualizarDisponibles();
    }

    private void actualizarDisponibles() {

        for (int f = 0; f < N; f++) {
            for (int c = 0; c < N; c++) {
                celdas[f][c].setEnabled(false);
            }
        }

        if (filaActual != -1 && colActual != -1) {
            for (int[] m : obtenerMovimientosValidos(filaActual, colActual)) {
                if (!visitadas[m[0]][m[1]]) {
                    celdas[m[0]][m[1]].setEnabled(true);
                }
            }
        }
    }

    private void reiniciarRecorrido() {

        String tipo = "RecorridoCaballo-" + N;
        String resumen = "Juego reiniciado por el usuario. " + movimientos + " movimientos registrados.";
        Partida reinicio = new Partida(tipo, resumen, java.time.LocalDateTime.now());
        reinicio.guardar();

        limpiarTablero();

    }



    private void resolverAutomaticamente() {
        if (resolviendo) return;
        resolviendo = true;
        limpiarTablero();
        bloquearTablero();
        mensajeLabel.setText("Resolviendo automáticamente...");
        btnReiniciar.setEnabled(false);
        btnResolverAuto.setEnabled(false);


        new SwingWorker<Void, int[]>() {
            private List<int[]> recorrido;

            @Override
            protected Void doInBackground() throws Exception {
                arcade.fabrica.JuegoAbstractFactory factory = arcade.fabrica.FabricaJuegosCompleta.obtenerFactory("Caballo");
                arcade.juegos.caballo.ControladorCaballo controlador =
                        (arcade.juegos.caballo.ControladorCaballo) factory.crearControlador(N);
                ResultadoCaballo resultado = controlador.resolver();
                recorrido = resultado.getRecorrido();
                for (int i = 0; i < recorrido.size(); i++) {
                    int[] actual = recorrido.get(i);
                    Thread.sleep(300);
                    publish(actual);
                }
                return null;
            }

            @Override
            protected void process(List<int[]> chunks) {
                int[] paso = chunks.get(chunks.size() - 1);
                if (filaActual != -1 && colActual != -1) {
                    celdas[filaActual][colActual].setText(String.valueOf(movimientos));
                    celdas[filaActual][colActual].setForeground(Color.GREEN);
                }
                filaActual = paso[0];
                colActual = paso[1];
                celdas[filaActual][colActual].setText("♞");
                celdas[filaActual][colActual].setForeground(Color.GREEN);
                visitadas[filaActual][colActual] = true;
                movimientos++;
                contadorLabel.setText("Movimientos: " + movimientos);
            }

            @Override
            protected void done() {
                resolviendo = false;
                btnReiniciar.setEnabled(true);
                btnResolverAuto.setEnabled(true);
                if (recorrido != null && recorrido.size() == N * N) {
                    mensajeLabel.setText("¡Recorrido completo automáticamente!");
                    colorearCasillasFinal(true);
                } else {
                    mensajeLabel.setText("No se encontró una solución automática.");
                    JOptionPane.showMessageDialog(PanelCaballo.this, "No se pudo resolver el recorrido automáticamente.",
                            "Sin solución", JOptionPane.WARNING_MESSAGE);
                }
            }
        }.execute();
    }


    private List<int[]> obtenerMovimientosValidos(int f, int c) {
        int[][] movs = {
                {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
                {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        List<int[]> validos = new ArrayList<>();
        for (int[] m : movs) {
            int nf = f + m[0];
            int nc = c + m[1];
            if (nf >= 0 && nf < N && nc >= 0 && nc < N && !visitadas[nf][nc]) {
                validos.add(new int[]{nf, nc});
            }
        }
        return validos;
    }

    private void limpiarTablero() {
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                celdas[fila][col].setText("");
                celdas[fila][col].setForeground(Color.BLACK);
                celdas[fila][col].setBackground((fila + col) % 2 == 0 ? Color.WHITE : new Color(200, 200, 200));
                visitadas[fila][col] = false;
                celdas[fila][col].setEnabled(true);
            }
        }
        filaActual = -1;
        colActual = -1;
        movimientos = 0;
        contadorLabel.setText("Movimientos: 0");
        mensajeLabel.setText("Selecciona una casilla para comenzar");
        resolviendo = false;
    }

    private boolean esMovimientoValido(int fila, int col) {
        int df = Math.abs(fila - filaActual);
        int dc = Math.abs(col - colActual);
        return (df == 2 && dc == 1) || (df == 1 && dc == 2);
    }

    private void bloquearTablero() {
        for (int f = 0; f < N; f++) {
            for (int c = 0; c < N; c++) {
                celdas[f][c].setEnabled(false);
            }
        }
    }

    private void guardarPartida(boolean completado) {
        String resumen = completado
                ? "Recorrido completo con " + movimientos + " movimientos."
                : "Recorrido incompleto. Solo se visitaron " + movimientos + " casillas.";
        String tipo = "RecorridoCaballo-" + N;
        Partida partida = new Partida(tipo, resumen, LocalDateTime.now());
        partida.guardar();
    }

    private void colorearCasillasFinal(boolean exito) {
        Color color = exito ? new Color(144, 238, 144) : new Color(255, 182, 193); // verde claro o rosa claro
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                if (visitadas[fila][col]) {
                    celdas[fila][col].setBackground(color);
                }
            }
        }
        if (movimientos > 0) {
            int[] inicio = obtenerPrimeraCasillaVisitada();
            int[] fin = new int[]{filaActual, colActual};
            celdas[inicio[0]][inicio[1]].setBackground(Color.CYAN);
            celdas[fin[0]][fin[1]].setBackground(Color.ORANGE);
        }
    }

    private int[] obtenerPrimeraCasillaVisitada() {
        for (int[] fila : obtenerRecorrido()) {
            return fila;
        }
        return new int[]{0, 0};
    }

    private List<int[]> obtenerRecorrido() {
        List<int[]> lista = new ArrayList<>();
        for (int f = 0; f < N; f++) {
            for (int c = 0; c < N; c++) {
                if (!"".equals(celdas[f][c].getText())) {
                    lista.add(new int[]{f, c});
                }
            }
        }
        return lista;
    }
}
