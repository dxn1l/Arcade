package arcade.interfaz;

import arcade.persistencia.Partida;

public interface Juego {
    void iniciar();
    boolean resolver();
    Partida obtenerResultado();
}
