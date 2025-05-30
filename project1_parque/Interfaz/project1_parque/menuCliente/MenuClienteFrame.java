package project1_parque.menuCliente;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuClienteFrame extends JFrame {

    private PrincipalParque parquePrincipal;
    private String nombreCliente;
    private JPanel panelContenido;
    private ImageIcon imagenFondo;

    public MenuClienteFrame(String nombre, Usuario usuarioAutenticado, PrincipalParque parquePrincipal) {
        this.nombreCliente = nombre;
        this.parquePrincipal = parquePrincipal;

        // Configuración básica del frame
        setTitle("Menú Cliente - " + nombre);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Cargar imagen de fondo
        try {
            imagenFondo = new ImageIcon("imagenes/imagenParque.jpg");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No se pudo cargar la imagen de fondo", "Error", JOptionPane.ERROR_MESSAGE);
            imagenFondo = null;
        }

        // Panel principal con imagen de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (imagenFondo != null) {
                    g.drawImage(imagenFondo.getImage(), 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panelPrincipal.setOpaque(false);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Crear botones con efecto hover
        JButton btnAtracciones = crearBotonConHover("Ver Atracciones");
        JButton btnComprar = crearBotonConHover("Comprar Tiquetes");
        JButton btnMisTiquetes = crearBotonConHover("Mis Tiquetes");

        // Configurar tamaño de botones
        Dimension buttonSize = new Dimension(200, 50);
        btnAtracciones.setPreferredSize(buttonSize);
        btnComprar.setPreferredSize(buttonSize);
        btnMisTiquetes.setPreferredSize(buttonSize);

        panelBotones.add(btnAtracciones);
        panelBotones.add(btnComprar);
        panelBotones.add(btnMisTiquetes);

        // Panel de contenido
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setOpaque(false);

        // Añadir componentes al panel principal
        panelPrincipal.add(panelBotones, BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        // Añadir panel principal al frame
        add(panelPrincipal);

        // Acciones de los botones (mantenemos la misma lógica)
        btnAtracciones.addActionListener(e -> mostrarPanel(new AtraccionesPanel(parquePrincipal)));
        btnComprar.addActionListener(e -> mostrarPanel(new ComprarTiquetesPanel(parquePrincipal, usuarioAutenticado)));
        btnMisTiquetes.addActionListener(e -> mostrarPanel(new MisTiquetesPanel(parquePrincipal, usuarioAutenticado)));
    }

    private JButton crearBotonConHover(String texto) {
        JButton boton = new JButton(texto);
        
        // Estilo normal del botón
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(new Color(240, 240, 240)); // Texto casi blanco
        boton.setBackground(new Color(70, 130, 180, 150)); // Fondo azul semi-transparente
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Efecto hover - oscurece el texto y el fondo
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setForeground(new Color(200, 200, 200)); // Texto más oscuro
                boton.setBackground(new Color(50, 100, 150, 180)); // Fondo más oscuro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setForeground(new Color(240, 240, 240)); // Texto original
                boton.setBackground(new Color(70, 130, 180, 150)); // Fondo original
            }
        });

        return boton;
    }

    private void mostrarPanel(JPanel nuevoPanel) {
        panelContenido.removeAll();
        if (nuevoPanel instanceof JComponent) {
            ((JComponent) nuevoPanel).setOpaque(false);
        }
        panelContenido.add(nuevoPanel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}