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

        // Título con estilo mejorado
        JLabel titulo = new JLabel("🎟 Mis Tiquetes No Usados");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

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
                JLabel mensaje = new JLabel("No tienes tiquetes no usados.");
                mensaje.setFont(new Font("Arial", Font.PLAIN, 14));
                mensaje.setForeground(Color.WHITE);
                mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
                panelTiquetes.add(mensaje);
            } else {
                for (Tiquete t : tiquetes) {
                    JButton botonTiquete = crearBotonTiquete(t);
                    panelTiquetes.add(botonTiquete);
                    panelTiquetes.add(Box.createVerticalStrut(8)); // Espacio entre botones
                }
            }
        } else {
            JLabel errorLabel = new JLabel("❌ Este usuario no es un cliente válido.");
            errorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            errorLabel.setForeground(new Color(255, 100, 100));
            errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelTiquetes.add(errorLabel);
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