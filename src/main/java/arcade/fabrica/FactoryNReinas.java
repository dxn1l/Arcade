package arcade.fabrica;

import arcade.vista.PanelNReinas;
import javax.swing.JPanel;

public class FactoryNReinas implements JuegoFactory {
    @Override
    public JPanel crear(int tamaño) {
        return new PanelNReinas(tamaño);
    }
}
