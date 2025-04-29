package arcade.juegos.hanoi;

import arcade.persistencia.Partida;

import java.time.LocalDateTime;
import java.util.List;

public class ResultadoHanoi {

    private final int cantidadDiscos;
    private final int cantidadMovimientos;
    private final boolean completado;

    public ResultadoHanoi(int cantidadDiscos, int cantidadMovimientos, boolean completado) {
        this.cantidadDiscos = cantidadDiscos;
        this.cantidadMovimientos = cantidadMovimientos;
        this.completado = completado;
    }

    public Partida toPartida() {
        String tipo = "TorresHanoi-" + cantidadDiscos;
        String resumen = completado ?
                "Juego completado en " + cantidadMovimientos + " movimientos." :
                "Juego incompleto. Se realizaron " + cantidadMovimientos + " movimientos.";
        return new Partida(tipo, resumen, LocalDateTime.now());
    }

    public int getCantidadDiscos() {
        return cantidadDiscos;
    }

    public int getCantidadMovimientos() {
        return cantidadMovimientos;
    }

    public boolean fueCompletado() {
        return completado;
    }
}

