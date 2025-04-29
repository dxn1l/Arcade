package arcade.juegos.hanoi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HanoiJuegoTest {

    @Test
    void debeResolverHanoiCon3Discos() {
        ControladorHanoi controlador = new ControladorHanoi(3);
        ResultadoHanoi resultado = controlador.resolver();

        List<int[]> pasos = controlador.obtenerPasos();

        assertThat(resultado.fueCompletado()).isTrue();
        assertThat(resultado.getCantidadMovimientos()).isEqualTo(7); // 2^3 - 1
        assertThat(pasos).hasSize(7);
    }
}
