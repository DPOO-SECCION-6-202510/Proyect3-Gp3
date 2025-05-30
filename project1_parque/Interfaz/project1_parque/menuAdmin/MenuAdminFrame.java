package project1_parque.menuAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistema_parque.sisParque.PrincipalParque;
import CONTROL_PERSISTENCIA.CentralPersistencia;
import CONTROL_PERSISTENCIA.PersistenciaParque;

public class MenuAdminFrame extends JFrame {

    private PrincipalParque parquePrincipal;
    private JPanel panelContenedor;
    private CardLayout cardLayout;
    private String nombreAdministrador;
    private static final String ARCHIVO_DATOS_PARQUE = "data/parque.json";

    public MenuAdminFrame(String nombreAdmin, PrincipalParque parque) {
        this.nombreAdministrador = nombreAdmin;
        this.parquePrincipal = parque;

        // Configuración básica de la ventana
        setTitle("Menú de Administración - " + nombreAdmin + " - Parque de Atracciones");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        // Configuración del cierre de ventana
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                confirmarSalida();
            }
        });

        // Inicialización del CardLayout
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // Creación de los paneles de gestión
        GestionAtraccionesPanel gestionAtraccionesPanel = new GestionAtraccionesPanel(parquePrincipal);
        GestionEmpleadosPanel gestionEmpleadosPanel = new GestionEmpleadosPanel(parquePrincipal);
        
        // Añadir paneles al CardLayout con identificadores únicos
        panelContenedor.add(gestionAtraccionesPanel, "ATRACCIONES");
        panelContenedor.add(gestionEmpleadosPanel, "EMPLEADOS");

        // Configuración de la barra de menú
        configurarBarraMenu();

        // Añadir panel contenedor al JFrame
        add(panelContenedor, BorderLayout.CENTER);

        // Mostrar panel de atracciones por defecto
        cardLayout.show(panelContenedor, "ATRACCIONES");
    }

    private void configurarBarraMenu() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menú Gestión
        JMenu menuGestion = new JMenu("Gestión");
        JMenuItem itemAtracciones = new JMenuItem("Gestión de Atracciones");
        JMenuItem itemEmpleados = new JMenuItem("Gestión de Empleados");
        
        itemAtracciones.addActionListener(e -> mostrarPanel("ATRACCIONES"));
        itemEmpleados.addActionListener(e -> mostrarPanel("EMPLEADOS"));
        
        menuGestion.add(itemAtracciones);
        menuGestion.add(itemEmpleados);

        // Menú Salir
        JMenu menuSalir = new JMenu("Salir");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem itemSalirApp = new JMenuItem("Salir de la Aplicación");
        
        itemCerrarSesion.addActionListener(e -> confirmarCierreSesion());
        itemSalirApp.addActionListener(e -> confirmarSalida());
        
        menuSalir.add(itemCerrarSesion);
        menuSalir.add(itemSalirApp);

        // Añadir menús a la barra
        menuBar.add(menuGestion);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
    }

    private void mostrarPanel(String nombrePanel) {
        System.out.println("Intentando mostrar panel: " + nombrePanel); // Depuración
        cardLayout.show(panelContenedor, nombrePanel);
        
        // Forzar actualización de datos al cambiar de panel
        if ("ATRACCIONES".equals(nombrePanel)) {
            ((GestionAtraccionesPanel) panelContenedor.getComponent(0)).cargarAtracciones();
        } else if ("EMPLEADOS".equals(nombrePanel)) {
            ((GestionEmpleadosPanel) panelContenedor.getComponent(1)).cargarEmpleados();
        }
    }

    private void confirmarCierreSesion() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro que quieres cerrar sesión?",
                "Confirmar Cierre de Sesión",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            guardarDatosParque();
            dispose();
            new project1_parque.inicio.LoginFrame().setVisible(true);
        }
    }

    private void confirmarSalida() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro que quieres salir de la aplicación? Los cambios se guardarán.",
                "Confirmar Salida",
                JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            guardarDatosParque();
            System.exit(0);
        }
    }

    private void guardarDatosParque() {
        CentralPersistencia central = new CentralPersistencia();
        PersistenciaParque persistencia = central.getPersistenciaParque(CentralPersistencia.JSON);
        try {
            persistencia.salvarParque(ARCHIVO_DATOS_PARQUE, parquePrincipal);
            JOptionPane.showMessageDialog(this, 
                "Datos del parque guardados exitosamente.", 
                "Guardado", 
                JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al guardar los datos del parque: " + ex.getMessage(), 
                "Error de Guardado", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}