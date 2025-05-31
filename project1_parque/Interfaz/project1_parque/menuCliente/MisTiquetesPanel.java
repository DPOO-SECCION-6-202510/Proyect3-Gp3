package project1_parque.menuCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Usuario;

public class MisTiquetesPanel extends JPanel {

    private PrincipalParque parquePrincipal;
    private Usuario cliente;

    public MisTiquetesPanel(PrincipalParque parquePrincipal, Usuario cliente) {
        this.parquePrincipal = parquePrincipal;
        this.cliente = cliente;

        // Configuración del panel principal
        setOpaque(false); // Transparente para el fondo
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel contenedor del título con recuadro azul
        JPanel panelTitulo = new JPanel();
        panelTitulo.setOpaque(false);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

        // Título con recuadro azul
        JLabel titulo = new JLabel("🎟 Mis Tiquetes No Usados");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(70, 130, 180, 180)); // Fondo azul semi-transparente
        titulo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 2),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);

        panelTitulo.add(titulo);
        add(panelTitulo, BorderLayout.NORTH);

        // Panel de tiquetes con scroll - transparente
        JPanel panelTiquetes = new JPanel();
        panelTiquetes.setOpaque(false);
        panelTiquetes.setLayout(new BoxLayout(panelTiquetes, BoxLayout.Y_AXIS));
        panelTiquetes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelTiquetes);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Estilo de la barra de scroll
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(16);
        verticalScrollBar.setBackground(new Color(70, 130, 180, 150));
        verticalScrollBar.setForeground(Color.WHITE);

        add(scrollPane, BorderLayout.CENTER);

        // Solo si el usuario es realmente un Cliente
        if (cliente instanceof Cliente) {
            List<Tiquete> tiquetes = ((Cliente) cliente).getListaTiquetesNoUsados();

            if (tiquetes.isEmpty()) {
                // Panel para el mensaje de "no hay tiquetes" con recuadro
                JPanel panelMensaje = new JPanel();
                panelMensaje.setOpaque(false);
                panelMensaje.setLayout(new FlowLayout(FlowLayout.CENTER));
                panelMensaje.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

                JLabel mensaje = new JLabel("No tienes tiquetes no usados.");
                mensaje.setFont(new Font("Arial", Font.PLAIN, 14));
                mensaje.setForeground(Color.WHITE);
                mensaje.setOpaque(true);
                mensaje.setBackground(new Color(70, 130, 180, 120));
                mensaje.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
                mensaje.setHorizontalAlignment(SwingConstants.CENTER);

                panelMensaje.add(mensaje);
                panelTiquetes.add(panelMensaje);
            } else {
                for (Tiquete t : tiquetes) {
                    JButton botonTiquete = crearBotonTiquete(t);
                    panelTiquetes.add(botonTiquete);
                    panelTiquetes.add(Box.createVerticalStrut(8)); // Espacio entre botones
                }
            }
        } else {
            // Panel para mensaje de error con recuadro
            JPanel panelError = new JPanel();
            panelError.setOpaque(false);
            panelError.setLayout(new FlowLayout(FlowLayout.CENTER));
            panelError.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

            JLabel errorLabel = new JLabel("❌ Este usuario no es un cliente válido.");
            errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            errorLabel.setForeground(Color.WHITE);
            errorLabel.setOpaque(true);
            errorLabel.setBackground(new Color(180, 70, 70, 150)); // Fondo rojo para error
            errorLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
            ));
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

            panelError.add(errorLabel);
            panelTiquetes.add(panelError);
        }

        revalidate();
        repaint();
    }

    private JButton crearBotonTiquete(Tiquete t) {
        JButton botonTiquete = new JButton(
            String.format("🎫 ID: %s | Expira: %s", t.getId(), t.getFechaExpiracion())
        );
        
        // Estilo del botón
        botonTiquete.setFont(new Font("Arial", Font.BOLD, 14));
        botonTiquete.setAlignmentX(Component.LEFT_ALIGNMENT);
        botonTiquete.setBackground(new Color(80, 140, 200, 180)); // Fondo azul semi-transparente
        botonTiquete.setForeground(Color.WHITE);
        botonTiquete.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        botonTiquete.setFocusPainted(false);
        botonTiquete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        botonTiquete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botonTiquete.setBackground(new Color(60, 120, 180, 220));
                botonTiquete.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 200), 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botonTiquete.setBackground(new Color(80, 140, 200, 180));
                botonTiquete.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 150), 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
            }
        });

        botonTiquete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre ventana ImprimirFrame con el tiquete
                new ImprimirFrame(t).setVisible(true);
            }
        });

        return botonTiquete;
    }
}