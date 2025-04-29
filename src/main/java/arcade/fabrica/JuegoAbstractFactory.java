package arcade.fabrica;

import javax.swing.JPanel;

public interface JuegoAbstractFactory {
    Object crearLogica(int parametro);
    Object crearControlador(int parametro);
    JPanel crearPanel(int parametro);
}
