package arcade.fabrica;

import arcade.juegos.caballo.CaballoJuego;
import arcade.juegos.caballo.ControladorCaballo;
import arcade.vista.PanelCaballo;

import javax.swing.JPanel;

public class FactoryCaballoCompleta implements JuegoAbstractFactory {

    @Override
    public CaballoJuego crearLogica(int tamaño) {
        return new CaballoJuego(tamaño);
    }

    @Override
    public ControladorCaballo crearControlador(int tamaño) {
        return new ControladorCaballo(tamaño);
    }

    @Override
    public JPanel crearPanel(int tamaño) {
        return new PanelCaballo(tamaño);
    }
}
