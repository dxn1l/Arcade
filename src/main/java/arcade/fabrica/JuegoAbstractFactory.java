package arcade.fabrica;

import javax.swing.*;

public interface JuegoAbstractFactory {
    Object crearLogica(int parametro);
    Object crearControlador(int parametro );
    JPanel crearPanelConAccion(int parametro, Runnable volverAccion);
}
