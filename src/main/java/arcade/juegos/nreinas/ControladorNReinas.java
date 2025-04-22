package arcade.juegos.nreinas;

public class ControladorNReinas {

    private final NReinasJuego juego;

    public ControladorNReinas(int N) {
        this.juego = new NReinasJuego(N);
    }

    public void iniciarJuego() {
        juego.iniciar();
    }

    public void resolver() {
        juego.resolver();
    }

    public int[][] obtenerTablero() {
        return juego.getTablero();
    }

    public boolean solucionEncontrada() {
        return juego.isSolucionEncontrada();
    }

    public void guardarResultado() {
        var partida = juego.obtenerResultado();
        partida.guardar();
    }
}
