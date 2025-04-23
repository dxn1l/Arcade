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
        itemNReinas.addActionListener(e -> {
            String[] opciones = {"4x4", "6x6", "8x8"};
            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Selecciona el tamaño del tablero:",
                    "Tamaño del tablero",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    "8x8"
            );

            if (seleccion != null) {
                int n = Integer.parseInt(seleccion.substring(0, 1));
                mostrarPanel(new PanelNReinas(n));
            }
        });

        JMenuItem itemCaballo = new JMenuItem("Recorrido del Caballo");
        itemCaballo.addActionListener(e -> {
            String[] opciones = {"5x5", "6x6", "8x8"};
            String seleccion = (String) JOptionPane.showInputDialog(
                    this,
                    "Selecciona el tamaño del tablero:",
                    "Tamaño del tablero",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    "8x8"
            );

            if (seleccion != null) {
                int n = Integer.parseInt(seleccion.substring(0, 1));
                mostrarPanel(new PanelCaballo(n));
            }
        });


        // JMenuItem itemHanoi = new JMenuItem("Torres de Hanoi");

        menuJuegos.add(itemNReinas);
        menuJuegos.add(itemCaballo);
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
