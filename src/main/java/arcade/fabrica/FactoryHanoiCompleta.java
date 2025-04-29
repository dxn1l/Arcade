package arcade.fabrica;

import arcade.juegos.hanoi.HanoiJuego;
import arcade.juegos.hanoi.ControladorHanoi;
import arcade.vista.PanelCaballo;
import arcade.vista.PanelHanoi;

import javax.swing.*;

public class FactoryHanoiCompleta implements JuegoAbstractFactory {

    @Override
    public HanoiJuego crearLogica(int discos) {
        return new HanoiJuego(discos);
    }

    @Override
    public ControladorHanoi crearControlador(int discos) {
        return new ControladorHanoi(discos);
    }

    @Override
    public JPanel crearPanelConAccion(int discos, Runnable volverAccion) {
        return new PanelCaballo(discos,  volverAccion);
    }
}
