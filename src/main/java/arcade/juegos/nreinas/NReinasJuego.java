package arcade.juegos.nreinas;


import java.util.ArrayList;
import java.util.List;

public class NReinasJuego  {

    private int N;
    private final List<List<int[]>> soluciones = new ArrayList<>();
    private int indiceSolucionActual = 0;

    public NReinasJuego(int N) {
        this.N = N;
        resolverTodas();
    }

    private void resolverTodas() {
        int[][] tablero = new int[N][N];
        backtrack(tablero, 0);
    }

    private void backtrack(int[][] tablero, int fila) {
        if (fila == N) {
            guardarSolucion(tablero);
            return;
        }

        for (int col = 0; col < N; col++) {
            if (esSeguro(tablero, fila, col)) {
                tablero[fila][col] = 1;
                backtrack(tablero, fila + 1);
                tablero[fila][col] = 0;
            }
        }
    }


    private boolean esSeguro(int[][] tablero, int fila, int col) {
        for (int i = 0; i < fila; i++) {
            if (tablero[i][col] == 1) return false;
        }
        for (int i = fila - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
            if (tablero[i][j] == 1) return false;
        }
        for (int i = fila - 1, j = col + 1; i >= 0 && j < N; i--, j++) {
            if (tablero[i][j] == 1) return false;
        }
        return true;
    }

    private void guardarSolucion(int[][] tablero) {
        List<int[]> solucion = new ArrayList<>();
        for (int fila = 0; fila < N; fila++) {
            for (int col = 0; col < N; col++) {
                if (tablero[fila][col] == 1) {
                    solucion.add(new int[]{fila, col});
                }
            }
        }
        soluciones.add(solucion);
    }

    public boolean haySoluciones() {
        return !soluciones.isEmpty();
    }

    public List<int[]> siguienteSolucion() {
        if (soluciones.isEmpty()) return null;
        List<int[]> solucion = soluciones.get(indiceSolucionActual);
        indiceSolucionActual = (indiceSolucionActual + 1) % soluciones.size(); // cicla
        return solucion;
    }

}
