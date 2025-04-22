package arcade.vista;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    private final JPanel panelContenedor;

    public MenuPrincipal() {
        setTitle("Máquina Arcade - Juegos de Lógica");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();

        JMenu menuJuegos = new JMenu("Juegos");
        JMenuItem itemNReinas = new JMenuItem("N Reinas");
        itemNReinas.addActionListener(e -> mostrarPanel(new PanelNReinas()));

        // Aquí después podés añadir:
        // JMenuItem itemCaballo = new JMenuItem("Recorrido del Caballo");
        // JMenuItem itemHanoi = new JMenuItem("Torres de Hanoi");

        menuJuegos.add(itemNReinas);
        menuBar.add(menuJuegos);

        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem itemHistorial = new JMenuItem("Ver historial de partidas");

        itemHistorial.addActionListener(e -> {
            new VentanaHistorial().setVisible(true);
        });

        menuOpciones.add(itemHistorial);
        menuBar.add(menuOpciones);

        setJMenuBar(menuBar);

        panelContenedor = new JPanel(new BorderLayout());
        add(panelContenedor, BorderLayout.CENTER);

        JLabel labelBienvenida = new JLabel("Selecciona un juego desde el menú", SwingConstants.CENTER);
        panelContenedor.add(labelBienvenida, BorderLayout.CENTER);
    }

    private void mostrarPanel(JPanel nuevoPanel) {
        panelContenedor.removeAll();
        panelContenedor.add(nuevoPanel, BorderLayout.CENTER);
        panelContenedor.revalidate();
        panelContenedor.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
