package arcade.juegos.nreinas;

import arcade.persistencia.Partida;

import java.time.LocalDateTime;
import java.util.List;

public class ResultadoNReinas {
    private final int N;
    private final boolean solucion;
    private final List<int[]> solucionCoords;
    private final boolean esAutomatica;


    public ResultadoNReinas(int n, boolean solucion, List<int[]> solucionCoords , boolean esAutomatica) {
        this.N = n;
        this.solucion = solucion;
        this.solucionCoords = solucionCoords;
        this.esAutomatica = esAutomatica;
    }

    public List<int[]> getSolucion() {
        return solucionCoords;
    }

    public boolean esExitosa() {
        return solucion;
    }

    public boolean esAutomatica() {
        return esAutomatica;
    }

    public Partida toPartida() {
        String origen = esAutomatica ? " (Automática)" : " (Manual)";
        String resumen = solucion ? "Solución encontrada para N = " + N + origen
                : "No se encontró solución para N = " + N + origen;
        return new Partida("NReinas-" + N, resumen, LocalDateTime.now());
    }


}
