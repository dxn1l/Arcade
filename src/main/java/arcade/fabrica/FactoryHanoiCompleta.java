package arcade.fabrica;

import arcade.juegos.hanoi.HanoiJuego;
import arcade.juegos.hanoi.ControladorHanoi;
import arcade.vista.PanelHanoi;

import javax.swing.JPanel;

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
    public JPanel crearPanel(int discos) {
        return new PanelHanoi(discos);
    }
}
