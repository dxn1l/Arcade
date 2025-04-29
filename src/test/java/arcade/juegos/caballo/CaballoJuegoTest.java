package arcade.juegos.caballo;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CaballoJuegoTest {

    @Test
    void debeResolverRecorridoCaballo5x5() {
        ControladorCaballo controlador = new ControladorCaballo(5);
        ResultadoCaballo resultado = controlador.resolver();
        assertThat(resultado.esCompleto()).isTrue();
        assertThat(resultado.getRecorrido()).hasSize(25);
    }
}
