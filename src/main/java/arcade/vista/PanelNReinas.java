package arcade.vista;

import arcade.juegos.nreinas.ControladorNReinas;
import arcade.juegos.nreinas.ResultadoNReinas;
import arcade.persistencia.Partida;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelNReinas extends JPanel {

    private final int N ;
    private final JButton[][] celdas ;
    private final int[][] tablero ;

    private final JPanel panelTablero;
    private final JLabel mensajeLabel;
    private final JLabel contadorLabel;
    private final JLabel cantidadLabel;

    public PanelNReinas(int N , Runnable volverAccion) {

        this.N = N;
        this.celdas = new JButton[N][N];
        this.tablero = new int[N][N];

        setLayout(new BorderLayout());

        cantidadLabel = new JLabel("Debes colocar exactamente " + N + " reinas.", SwingConstants.CENTER);
        cantidadLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        cantidadLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        add(cantidadLabel, BorderLayout.NORTH);

        panelTablero = new JPanel(new GridLayout(N, N));
        panelTablero.setPreferredSize(new Dimension(500, 500));
        panelTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        inicializarTablero();
        add(panelTablero, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());

        JButton btnVolver = new JButton("Volver al menú");
        btnVolver.addActionListener(e -> volverAccion.run());
        panelInferior.add(btnVolver);


        mensajeLabel = new JLabel("Haz clic para colocar o quitar reinas", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));


        contadorLabel = new JLabel("Reinas colocadas: 0");

        JButton validarBtn = new JButton("Validar solución");
        validarBtn.addActionListener(e -> validar());

        JButton reiniciarBtn = new JButton("Reiniciar tablero");
        reiniciarBtn.addActionListener(e -> reiniciarTablero());

        JButton btnResolverAuto = new JButton("Resolver automáticamente");
        btnResolverAuto.addActionListener(e -> resolverAutomaticamente());
        panelInferior.add(btnResolverAuto);


        panelInferior.add(validarBtn);
        panelInferior.add(reiniciarBtn);
        panelInferior.add(contadorLabel);

        add(mensajeLabel, BorderLayout.SOUTH);
        add(panelInferior, BorderLayout.PAGE_END);

    }

    private void inicializarTablero() {
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                JButton celda = new JButton();
                celda.setBackground((fila + col) % 2 == 0 ? Color.WHITE : new Color(200, 200, 200));
                celda.setFont(new Font("SansSerif", Font.BOLD, 24));
                final int f = fila;
                final int c = col;
                celda.addActionListener(e -> toggleReina(f, c));
                celdas[fila][col] = celda;
                panelTablero.add(celda);
            }
        }
    }

    private void toggleReina(int fila, int col) {

        int colocadas = contarReinas();

        if (tablero[fila][col] == 0) {
            if (colocadas >= N) {
                JOptionPane.showMessageDialog(this,
                        "Solo puedes colocar " + N + " reinas.",
                        "Límite alcanzado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            tablero[fila][col] = 1;
            celdas[fila][col].setText("♛");
        } else {
            tablero[fila][col] = 0;
            celdas[fila][col].setText("");
        }
        actualizarContador();
    }

    private void actualizarContador() {
        contadorLabel.setText("Reinas colocadas: " + contarReinas());
    }

    private int contarReinas() {
        int contador = 0;
        for (int[] fila : tablero) {
            for (int val : fila) {
                if (val == 1) contador++;
            }
        }
        return contador;
    }

    private void validar() {
        int colocadas = contarReinas();
        if (colocadas < N) {
            JOptionPane.showMessageDialog(this,
                    "Debes colocar exactamente " + N + " reinas antes de validar.",
                    "Reinas insuficientes",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        boolean valido = esValido();
        if (valido) {
            mensajeLabel.setText("¡Solución válida!");
            JOptionPane.showMessageDialog(this,
                    "¡Se encontró una solución válida para N = " + N + "!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            mensajeLabel.setText("Las reinas se atacan entre sí.");
            JOptionPane.showMessageDialog(this,
                    "No se encontronó una solución válida para N = " + N + ". Las reinas se están atacando.",
                    "Error de solución",
                    JOptionPane.WARNING_MESSAGE);
        }

        ResultadoNReinas resultado = new ResultadoNReinas(N, valido , obtenerReinasDelTablero(), false);
        Partida partida = resultado.toPartida();
        partida.guardar();
    }

    private void reiniciarTablero() {
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                tablero[fila][col] = 0;
                celdas[fila][col].setText("");
            }
        }
        mensajeLabel.setText("Haz clic para colocar o quitar reinas");
        actualizarContador();
    }

    private void resolverAutomaticamente() {
        reiniciarTablero();

        ResultadoNReinas resultado = ControladorNReinas.resolver(N);
        if (resultado == null || !resultado.esExitosa()) {
            JOptionPane.showMessageDialog(this,
                    "No se encontró una solución automática para N = " + N,
                    "Sin solución",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int[] coord : resultado.getSolucion()) {
            int fila = coord[0];
            int col = coord[1];
            tablero[fila][col] = 1;
            celdas[fila][col].setText("♛");
        }

        actualizarContador();
        mensajeLabel.setText("Solución automática colocada.");

        Partida partida = resultado.toPartida();
        partida.guardar();
    }

    private boolean esValido() {
        for (int fila1 = 0; fila1 < N; fila1++) {
            for (int col1 = 0; col1 < N; col1++) {
                if (tablero[fila1][col1] == 1) {
                    for (int fila2 = 0; fila2 < N; fila2++) {
                        for (int col2 = 0; col2 < N; col2++) {
                            if ((fila1 != fila2 || col1 != col2) && tablero[fila2][col2] == 1) {
                                if (fila1 == fila2 || col1 == col2 ||
                                        Math.abs(fila1 - fila2) == Math.abs(col1 - col2)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    private List<int[]> obtenerReinasDelTablero() {
        List<int[]> coords = new ArrayList<int[]>();
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                if ("♛".equals(celdas[fila][col].getText())) {
                    coords.add(new int[]{fila, col});
                }
            }
        }
        return coords;
    }






}
