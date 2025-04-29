package arcade.fabrica;

import arcade.juegos.caballo.ControladorCaballo;
import arcade.juegos.caballo.CaballoJuego;
import arcade.vista.PanelCaballo;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.assertj.core.api.Assertions.*;

public class FabricaJuegosCompletaTest {

    @Test
    void debeCrearFactoryValidaParaCaballo() {
        JuegoAbstractFactory factory = FabricaJuegosCompleta.obtenerFactory("Caballo");
        assertThat(factory).isNotNull();
    }

    @Test
    void debeCrearControladorCaballo() {
        JuegoAbstractFactory factory = FabricaJuegosCompleta.obtenerFactory("Caballo");
        Object controlador = factory.crearControlador(5);
        assertThat(controlador).isInstanceOf(ControladorCaballo.class);
    }

    @Test
    void debeCrearLogicaCaballo() {
        JuegoAbstractFactory factory = FabricaJuegosCompleta.obtenerFactory("Caballo");
        Object logica = factory.crearLogica(5);
        assertThat(logica).isInstanceOf(CaballoJuego.class);
    }

    @Test
    void debeCrearPanelCaballo() {
        JuegoAbstractFactory factory = FabricaJuegosCompleta.obtenerFactory("Caballo");
        JPanel panel = factory.crearPanelConAccion(5, () -> {});
        assertThat(panel).isInstanceOf(PanelCaballo.class);
    }

    @Test
    void debeLanzarExcepcionConTipoDesconocido() {
        assertThatThrownBy(() -> FabricaJuegosCompleta.obtenerFactory("Inexistente"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tipo de juego desconocido");
    }
}
