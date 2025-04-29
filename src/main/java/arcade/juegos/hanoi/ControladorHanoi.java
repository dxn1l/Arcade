package arcade.juegos.hanoi;

import arcade.persistencia.Partida;

public class ControladorHanoi {

    private final HanoiJuego juego;

    public ControladorHanoi(int cantidadDiscos) {
        this.juego = new HanoiJuego(cantidadDiscos);
    }

    public ResultadoHanoi resolver() {
        juego.resolver();
        ResultadoHanoi resultado = new ResultadoHanoi(
                juego.getCantidadDiscos(),
                juego.getCantidadPasos(),
                true,
                true
        );
        Partida partida = resultado.toPartida();
        partida.guardar();
        return resultado;
    }

    public java.util.List<int[]> obtenerPasos() {
        return juego.obtenerPasos();
    }
}
