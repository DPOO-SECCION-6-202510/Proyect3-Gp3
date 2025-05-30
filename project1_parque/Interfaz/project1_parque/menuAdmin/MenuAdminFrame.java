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
    private ReportesPanel reportesPanel;

    public MenuAdminFrame(String nombreAdmin, PrincipalParque parque) {
        this.nombreAdministrador = nombreAdmin;
        this.parquePrincipal = parque;

        // Configuración básica de la ventana
        setTitle("Menú de Administración - " + nombreAdmin + " - Parque de Atracciones");
        setSize(1200, 800); // Aumenté el tamaño para mejor visualización
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
        this.reportesPanel = new ReportesPanel(parquePrincipal); // Panel de reportes

        // Añadir paneles al CardLayout con identificadores únicos
        panelContenedor.add(gestionAtraccionesPanel, "ATRACCIONES");
        panelContenedor.add(gestionEmpleadosPanel, "EMPLEADOS");
        panelContenedor.add(reportesPanel, "REPORTES");

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

        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes");
        JMenuItem itemReporteGeneral = new JMenuItem("Reporte General");
        JMenuItem itemReporteAtracciones = new JMenuItem("Reporte de Atracciones");
        JMenuItem itemReporteEmpleados = new JMenuItem("Reporte de Empleados");
        JMenuItem itemReporteTiquetes = new JMenuItem("Reporte de Tiquetes");
        
        itemReporteGeneral.addActionListener(e -> {
            mostrarPanel("REPORTES");
            reportesPanel.mostrarPestana(0); // Mostrar pestaña de resumen
        });
        
        itemReporteAtracciones.addActionListener(e -> {
            mostrarPanel("REPORTES");
            reportesPanel.mostrarPestana(1); // Mostrar pestaña de atracciones
        });
        
        itemReporteEmpleados.addActionListener(e -> {
            mostrarPanel("REPORTES");
            reportesPanel.mostrarPestana(2); // Mostrar pestaña de empleados
        });
        
        itemReporteTiquetes.addActionListener(e -> {
            mostrarPanel("REPORTES");
            reportesPanel.mostrarPestana(3); // Mostrar pestaña de tiquetes
        });
        
        menuReportes.add(itemReporteGeneral);
        menuReportes.add(itemReporteAtracciones);
        menuReportes.add(itemReporteEmpleados);
        menuReportes.add(itemReporteTiquetes);

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
        menuBar.add(menuReportes);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar);
    }

    private void mostrarPanel(String nombrePanel) {
        cardLayout.show(panelContenedor, nombrePanel);
        
        // Actualizar datos cuando se muestra el panel de reportes
        if ("REPORTES".equals(nombrePanel)) {
            reportesPanel.actualizarReportes();
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