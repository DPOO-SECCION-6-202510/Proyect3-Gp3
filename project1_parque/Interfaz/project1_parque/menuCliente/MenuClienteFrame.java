package project1_parque.menuCliente;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuClienteFrame extends JFrame {

    private PrincipalParque parquePrincipal;
    private String nombreCliente;
    private JPanel panelContenido;

    public MenuClienteFrame(String nombre, Usuario usuarioAutenticado, PrincipalParque parquePrincipal) {
        this.nombreCliente = nombre;
        this.parquePrincipal = parquePrincipal;

        setTitle("Menú Cliente - " + nombre);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnAtracciones = new JButton("Ver Atracciones");
        JButton btnComprar = new JButton("Comprar Tiquetes");
        JButton btnMisTiquetes = new JButton("Mis Tiquetes");

        panelBotones.add(btnAtracciones);
        panelBotones.add(btnComprar);
        panelBotones.add(btnMisTiquetes);

        add(panelBotones, BorderLayout.NORTH);

        // Panel de contenido que cambia
        panelContenido = new JPanel(new BorderLayout());
        add(panelContenido, BorderLayout.CENTER);

        // Acciones de botones
        btnAtracciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(new AtraccionesPanel(parquePrincipal));
            }
        });

        btnComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(new ComprarTiquetesPanel(parquePrincipal, usuarioAutenticado));
            }
        });

        btnMisTiquetes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPanel(new MisTiquetesPanel(parquePrincipal, nombreCliente));
            }
        });
    }

    private void mostrarPanel(JPanel nuevoPanel) {
        panelContenido.removeAll();
        panelContenido.add(nuevoPanel, BorderLayout.CENTER);
        panelContenido.revalidate();
        panelContenido.repaint();
    }
}
