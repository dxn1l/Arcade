package arcade.persistencia;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PartidaTest {

    @Test
    void debeGuardarYRecuperarUnaPartida() {
        Partida partida = new Partida("Test-Tipo", "Simulación exitosa", LocalDateTime.now());
        partida.guardar();

        List<Partida> historial = Partida.obtenerHistorial();

        assertThat(historial).isNotEmpty();
        assertThat(historial.stream().anyMatch(p -> p.getTipoJuego().equals("Test-Tipo"))).isTrue();
    }

    @Test
    void debeEliminarPartidasPorTipo() {
        Partida p1 = new Partida("Prueba-Eliminar", "Eliminar esta", LocalDateTime.now());
        p1.guardar();

        Partida.eliminarPorTipo("Prueba-Eliminar");

        List<Partida> historial = Partida.obtenerHistorial();
        assertThat(historial.stream().noneMatch(p -> p.getTipoJuego().equals("Prueba-Eliminar"))).isTrue();
    }

    @Test
    void debeEliminarTodasLasPartidas() {
        new Partida("GlobalTest", "1", LocalDateTime.now()).guardar();
        new Partida("GlobalTest", "2", LocalDateTime.now()).guardar();

        Partida.eliminarTodas();

        List<Partida> historial = Partida.obtenerHistorial();
        assertThat(historial).isEmpty();
    }
}
