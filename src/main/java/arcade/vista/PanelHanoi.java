package arcade.vista;

import arcade.juegos.hanoi.ControladorHanoi;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelHanoi extends JPanel {

    private final JPanel[] postes = new JPanel[3];
    private final JLabel contadorLabel;
    private final int cantidadDiscos;
    private JPanel posteOrigen = null;
    private int movimientos = 0;
    private boolean resolviendo = false;
    private final JButton btnReiniciar;
    private final JButton btnLimpiar;
    private final JButton btnResolver;

    public PanelHanoi(int cantidadDiscos) {
        this.cantidadDiscos = cantidadDiscos;
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Torres de Hanoi", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panelPostes = new JPanel(new GridLayout(1, 3, 20, 0));
        panelPostes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 3; i++) {
            final int indice = i;
            postes[i] = crearPoste();
            postes[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (!resolviendo) manejarClickPoste(indice);
                }
            });
            panelPostes.add(postes[i]);
        }

        add(panelPostes, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout());

        btnReiniciar = new JButton("Reiniciar partida");
        btnReiniciar.setEnabled(false);
        btnReiniciar.addActionListener(e -> reiniciar());
        panelInferior.add(btnReiniciar);

        btnLimpiar = new JButton("Limpiar tablero");
        btnLimpiar.setEnabled(false);
        btnLimpiar.addActionListener(e -> limpiarTablero());
        panelInferior.add(btnLimpiar);

        btnResolver = new JButton("Resolver automáticamente");
        btnResolver.addActionListener(e -> resolverAutomaticamente());
        panelInferior.add(btnResolver);


        contadorLabel = new JLabel("Movimientos: 0");
        panelInferior.add(contadorLabel);

        add(panelInferior, BorderLayout.SOUTH);

        inicializarDiscos(cantidadDiscos);
    }

    private JPanel crearPoste() {
        JPanel poste = new JPanel();
        poste.setLayout(new BoxLayout(poste, BoxLayout.Y_AXIS));
        poste.setBackground(Color.LIGHT_GRAY);
        poste.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        Component filler = new Box.Filler(
                new Dimension(0, Short.MAX_VALUE),
                new Dimension(0, Short.MAX_VALUE),
                new Dimension(0, Short.MAX_VALUE)
        );
        poste.add(filler);
        return poste;
    }

    private void inicializarDiscos(int cantidad) {
        postes[0].removeAll();
        postes[0].setLayout(new BoxLayout(postes[0], BoxLayout.Y_AXIS));
        for (int i = cantidad; i >= 1; i--) {
            JPanel disco = crearDisco(i);
            postes[0].add(disco);
        }
        postes[0].add(Box.createVerticalGlue());
        for (int i = 1; i < 3; i++) {
            postes[i].removeAll();
            postes[i].setLayout(new BoxLayout(postes[i], BoxLayout.Y_AXIS));
            postes[i].add(Box.createVerticalGlue());
        }
        movimientos = 0;
        contadorLabel.setText("Movimientos: 0");
        revalidate();
        repaint();
    }

    private JPanel crearDisco(int tamaño) {
        JPanel disco = new JPanel();
        disco.setPreferredSize(new Dimension(40 + tamaño * 20, 20));
        disco.setMaximumSize(new Dimension(40 + tamaño * 20, 20));
        disco.setBackground(obtenerColorPorTamaño(tamaño));
        disco.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return disco;
    }

    private Color obtenerColorPorTamaño(int tamaño) {
        switch (tamaño) {
            case 1: return Color.RED;
            case 2: return Color.ORANGE;
            case 3: return Color.YELLOW;
            case 4: return Color.GREEN;
            case 5: return Color.BLUE;
            default: return Color.GRAY;
        }
    }

    private void manejarClickPoste(int indice) {
        if (posteOrigen == null) {
            if (postes[indice].getComponentCount() > 1) {
                posteOrigen = postes[indice];
                mensajeSeleccion("Origen seleccionado: Poste " + (indice + 1));
            } else {
                mensajeSeleccion("Este poste está vacío.");
            }
        } else {
            if (moverDisco(posteOrigen, postes[indice])) {
                movimientos++;
                btnReiniciar.setEnabled(true);
                contadorLabel.setText("Movimientos: " + movimientos);
                if (postes[2].getComponentCount() - 1 == cantidadDiscos) {
                    JOptionPane.showMessageDialog(this, "¡Felicidades! Has resuelto Hanoi en " + movimientos + " movimientos.");
                    guardarPartida(true);
                    bloquearPostes();
                }
            }
            posteOrigen = null;
        }
    }

    private boolean moverDisco(JPanel origen, JPanel destino) {
        if (origen.getComponentCount() <= 1) return false;

        JPanel discoOrigen = (JPanel) origen.getComponent(origen.getComponentCount() - 2);

        if (destino.getComponentCount() > 1) {
            JPanel discoDestino = (JPanel) destino.getComponent(destino.getComponentCount() - 2);
            if (discoDestino.getWidth() < discoOrigen.getWidth()) {
                JOptionPane.showMessageDialog(this,
                        "No puedes colocar un disco grande sobre uno pequeño.",
                        "Movimiento inválido",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        origen.remove(discoOrigen);
        destino.add(discoOrigen, destino.getComponentCount() - 1);
        origen.revalidate();
        origen.repaint();
        destino.revalidate();
        destino.repaint();
        return true;
    }

    private void mensajeSeleccion(String msg) {
        contadorLabel.setText(msg);
    }

    private void guardarPartida(boolean completado) {
        String resumen = completado
                ? "Torres de Hanoi completado en " + movimientos + " movimientos."
                : "Partida incompleta de Hanoi. " + movimientos + " movimientos.";
        String tipo = "TorresHanoi-" + cantidadDiscos;
        new arcade.persistencia.Partida(tipo, resumen, java.time.LocalDateTime.now()).guardar();
    }

    private void bloquearPostes() {
        for (JPanel poste : postes) {
            for (var listener : poste.getMouseListeners()) {
                poste.removeMouseListener(listener);
            }
        }
    }

    private void reiniciar() {
        if (resolviendo) return;
        posteOrigen = null;
        inicializarDiscos(cantidadDiscos);
    }

    private void resolverAutomaticamente() {
        btnReiniciar.setEnabled(false);
        btnLimpiar.setEnabled(false);
        resolviendo = true;
        posteOrigen = null;
        inicializarDiscos(cantidadDiscos);

        ControladorHanoi controlador = new ControladorHanoi(cantidadDiscos);
        controlador.resolver();
        List<int[]> pasos = controlador.obtenerPasos();

        new SwingWorker<Void, int[]>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int[] paso : pasos) {
                    Thread.sleep(500);
                    publish(paso);
                }
                return null;
            }

            @Override
            protected void process(List<int[]> chunks) {
                int[] paso = chunks.get(chunks.size() - 1);
                moverDisco(postes[paso[0]], postes[paso[1]]);
                movimientos++;
                contadorLabel.setText("Movimientos: " + movimientos);
            }

            @Override
            protected void done() {
                btnReiniciar.setEnabled(false);
                btnLimpiar.setEnabled(true);
                JOptionPane.showMessageDialog(PanelHanoi.this, "¡Resolución automática completada!");
                guardarPartida(true);
                bloquearPostes();
                resolviendo = false;
            }
        }.execute();
    }

    private void limpiarTablero() {
        posteOrigen = null;
        resolviendo = false;
        movimientos = 0;
        contadorLabel.setText("Movimientos: 0");

        // Restaurar paneles y listeners
        for (int i = 0; i < 3; i++) {
            final int index = i;
            postes[i].removeAll();
            postes[i].setLayout(new BoxLayout(postes[i], BoxLayout.Y_AXIS));
            postes[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    if (!resolviendo) manejarClickPoste(index);
                }
            });
            postes[i].add(Box.createVerticalGlue());
        }

        // Colocar discos nuevamente en el primer poste
        for (int i = cantidadDiscos; i >= 1; i--) {
            JPanel disco = crearDisco(i);
            postes[0].add(disco, postes[0].getComponentCount() - 1);
        }

        revalidate();
        repaint();

        // Actualizar botones
        btnLimpiar.setEnabled(false);
        btnReiniciar.setEnabled(false);
        btnResolver.setEnabled(true);
    }



}
