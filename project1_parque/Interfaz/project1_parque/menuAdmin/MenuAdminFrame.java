package project1_parque.menuAdmin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sistema_parque.sisParque.PrincipalParque; // Importa tu clase PrincipalParque
import CONTROL_PERSISTENCIA.CentralPersistencia; // Para guardar datos al cerrar
import CONTROL_PERSISTENCIA.PersistenciaParque;

public class MenuAdminFrame extends JFrame {

    private PrincipalParque parquePrincipal;
    private JPanel panelContenedor; // Este panel usará CardLayout
    private CardLayout cardLayout;   // El layout manager para cambiar paneles
    private String nombreAdministrador;
    private static final String ARCHIVO_DATOS_PARQUE = "data/parque.json"; // Ruta del archivo JSON

    public MenuAdminFrame(String nombreAdmin, PrincipalParque parque) {
        this.nombreAdministrador = nombreAdmin;
        this.parquePrincipal = parque;

        setTitle("Menú de Administración - " + nombreAdmin + " - Parque de Atracciones");
        setSize(1000, 700); // Tamaño adecuado para una aplicación de gestión
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Manejar el cierre manualmente
        setLocationRelativeTo(null); // Centra la ventana

        // --- Manejo del cierre de la ventana para guardar datos ---
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                int confirm = JOptionPane.showConfirmDialog(MenuAdminFrame.this,
                        "¿Estás seguro que quieres salir? Los cambios se guardarán automáticamente.",
                        "Confirmar Salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    guardarDatosParque();
                    System.exit(0);
                }
            }
        });

        // --- Configuración de CardLayout ---
        cardLayout = new CardLayout();
        panelContenedor = new JPanel(cardLayout);

        // --- Creación de los paneles de gestión ---
        // Asegúrate de que los constructores de estos paneles reciban PrincipalParque
        GestionAtraccionesPanel gestionAtraccionesPanel = new GestionAtraccionesPanel(parquePrincipal);
        //GestionEmpleadosPanel gestionEmpleadosPanel = new GestionEmpleadosPanel(parquePrincipal);
        //ReportesPanel reportesPanel = new ReportesPanel(parquePrincipal);

        // Añadir los paneles al CardLayout
        panelContenedor.add(gestionAtraccionesPanel, "ATRACCIONES");
        //panelContenedor.add(gestionEmpleadosPanel, "EMPLEADOS");
        //panelContenedor.add(reportesPanel, "REPORTES");

        // --- Barra de navegación (JMenuBar o JPanel con botones) ---
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGestion = new JMenu("Gestión");
        JMenu menuReportes = new JMenu("Reportes");
        JMenu menuSalir = new JMenu("Salir");

        JMenuItem itemAtracciones = new JMenuItem("Gestión de Atracciones");
        JMenuItem itemEmpleados = new JMenuItem("Gestión de Empleados");
        JMenuItem itemGenerarReportes = new JMenuItem("Generar Reportes");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem itemSalirApp = new JMenuItem("Salir de la Aplicación");

        menuGestion.add(itemAtracciones);
        menuGestion.add(itemEmpleados);
        menuReportes.add(itemGenerarReportes);
        menuSalir.add(itemCerrarSesion);
        menuSalir.add(itemSalirApp);

        menuBar.add(menuGestion);
        menuBar.add(menuReportes);
        menuBar.add(menuSalir);

        setJMenuBar(menuBar); // Establece la barra de menú en el JFrame

        // --- Acciones de los elementos del menú ---
        itemAtracciones.addActionListener(e -> cardLayout.show(panelContenedor, "ATRACCIONES"));
        itemEmpleados.addActionListener(e -> cardLayout.show(panelContenedor, "EMPLEADOS"));
        itemGenerarReportes.addActionListener(e -> cardLayout.show(panelContenedor, "REPORTES"));

        itemCerrarSesion.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(MenuAdminFrame.this,
                                                        "¿Estás seguro que quieres cerrar sesión?",
                                                        "Confirmar Cierre de Sesión",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                guardarDatosParque(); // Guardar antes de cerrar sesión
                dispose(); // Cierra la ventana actual
                new project1_parque.inicio.LoginFrame().setVisible(true); // Abre la ventana de login
            }
        });

        itemSalirApp.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(MenuAdminFrame.this,
                                                        "¿Estás seguro que quieres salir de la aplicación? Los cambios se guardarán.",
                                                        "Confirmar Salida",
                                                        JOptionPane.YES_NO_OPTION,
                                                        JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                guardarDatosParque();
                System.exit(0);
            }
        });

        // Añadir el panel contenedor al JFrame
        add(panelContenedor, BorderLayout.CENTER);

        // Mostrar el panel de Atracciones por defecto al iniciar
        cardLayout.show(panelContenedor, "ATRACCIONES");
    }

    /**
     * Método para guardar los datos del parque en el archivo JSON.
     * Centraliza la lógica de persistencia para evitar duplicación.
     */
    private void guardarDatosParque() {
        CentralPersistencia central = new CentralPersistencia();
        PersistenciaParque persistencia = central.getPersistenciaParque(CentralPersistencia.JSON);
        try {
            persistencia.salvarParque(ARCHIVO_DATOS_PARQUE, parquePrincipal);
            JOptionPane.showMessageDialog(this, "Datos del parque guardados exitosamente.", "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar los datos del parque: " + ex.getMessage(), "Error de Guardado", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error al guardar: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    // Puedes añadir un método main para probar solo esta ventana si lo deseas
    // public static void main(String[] args) {
    //     // Esto es solo para pruebas. En la aplicación real, se llama desde LoginFrame.
    //     SwingUtilities.invokeLater(() -> {
    //         PrincipalParque parqueDePrueba = new PrincipalParque();
    //         // Puedes cargar algunos datos de prueba aquí si quieres
    //         new MenuAdminFrame("Admin de Prueba", parqueDePrueba).setVisible(true);
    //     });
    // }
}