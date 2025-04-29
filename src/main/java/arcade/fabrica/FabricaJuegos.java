package arcade.fabrica;

import javax.swing.JPanel;

public class FabricaJuegos {

    public static JPanel crearJuego(String tipo, int parametro) {
        JuegoFactory factory = switch (tipo) {
            case "Caballo" -> new FactoryCaballo();
            case "NReinas" -> new FactoryNReinas();
            case "Hanoi"   -> new FactoryHanoi();
            default -> throw new IllegalArgumentException("Tipo de juego desconocido: " + tipo);
        };
        return factory.crear(parametro);
    }
}
