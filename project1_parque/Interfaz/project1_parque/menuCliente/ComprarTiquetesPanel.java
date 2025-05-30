package project1_parque.menuCliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import sistema_parque.lugaresServicio.Taquilla;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.tiquetes.*;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Usuario;

public class ComprarTiquetesPanel extends JPanel {

    private PrincipalParque parquePrincipal;
    private Usuario cliente;
    private Taquilla taquilla;

    private JPanel panelResultados;
    private List<Tiquete> tiquetes;

    public ComprarTiquetesPanel(PrincipalParque parquePrincipal, Usuario usuarioAutenticado) {
        this.parquePrincipal = parquePrincipal;
        this.cliente = usuarioAutenticado;
        this.taquilla = new Taquilla();
        this.tiquetes = parquePrincipal.getListaTiquetes();

        // Cargar tiquetes desde el parque a la taquilla
        for (Tiquete tiquete : tiquetes) {
            taquilla.getListaTiquetesVender().add(tiquete);
        }

        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("🎟 Lista de tiquetes disponibles");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titulo, BorderLayout.NORTH);

        // Panel de resultados con scroll
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(panelResultados);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        mostrarTodosLosTiquetes();
    }

    private void mostrarTodosLosTiquetes() {
        panelResultados.removeAll();

        List<Tiquete> disponibles = taquilla.getListaTiquetesVender();

        if (disponibles.isEmpty()) {
            panelResultados.add(new JLabel("No hay tiquetes disponibles en la taquilla."));
        } else {
            for (Tiquete t : disponibles) {
                String tipo;
                if (t instanceof TiqueteTemporada) {
                    tipo = "TEMPORADA";
                } else if (t instanceof FastPass) {
                    tipo = "FASTPASS";
                } else if (t instanceof TiqueteIndividual) {
                    tipo = "INDIVIDUAL";
                } else {
                    tipo = "DESCONOCIDO";
                }

                JButton botonTiquete = new JButton("🎟 ID: " + t.getId() + " | Expira: " + t.getFechaExpiracion() + " | Tipo: " + tipo);
                botonTiquete.setFont(new Font("SansSerif", Font.PLAIN, 14));
                botonTiquete.setAlignmentX(Component.LEFT_ALIGNMENT);
                botonTiquete.setBackground(new Color(230, 245, 255));
                botonTiquete.setFocusPainted(false);

                botonTiquete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int confirmacion = JOptionPane.showConfirmDialog(
                                ComprarTiquetesPanel.this,
                                "¿Está seguro de que desea comprar este tiquete?",
                                "Confirmar compra",
                                JOptionPane.YES_NO_OPTION
                        );

                        if (confirmacion == JOptionPane.YES_OPTION) {
                            if (cliente instanceof Cliente) {
                                Cliente clienteReal = (Cliente) cliente;

                                // Registrar la venta
                                taquilla.registrarVenta(clienteReal, t);

                                // Eliminar de la lista de venta
                                taquilla.getListaTiquetesVender().remove(t);

                                // Notificar éxito
                                JOptionPane.showMessageDialog(
                                        ComprarTiquetesPanel.this,
                                        "✅ Tiquete añadido exitosamente.",
                                        "Compra realizada",
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                // Refrescar la interfaz
                                tiquetes.remove(t); // también lo eliminamos del parque
                                mostrarTodosLosTiquetes();
                            } else {
                                JOptionPane.showMessageDialog(
                                        ComprarTiquetesPanel.this,
                                        "❌ Error: El usuario no es un cliente válido.",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    }
                });

                panelResultados.add(botonTiquete);
                panelResultados.add(Box.createVerticalStrut(5)); // Espacio entre botones
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}
