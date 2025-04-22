package arcade.juegos.nreinas;

import arcade.interfaz.Juego;
import arcade.persistencia.Partida;

public class NReinasJuego implements Juego {

    private int N;
    private int[][] tablero;
    private boolean solucionEncontrada;

    public NReinasJuego(int N) {
        this.N = N;
        this.tablero = new int[N][N];
    }

    @Override
    public void iniciar() {
        solucionEncontrada = false;
    }

    @Override
    public void resolver() {
        solucionEncontrada = resolverUtil(0);
    }

    private boolean resolverUtil(int fila) {
        if (fila >= N) return true;

        for (int col = 0; col < N; col++) {
            if (esSeguro(fila, col)) {
                tablero[fila][col] = 1;
                if (resolverUtil(fila + 1)) return true;
                tablero[fila][col] = 0; // backtrack
            }
        }
        return false;
    }

    private boolean esSeguro(int fila, int col) {
        for (int i = 0; i < fila; i++) if (tablero[i][col] == 1) return false;
        for (int i = fila, j = col; i >= 0 && j >= 0; i--, j--) if (tablero[i][j] == 1) return false;
        for (int i = fila, j = col; i >= 0 && j < N; i--, j++) if (tablero[i][j] == 1) return false;
        return true;
    }

    @Override
    public Partida obtenerResultado() {
        return new ResultadoNReinas(N, solucionEncontrada).toPartida();
    }

    public int[][] getTablero() {
        return tablero;
    }

    public boolean isSolucionEncontrada() {
        return solucionEncontrada;
    }
}
