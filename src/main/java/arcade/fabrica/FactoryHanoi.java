package arcade.fabrica;

import arcade.vista.PanelHanoi;
import javax.swing.JPanel;

public class FactoryHanoi implements JuegoFactory {
    @Override
    public JPanel crear(int discos) {
        return new PanelHanoi(discos);
    }
}
