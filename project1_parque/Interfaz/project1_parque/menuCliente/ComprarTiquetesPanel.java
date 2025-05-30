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

        // Configuración del panel principal
        setOpaque(false); // Hacemos el panel transparente para ver el fondo
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Cargar tiquetes desde el parque a la taquilla
        for (Tiquete tiquete : tiquetes) {
            taquilla.getListaTiquetesVender().add(tiquete);
        }

        // Título con estilo mejorado
        JLabel titulo = new JLabel("🎟 Lista de tiquetes disponibles");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(titulo, BorderLayout.NORTH);

        // Panel de resultados con scroll - transparente
        panelResultados = new JPanel();
        panelResultados.setOpaque(false);
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(panelResultados);
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

        mostrarTodosLosTiquetes();
    }

    private void mostrarTodosLosTiquetes() {
        panelResultados.removeAll();

        List<Tiquete> disponibles = taquilla.getListaTiquetesVender();

        if (disponibles.isEmpty()) {
            JLabel mensaje = new JLabel("No hay tiquetes disponibles en la taquilla.");
            mensaje.setFont(new Font("Arial", Font.PLAIN, 14));
            mensaje.setForeground(Color.WHITE);
            mensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelResultados.add(mensaje);
        } else {
            for (Tiquete t : disponibles) {
                String tipo;
                Color colorTipo;
                
                if (t instanceof TiqueteTemporada) {
                    tipo = "TEMPORADA";
                    colorTipo = new Color(100, 200, 100); // Verde
                } else if (t instanceof FastPass) {
                    tipo = "FASTPASS";
                    colorTipo = new Color(255, 180, 0); // Amarillo/naranja
                } else if (t instanceof TiqueteIndividual) {
                    tipo = "INDIVIDUAL";
                    colorTipo = new Color(100, 180, 255); // Azul claro
                } else {
                    tipo = "DESCONOCIDO";
                    colorTipo = Color.GRAY;
                }

                JButton botonTiquete = new JButton(
                    "🎟 ID: " + t.getId() + " | Expira: " + t.getFechaExpiracion() + " | Tipo: " + tipo
                );
                
                // Estilo del botón
                botonTiquete.setFont(new Font("Arial", Font.BOLD, 14));
                botonTiquete.setAlignmentX(Component.LEFT_ALIGNMENT);
                botonTiquete.setBackground(new Color(70, 130, 180, 180)); // Fondo azul semi-transparente
                botonTiquete.setForeground(Color.WHITE);
                botonTiquete.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1),
                    BorderFactory.createEmptyBorder(8, 15, 8, 15)
                ));
                botonTiquete.setFocusPainted(false);
                botonTiquete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
                // Etiqueta del tipo con color
                botonTiquete.setText(String.format(
                    "<html>🎟 ID: %s | Expira: %s | Tipo: <font color='#%02x%02x%02x'>%s</font></html>",
                    t.getId(), 
                    t.getFechaExpiracion(),
                    colorTipo.getRed(), colorTipo.getGreen(), colorTipo.getBlue(),
                    tipo
                ));

                // Efecto hover
                botonTiquete.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        botonTiquete.setBackground(new Color(50, 100, 150, 220));
                    }
                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        botonTiquete.setBackground(new Color(70, 130, 180, 180));
                    }
                });

                botonTiquete.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int confirmacion = JOptionPane.showConfirmDialog(
                                ComprarTiquetesPanel.this,
                                "¿Está seguro de que desea comprar este tiquete?",
                                "Confirmar compra",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirmacion == JOptionPane.YES_OPTION) {
                            if (cliente instanceof Cliente) {
                                Cliente clienteReal = (Cliente) cliente;

                                // Registrar la venta
                                taquilla.registrarVenta(clienteReal, t);

                                // Eliminar de la lista de venta
                                taquilla.getListaTiquetesVender().remove(t);

                                // Notificar éxito con estilo
                                JOptionPane optionPane = new JOptionPane(
                                    "✅ Tiquete añadido exitosamente.",
                                    JOptionPane.INFORMATION_MESSAGE
                                );
                                JDialog dialog = optionPane.createDialog("Compra realizada");
                                dialog.setVisible(true);

                                // Refrescar la interfaz
                                tiquetes.remove(t);
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
                panelResultados.add(Box.createVerticalStrut(8)); // Espacio entre botones
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}