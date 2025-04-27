package arcade.juegos.caballo;

import arcade.persistencia.Partida;

import java.time.LocalDateTime;
import java.util.List;

public class ResultadoCaballo {
    private final int N;
    private final List<int[]> recorrido;
    private final boolean completo;

    public ResultadoCaballo(int N, List<int[]> recorrido, boolean completo) {
        this.N = N;
        this.recorrido = recorrido;
        this.completo = completo;
    }

    public List<int[]> getRecorrido() {
        return recorrido;
    }

    public boolean esCompleto() {
        return completo;
    }

    public Partida toPartida() {
        String resumen = completo ?
                "Recorrido completo con " + recorrido.size() + " movimientos." :
                "Recorrido incompleto. Solo se visitaron " + recorrido.size() + " casillas.";
        return new Partida("RecorridoCaballo-" + N, resumen, LocalDateTime.now());
    }
}