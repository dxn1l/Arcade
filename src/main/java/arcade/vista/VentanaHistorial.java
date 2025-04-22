package arcade.vista;

import arcade.persistencia.Partida;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class VentanaHistorial extends JFrame {

    private final JEditorPane areaTexto;
    private final JComboBox<String> filtroCombo;

    public VentanaHistorial() {
        setTitle("Historial de Partidas");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        String[] tipos = {"Todos", "NReinas", "RecorridoCaballo", "TorresHanoi"};
        filtroCombo = new JComboBox<>(tipos);

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar todo");

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(new JLabel("Filtrar por juego:"));
        panelTop.add(filtroCombo);
        panelTop.add(btnActualizar);
        panelTop.add(btnEliminar);
        add(panelTop, BorderLayout.NORTH);

        areaTexto = new JEditorPane();
        areaTexto.setContentType("text/html");
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        cargarHistorial("Todos");

        btnActualizar.addActionListener(e -> {
            String filtro = (String) filtroCombo.getSelectedItem();
            cargarHistorial(filtro);
        });

        btnEliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas eliminar todas las partidas?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                Partida.eliminarTodas();
                cargarHistorial("Todos");
            }
        });
    }

    private void cargarHistorial(String tipoFiltro) {
        List<Partida> partidas = Partida.obtenerHistorial();

        if (!"Todos".equals(tipoFiltro)) {
            partidas = partidas.stream()
                    .filter(p -> p.getTipoJuego().startsWith(tipoFiltro))
                    .collect(Collectors.toList());
        }

        StringBuilder sb = new StringBuilder("<html><body style='font-family:monospace;'>");

        for (Partida p : partidas) {
            sb.append(p.toString()).append("<br>");
        }

        if (partidas.isEmpty()) {
            sb.append("No hay partidas guardadas.");
        }

        sb.append("</body></html>");
        areaTexto.setText(sb.toString());
    }
}
