package arcade.fabrica;

import arcade.vista.PanelCaballo;
import javax.swing.JPanel;

public class FactoryCaballo implements JuegoFactory {
    @Override
    public JPanel crear(int tamaño) {
        return new PanelCaballo(tamaño);
    }
}
