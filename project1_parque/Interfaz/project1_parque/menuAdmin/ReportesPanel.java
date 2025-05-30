package project1_parque.menuAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.usuarios.Empleado;
import sistema_parque.tiquetes.Tiquete;

public class ReportesPanel extends JPanel {
    private PrincipalParque parquePrincipal;
    private JTextArea areaReportes;
    private JTabbedPane pestañas; // Declaración añadida
    private JTable tablaAtracciones; // Declaración añadida
    private DefaultTableModel modeloAtracciones; // Declaración añadida

    public ReportesPanel(PrincipalParque parque) {
        this.parquePrincipal = parque;
        setLayout(new BorderLayout());

        // Panel superior con título y fecha
        JPanel panelSuperior = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Reportes del Parque", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        
        String fechaActual = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
        JLabel lblFecha = new JLabel("Generado: " + fechaActual, SwingConstants.RIGHT);
        
        panelSuperior.add(titulo, BorderLayout.CENTER);
        panelSuperior.add(lblFecha, BorderLayout.EAST);
        add(panelSuperior, BorderLayout.NORTH);

        // Inicialización del JTabbedPane
        pestañas = new JTabbedPane();
        
        // Pestaña 1: Reporte resumido
        pestañas.addTab("Resumen", crearPanelResumen());
        
        // Pestaña 2: Atracciones
        pestañas.addTab("Atracciones", crearPanelAtracciones());
        
        // Pestaña 3: Empleados
        pestañas.addTab("Empleados", crearPanelEmpleados());
        
        // Pestaña 4: Tiquetes
        pestañas.addTab("Tiquetes", crearPanelTiquetes());

        add(pestañas, BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGenerarPDF = new JButton("Generar PDF");
        JButton btnImprimir = new JButton("Imprimir");
        
        btnGenerarPDF.addActionListener(e -> generarPDF());
        btnImprimir.addActionListener(e -> imprimirReporte());
        
        panelBotones.add(btnGenerarPDF);
        panelBotones.add(btnImprimir);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private JPanel crearPanelResumen() {
        JPanel panel = new JPanel(new BorderLayout());
        areaReportes = new JTextArea();
        areaReportes.setEditable(false);
        areaReportes.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        generarReporteResumen();
        
        panel.add(new JScrollPane(areaReportes), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelAtracciones() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Nombre", "Ubicación", "Tipo", "Estado", "Cupo", "Empleados Req."};
        modeloAtracciones = new DefaultTableModel(columnas, 0);
        
        tablaAtracciones = new JTable(modeloAtracciones);
        cargarDatosAtracciones();
        
        panel.add(new JScrollPane(tablaAtracciones), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelEmpleados() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"Nombre", "Rol", "Turno", "Capacitaciones"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        
        for (Empleado empleado : parquePrincipal.getListaEmpleados()) {
            Object[] fila = {
                empleado.getNombre(),
                empleado.getRol(),
                empleado.isTurnoDiurno() ? "Diurno" : (empleado.isTurnoNocturno() ? "Nocturno" : "No asignado"),
                String.join(", ", empleado.getCapacitaciones())
            };
            modelo.addRow(fila);
        }
        
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelTiquetes() {
        JPanel panel = new JPanel(new BorderLayout());
        
        String[] columnas = {"ID", "Tipo", "Fecha Exp.", "Estado", "Atracción"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);
        JTable tabla = new JTable(modelo);
        
        if (parquePrincipal.getListaTiquetes() != null) {
            for (Tiquete tiquete : parquePrincipal.getListaTiquetes()) {
                Object[] fila = {
                    tiquete.getId(),
                    tiquete.getTipo(),
                    tiquete.getFechaExpiracion() != null ? tiquete.getFechaExpiracion().toString() : "N/A",
                    tiquete.isFueUsado() ? "Usado" : "Disponible",
                    tiquete.getAtraccion() != null ? tiquete.getAtraccion().getNombre() : "N/A"
                };
                modelo.addRow(fila);
            }
        }
        
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        return panel;
    }

    private void generarReporteResumen() {
        StringBuilder reporte = new StringBuilder();
        
        reporte.append("=== REPORTE GENERAL DEL PARQUE ===\n\n");
        reporte.append(String.format("%-25s: %d\n", "Total Atracciones", parquePrincipal.getListaAtracciones().size()));
        reporte.append(String.format("%-25s: %d\n", "Total Empleados", parquePrincipal.getListaEmpleados().size()));
        reporte.append(String.format("%-25s: %d\n", "Total Tiquetes", parquePrincipal.getListaTiquetes() != null ? parquePrincipal.getListaTiquetes().size() : 0));
        
        reporte.append("\n=== ESTADO ATRACCIONES ===\n");
        for (Atraccion atraccion : parquePrincipal.getListaAtracciones()) {
            reporte.append(String.format("%-20s: %s\n", 
                atraccion.getNombre(), 
                atraccion.estaCerrada() ? "CERRADA" : "ABIERTA"));
        }
        
        areaReportes.setText(reporte.toString());
    }

    private void cargarDatosAtracciones() {
        modeloAtracciones.setRowCount(0);
        
        for (Atraccion atraccion : parquePrincipal.getListaAtracciones()) {
            Object[] fila = {
                atraccion.getNombre(),
                atraccion.getUbicacion(),
                atraccion.getClass().getSimpleName(),
                atraccion.estaCerrada() ? "Cerrada" : "Abierta",
                atraccion.getCupoMaximo(),
                atraccion.getEmpleadosMinimos()
            };
            modeloAtracciones.addRow(fila);
        }
    }

    public void mostrarPestana(int indice) {
        if (pestañas != null && indice >= 0 && indice < pestañas.getTabCount()) {
            pestañas.setSelectedIndex(indice);
        }
    }

    public void actualizarReportes() {
        generarReporteResumen();
        cargarDatosAtracciones();
        // Las otras pestañas se actualizan automáticamente al crearse
    }

    private void generarPDF() {
        JOptionPane.showMessageDialog(this, 
            "Función de generar PDF en desarrollo", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void imprimirReporte() {
        JOptionPane.showMessageDialog(this, 
            "Función de impresión en desarrollo", 
            "Información", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}