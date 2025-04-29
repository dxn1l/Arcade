package arcade.fabrica;

public class FabricaJuegosCompleta {

    public static JuegoAbstractFactory obtenerFactory(String tipoJuego) {
        return switch (tipoJuego) {
            case "Caballo" -> new FactoryCaballoCompleta();
            case "NReinas" -> new FactoryNReinasCompleta();
            case "Hanoi"   -> new FactoryHanoiCompleta();
            default        -> throw new IllegalArgumentException("Tipo de juego desconocido: " + tipoJuego);
        };
    }
}
