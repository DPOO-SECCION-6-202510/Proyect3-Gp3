package project1_parque.menuCliente;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.atracciones.Atraccion;

public class AtraccionesPanel extends JPanel {

    private PrincipalParque parquePrincipal;
    private List<Atraccion> atracciones;

    public AtraccionesPanel(PrincipalParque parquePrincipal) {
        this.parquePrincipal = parquePrincipal;
        this.atracciones = parquePrincipal.getListaAtracciones();

        setOpaque(false); // Hacemos transparente el panel para ver el fondo
        setLayout(new BorderLayout());

        // Panel de título con estilo
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        JLabel titulo = new JLabel("ATRACCIONES DISPONIBLES", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel interno con fondo semi-transparente para mejor legibilidad
        JPanel panelLista = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fondo semi-transparente
                g.setColor(new Color(0, 0, 0, 150)); // Negro con 150/255 de opacidad
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        panelLista.setLayout(new BoxLayout(panelLista, BoxLayout.Y_AXIS));
        panelLista.setOpaque(false); // Importante para que se vea el fondo
        panelLista.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Estilo para los elementos de la lista
        Font itemFont = new Font("SansSerif", Font.PLAIN, 16);
        Color textColor = Color.WHITE;

        for (Atraccion atraccion : atracciones) {
            String nombre = atraccion.getNombre();
            String categoria = atraccion.getClasificacionCategoria();
            int cupoMaximo = atraccion.getCupoMaximo();

            // Panel para cada atracción con borde y margen
            JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            itemPanel.setOpaque(false);
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(255, 255, 255, 100)), // Borde inferior blanco semi-transparente
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));

            // Icono según tipo de atracción
            JLabel icono = new JLabel();
            if (nombre.toLowerCase().contains("montaña") || nombre.toLowerCase().contains("roller")) {
                icono.setIcon(new ImageIcon("imagenes/roller-coaster.png")); // Asegúrate de tener este icono
            } else {
                icono.setIcon(new ImageIcon("imagenes/carousel.png")); // Icono genérico
            }

            // Texto de la atracción
            JLabel lbl = new JLabel("<html><b>" + nombre + "</b><br>" +
                                  "Categoría: " + categoria + " | " +
                                  "Cupo: " + cupoMaximo + " personas</html>");
            lbl.setFont(itemFont);
            lbl.setForeground(textColor);

            itemPanel.add(icono);
            itemPanel.add(Box.createHorizontalStrut(10));
            itemPanel.add(lbl);
            
            panelLista.add(itemPanel);
            panelLista.add(Box.createVerticalStrut(5));
        }

        // Scroll pane con estilo transparente
        JScrollPane scrollPane = new JScrollPane(panelLista);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Scroll más suave

        // Panel contenedor para márgenes
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setOpaque(false);
        containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 30, 30));
        containerPanel.add(scrollPane, BorderLayout.CENTER);

        add(containerPanel, BorderLayout.CENTER);
    }
}