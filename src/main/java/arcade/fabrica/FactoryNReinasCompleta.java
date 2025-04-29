package arcade.fabrica;

import arcade.juegos.nreinas.NReinasJuego;
import arcade.vista.PanelCaballo;
import arcade.vista.PanelNReinas;

import javax.swing.*;

public class FactoryNReinasCompleta implements JuegoAbstractFactory {

    @Override
    public NReinasJuego crearLogica(int tamaño) {
        return new NReinasJuego(tamaño);
    }

    @Override
    public Object crearControlador(int tamaño) {
        return null;
    }

    @Override
    public JPanel crearPanelConAccion(int tamaño, Runnable volverAccion) {
        return new PanelCaballo(tamaño,  volverAccion);
    }
}
