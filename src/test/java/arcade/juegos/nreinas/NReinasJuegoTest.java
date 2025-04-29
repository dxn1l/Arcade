package arcade.juegos.nreinas;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NReinasJuegoTest {

    @Test
    void debeResolverCorrectamenteParaN4() {
        ResultadoNReinas resultado = ControladorNReinas.resolver(4);
        assertThat(resultado.esExitosa()).isTrue();
        assertThat(resultado.getSolucion()).hasSize(4);
    }

    @Test
    void debeColocarReinasSinConflictos() {
        ResultadoNReinas resultado = ControladorNReinas.resolver(6);
        assertThat(resultado.esExitosa()).isTrue();

        var reinas = resultado.getSolucion();
        for (int i = 0; i < reinas.size(); i++) {
            for (int j = i + 1; j < reinas.size(); j++) {
                int[] r1 = reinas.get(i);
                int[] r2 = reinas.get(j);
                boolean seAtacan = r1[0] == r2[0] ||
                        r1[1] == r2[1] ||
                        Math.abs(r1[0] - r2[0]) == Math.abs(r1[1] - r2[1]);
                assertThat(seAtacan).isFalse();
            }
        }
    }
}
