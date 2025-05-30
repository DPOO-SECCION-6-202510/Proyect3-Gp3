package project1_parque.menuEmpleado;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Empleado;
import sistema_parque.atracciones.Atraccion;
import javax.swing.*;
import javax.swing.border.Border;

import project1_parque.inicio.LoginFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MenuEmpleadoFrame extends JFrame {
    
    private PrincipalParque parquePrincipal;
    private String nombreEmpleado;
    
    public MenuEmpleadoFrame(String nombre, PrincipalParque parquePrincipal) {
        this.nombreEmpleado = nombre;
        this.parquePrincipal = parquePrincipal;
        initializeComponents();
    }
    
    private void initializeComponents() {
        setTitle("Sistema de Parque - Menú Empleado");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel principal con fondo
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Header con bienvenida
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Panel central con opciones del menú
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.CENTER);
        
        // Panel inferior con botón de cerrar sesión
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setPreferredSize(new Dimension(600, 80));
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Título de bienvenida
        JLabel welcomeLabel = new JLabel("BIENVENIDO/A, " + nombreEmpleado.toUpperCase());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        headerPanel.add(welcomeLabel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(new Color(240, 248, 255));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Título del menú
        JLabel menuTitle = new JLabel("SELECCIONA UNA OPCIÓN");
        menuTitle.setFont(new Font("Courier New", Font.BOLD, 16));
        menuTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        menuPanel.add(menuTitle, gbc);
        
        gbc.gridwidth = 1;
        
        // Botón 1: Ver mis turnos
        JButton btnVerTurnos = createMenuButton("Ver mis turnos");
        btnVerTurnos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verMisTurnos();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(btnVerTurnos, gbc);
        
        // Botón 2: Gestionar atracción asignada
        JButton btnGestionarAtraccion = createMenuButton("Gestionar atracción asignada");
        btnGestionarAtraccion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gestionarAtraccionAsignada();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        menuPanel.add(btnGestionarAtraccion, gbc);
        
        // Botón 3: Ver catálogo de atracciones
        JButton btnCatalogoAtracciones = createMenuButton("Ver catálogo de atracciones");
        btnCatalogoAtracciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verCatalogoAtracciones();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(btnCatalogoAtracciones, gbc);
        
        // Botón 4: Ver información de empleado
        JButton btnInfoEmpleado = createMenuButton("Ver información de empleado");
        btnInfoEmpleado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verInformacionEmpleado();
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 2;
        menuPanel.add(btnInfoEmpleado, gbc);
        
        return menuPanel;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(240, 248, 255));
        footerPanel.setPreferredSize(new Dimension(600, 80));
        footerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Botón 5: Cerrar sesión
        JButton btnCerrarSesion = new JButton("Cerrar sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(200, 40));
        btnCerrarSesion.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrarSesion.setBackground(new Color(220, 20, 60));
        btnCerrarSesion.setForeground(Color.RED);
        btnCerrarSesion.setBorder(BorderFactory.createEmptyBorder());
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        footerPanel.add(btnCerrarSesion);
        
        return footerPanel;
    }
    
    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 45));
        button.setFont(new Font("Arial", Font.PLAIN, 11));
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        
        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237));
            }
        });
        
        return button;
    }
    
    // Métodos para manejar las acciones de los botones
    private void verMisTurnos() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Ver mis turnos\n\nAquí se mostrarían los turnos asignados al empleado: " + nombreEmpleado,
            "Ver Turnos", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: Implementar la lógica para mostrar los turnos del empleado
        // Ejemplo: new VerTurnosFrame(nombreEmpleado, parquePrincipal).setVisible(true);
    }
    
    private void gestionarAtraccionAsignada() {
        JOptionPane.showMessageDialog(this, 
            "Funcionalidad: Gestionar atracción asignada\n\nAquí el empleado podría gestionar su atracción asignada.",
            "Gestionar Atracción", 
            JOptionPane.INFORMATION_MESSAGE);
        
        // TODO: Implementar la lógica para gestionar la atracción asignada
        // Ejemplo: new GestionarAtraccionFrame(nombreEmpleado, parquePrincipal).setVisible(true);
    }
    
    private void verCatalogoAtracciones() {
        // Crear y mostrar la ventana del catálogo de atracciones
        new CatalogoAtraccionesFrame(parquePrincipal).setVisible(true);
    }
    
    private void verInformacionEmpleado() {
        // TODO: Implementar ver información del empleado
    }
    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro que desea cerrar sesión?",
            "Confirmar Cierre de Sesión",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (opcion == JOptionPane.YES_OPTION) {
            new LoginFrame().setVisible(true);
            this.dispose();
        }
    }
    
    // Método para mostrar la ventana
    public void mostrarMenu() {
        setVisible(true);
    }
}

// Clase para la ventana del catálogo de atracciones
class CatalogoAtraccionesFrame extends JFrame {
    private PrincipalParque parquePrincipal;
    private ArrayList<Atraccion> atracciones;
    private JPanel panelPrincipal;
    private JLabel labelTotal;
    
    public CatalogoAtraccionesFrame(PrincipalParque parquePrincipal) {
        this.parquePrincipal = parquePrincipal;
        inicializarAtracciones();
        configurarVentana();
        crearInterfaz();
    }
    
    private void inicializarAtracciones() {
        // Obtener las atracciones del parque principal
        if (parquePrincipal != null && parquePrincipal.getListaAtracciones() != null) {
            atracciones = (ArrayList<Atraccion>) parquePrincipal.getListaAtracciones();
        } else {

            atracciones = new ArrayList<>();
        }
    }
    
    private void configurarVentana() {
        setTitle("Catálogo de Atracciones del Parque");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Color de fondo similar al de la imagen original
        getContentPane().setBackground(new Color(40, 40, 40));
    }
    
    private void crearInterfaz() {
        setLayout(new BorderLayout());
        
        // Panel principal con padding
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(new Color(40, 40, 40));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Título principal
        crearTitulo();
        
        // Total de atracciones
        crearContadorTotal();
        
        // Lista de atracciones
        crearListaAtracciones();
        
        // Panel con scroll
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(new Color(40, 40, 40));
        
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel inferior con botón cerrar
        crearPanelInferior();
    }
    
    private void crearTitulo() {
        JLabel titulo = new JLabel("LISTADO DE ATRACCIONES");
        titulo.setFont(new Font("Monospaced", Font.BOLD, 16));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Crear borde decorativo
        Border bordeTitulo = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        );
        titulo.setBorder(bordeTitulo);
        titulo.setOpaque(true);
        titulo.setBackground(new Color(40, 40, 40));
        
        panelPrincipal.add(titulo);
        panelPrincipal.add(Box.createVerticalStrut(10));
    }
    
    private void crearContadorTotal() {
        labelTotal = new JLabel("Total atracciones: " + atracciones.size());
        labelTotal.setFont(new Font("Monospaced", Font.PLAIN, 12));
        labelTotal.setForeground(Color.WHITE);
        labelTotal.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        panelPrincipal.add(labelTotal);
        panelPrincipal.add(Box.createVerticalStrut(15));
    }
    
    private void crearListaAtracciones() {
        if (atracciones.isEmpty()) {
            JLabel mensajeVacio = new JLabel("No hay atracciones registradas en el parque.");
            mensajeVacio.setFont(new Font("Monospaced", Font.ITALIC, 12));
            mensajeVacio.setForeground(Color.GRAY);
            mensajeVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPrincipal.add(mensajeVacio);
        } else {
            for (Atraccion atraccion : atracciones) {
                JPanel panelAtraccion = crearPanelAtraccion(atraccion);
                panelPrincipal.add(panelAtraccion);
                panelPrincipal.add(Box.createVerticalStrut(5));
            }
        }
    }
    
    private JPanel crearPanelAtraccion(Atraccion atraccion) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(60, 60, 60));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // Panel para el contenido
        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(new Color(60, 60, 60));
        
        // Nombre de la atracción
        JLabel nombre = new JLabel(atraccion.getNombre());
        nombre.setFont(new Font("Monospaced", Font.BOLD, 14));
        nombre.setForeground(Color.WHITE);
        nombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(nombre);
        
        contenido.add(Box.createVerticalStrut(8));
        
        // Detalles de la atracción usando getters de tu clase
        String tipoAtraccion = atraccion.getClass().getSimpleName();
        String detalles = String.format("- Tipo: %s    - Categoría: %s", 
            tipoAtraccion, atraccion.getClasificacionCategoria());
        JLabel labelDetalles = new JLabel(detalles);
        labelDetalles.setFont(new Font("Monospaced", Font.PLAIN, 11));
        labelDetalles.setForeground(Color.LIGHT_GRAY);
        labelDetalles.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(labelDetalles);
        
        // Ubicación si existe
        if (atraccion.getUbicacion() != null && !atraccion.getUbicacion().isEmpty()) {
            JLabel ubicacion = new JLabel("- Ubicación: " + atraccion.getUbicacion());
            ubicacion.setFont(new Font("Monospaced", Font.PLAIN, 11));
            ubicacion.setForeground(Color.LIGHT_GRAY);
            ubicacion.setAlignmentX(Component.LEFT_ALIGNMENT);
            contenido.add(ubicacion);
        }
        
        contenido.add(Box.createVerticalStrut(3));
        
        // Aforo máximo
        String aforoTexto = String.format("- Aforo máximo: %d personas", atraccion.getCupoMaximo());
        JLabel labelAforo = new JLabel(aforoTexto);
        labelAforo.setFont(new Font("Monospaced", Font.PLAIN, 11));
        labelAforo.setForeground(Color.LIGHT_GRAY);
        labelAforo.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(labelAforo);
        
        // Empleados mínimos
        String empleadosTexto = String.format("- Empleados mínimos: %d", atraccion.getEmpleadosMinimos());
        JLabel labelEmpleados = new JLabel(empleadosTexto);
        labelEmpleados.setFont(new Font("Monospaced", Font.PLAIN, 11));
        labelEmpleados.setForeground(Color.LIGHT_GRAY);
        labelEmpleados.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(labelEmpleados);
        
        // Estado de la atracción
        String estadoTexto = "- Estado: " + (Atraccion.estaCerrada() ? "CERRADA" : "ABIERTA");
        JLabel labelEstado = new JLabel(estadoTexto);
        labelEstado.setFont(new Font("Monospaced", Font.PLAIN, 11));
        labelEstado.setForeground(Atraccion.estaCerrada() ? Color.RED : Color.GREEN);
        labelEstado.setAlignmentX(Component.LEFT_ALIGNMENT);
        contenido.add(labelEstado);
        
        panel.add(contenido, BorderLayout.CENTER);
        return panel;
    }
    
    private void crearPanelInferior() {
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(40, 40, 40));
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(100, 35));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(70, 130, 180));
        btnCerrar.setForeground(Color.GRAY);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder());
        btnCerrar.setFocusPainted(false);
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelInferior.add(btnCerrar);
        add(panelInferior, BorderLayout.SOUTH);
    }
}