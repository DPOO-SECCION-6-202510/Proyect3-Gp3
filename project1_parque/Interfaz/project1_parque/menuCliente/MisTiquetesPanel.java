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

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("🎟 Mis Tiquetes No Usados");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel panelTiquetes = new JPanel();
        panelTiquetes.setLayout(new BoxLayout(panelTiquetes, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(panelTiquetes);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Solo si el usuario es realmente un Cliente
        if (cliente instanceof Cliente) {
            List<Tiquete> tiquetes = ((Cliente) cliente).getListaTiquetesNoUsados();

            if (tiquetes.isEmpty()) {
                panelTiquetes.add(new JLabel("No tienes tiquetes no usados."));
            } else {
                for (Tiquete t : tiquetes) {
                    JButton botonTiquete = new JButton("🎫 ID: " + t.getId() + " | Expira: " + t.getFechaExpiracion());
                    botonTiquete.setFont(new Font("SansSerif", Font.PLAIN, 14));
                    botonTiquete.setAlignmentX(Component.LEFT_ALIGNMENT);
                    botonTiquete.setBackground(new Color(240, 255, 250));
                    botonTiquete.setFocusPainted(false);

                    botonTiquete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Abre ventana ImprimirFrame con el tiquete
                            new ImprimirFrame(t).setVisible(true);
                        }
                    });

                    panelTiquetes.add(botonTiquete);
                    panelTiquetes.add(Box.createVerticalStrut(5)); // Espacio entre botones
                }
            }
        } else {
            panelTiquetes.add(new JLabel("❌ Este usuario no es un cliente válido."));
        }

        revalidate();
        repaint();
    }
}
