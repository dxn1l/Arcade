package arcade.vista;

import arcade.juegos.nreinas.ResultadoNReinas;
import arcade.persistencia.Partida;

import javax.swing.*;
import java.awt.*;

public class PanelNReinas extends JPanel {

    private static final int N = 8;
    private final JButton[][] celdas = new JButton[N][N];
    private final int[][] tablero = new int[N][N];

    private final JPanel panelTablero;
    private final JLabel mensajeLabel;
    private final JLabel contadorLabel;

    public PanelNReinas() {
        setLayout(new BorderLayout());

        panelTablero = new JPanel(new GridLayout(N, N));
        panelTablero.setPreferredSize(new Dimension(500, 500));
        panelTablero.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        inicializarTablero();
        add(panelTablero, BorderLayout.CENTER);

        mensajeLabel = new JLabel("Haz clic para colocar o quitar reinas", SwingConstants.CENTER);
        mensajeLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        mensajeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(mensajeLabel, BorderLayout.NORTH);

        JPanel panelInferior = new JPanel(new FlowLayout());

        JButton validarBtn = new JButton("Validar solución");
        validarBtn.addActionListener(e -> validar());

        JButton reiniciarBtn = new JButton("Reiniciar tablero");
        reiniciarBtn.addActionListener(e -> reiniciarTablero());

        contadorLabel = new JLabel("Reinas colocadas: 0");

        panelInferior.add(validarBtn);
        panelInferior.add(reiniciarBtn);
        panelInferior.add(contadorLabel);

        add(panelInferior, BorderLayout.SOUTH);

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
        if (tablero[fila][col] == 0) {
            tablero[fila][col] = 1;
            celdas[fila][col].setText("Q");
        } else {
            tablero[fila][col] = 0;
            celdas[fila][col].setText("");
        }
        actualizarContador();
    }

    private void actualizarContador() {
        int contador = 0;
        for (int[] fila : tablero) {
            for (int val : fila) {
                if (val == 1) contador++;
            }
        }
        contadorLabel.setText("Reinas colocadas: " + contador);
    }

    private void validar() {
        boolean valido = esValido();
        mensajeLabel.setText(valido ? "¡Solución válida!" : "Las reinas se atacan entre sí.");

        ResultadoNReinas resultado = new ResultadoNReinas(N, valido);
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

    private boolean esValido() {
        for (int fila1 = 0; fila1 < N; fila1++) {
            for (int col1 = 0; col1 < N; col1++) {
                if (tablero[fila1][col1] == 1) {
                    for (int fila2 = 0; fila2 < N; fila2++) {
                        for (int col2 = 0; col2 < N; col2++) {
                            if ((fila1 != fila2 || col1 != col2) && tablero[fila2][col2] == 1) {

                                if (fila1 == fila2) return false;

                                if (col1 == col2) return false;

                                if (Math.abs(fila1 - fila2) == Math.abs(col1 - col2)) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


}
