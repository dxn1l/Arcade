package arcade.juegos.caballo;

import arcade.persistencia.Partida;


public class ControladorCaballo {

    private final CaballoJuego juego;

    public ControladorCaballo(int N) {
        this.juego = new CaballoJuego(N);
    }

    public ResultadoCaballo resolver() {
        juego.resolver();
        ResultadoCaballo resultado = juego.obtenerResultado();
        Partida partida = resultado.toPartida();
        partida.guardar();
        return resultado;
    }
}