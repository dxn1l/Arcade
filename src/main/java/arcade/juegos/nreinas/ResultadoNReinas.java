package arcade.juegos.nreinas;

import arcade.persistencia.Partida;

import java.time.LocalDateTime;

public class ResultadoNReinas {
    private final int N;
    private final boolean solucion;

    public ResultadoNReinas(int n, boolean solucion) {
        this.N = n;
        this.solucion = solucion;
    }

    public Partida toPartida() {
        String resumen = solucion ? "Solución encontrada para N = " + N
                : "No se encontró solución para N = " + N;
        return new Partida("NReinas-" + N, resumen, LocalDateTime.now());
    }
}
