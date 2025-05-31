package project1_parque.menuEmpleado;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Turno;
import sistema_parque.usuarios.Turno;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.lugaresServicio.LugarServicio;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class TurnosPanel extends JFrame {
    
    private PrincipalParque parquePrincipal;
    private String nombreEmpleado;
    private Empleado empleadoActual;
    private List<Turno> turnosEmpleado;
    private JPanel panelTurnos;
    private JLabel labelTotal;
    private JComboBox<String> filtroTipoTurno;
    private JComboBox<String> filtroSemana;
    
    public TurnosPanel(String nombreEmpleado, PrincipalParque parquePrincipal) {
        this.nombreEmpleado = nombreEmpleado;
        this.parquePrincipal = parquePrincipal;
        this.empleadoActual = parquePrincipal.buscarEmpleadoPorNombre(nombreEmpleado);
        inicializarTurnos();
        configurarVentana();
        crearInterfaz();
    }
    
    private void inicializarTurnos() {
        // Obtener todos los turnos del empleado actual
        turnosEmpleado = new ArrayList<>();
        
        if (parquePrincipal != null && parquePrincipal.getListaTurnos() != null) {
            turnosEmpleado = parquePrincipal.getListaTurnos().stream()
                .filter(turno -> turno.getEmpleadoAsignado() != null && 
                        turno.getEmpleadoAsignado().getNombre().equalsIgnoreCase(nombreEmpleado))
                .collect(Collectors.toList());
        }
        
        // Si no hay método getListaTurnos, crear algunos turnos de ejemplo para prueba
        if (turnosEmpleado.isEmpty()) {
            crearTurnosEjemplo();
        }
    }
    
    private void crearTurnosEjemplo() {
        // Método temporal para crear turnos de ejemplo
        if (empleadoActual != null) {
            // Crear algunos tipos de turno de ejemplo
            TipoTurno diurno = new TipoTurno("Diurno", "06:00", "18:00");
            TipoTurno nocturno = new TipoTurno("Nocturno", "18:00", "06:00");
            
                Object lugar = "Atracción Principal";
                
                // Turno turno = new Turno(empleadoActual, lugar, fecha, tipo);
                // turnosEmpleado.add(turno);
            }
        }
    
    
    private void configurarVentana() {
        setTitle("Mis Turnos - " + nombreEmpleado);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        getContentPane().setBackground(new Color(240, 248, 255));
    }
    
    private void crearInterfaz() {
        setLayout(new BorderLayout());
        
        // Panel superior con título y filtros
        JPanel panelSuperior = crearPanelSuperior();
        add(panelSuperior, BorderLayout.NORTH);
        
        // Panel central con la lista de turnos
        JPanel panelCentral = crearPanelCentral();
        add(panelCentral, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel panelInferior = crearPanelInferior();
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Título
        JLabel titulo = new JLabel("MIS TURNOS ASIGNADOS");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Panel de filtros
        JPanel panelFiltros = crearPanelFiltros();
        
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        panel.add(panelFiltros, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel crearPanelFiltros() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panel.setBackground(new Color(70, 130, 180));
        
        // Filtro por tipo de turno
        JLabel labelFiltro1 = new JLabel("Tipo de turno:");
        labelFiltro1.setForeground(Color.WHITE);
        labelFiltro1.setFont(new Font("Arial", Font.PLAIN, 12));
        
        filtroTipoTurno = new JComboBox<>(new String[]{"Todos", "Diurno", "Nocturno"});
        filtroTipoTurno.setPreferredSize(new Dimension(120, 25));
        filtroTipoTurno.addActionListener(e -> aplicarFiltros());
        
        // Filtro por semana
        JLabel labelFiltro2 = new JLabel("Período:");
        labelFiltro2.setForeground(Color.WHITE);
        labelFiltro2.setFont(new Font("Arial", Font.PLAIN, 12));
        
        filtroSemana = new JComboBox<>(new String[]{"Todos", "Esta semana", "Próxima semana", "Este mes"});
        filtroSemana.setPreferredSize(new Dimension(140, 25));
        filtroSemana.addActionListener(e -> aplicarFiltros());
        
        panel.add(labelFiltro1);
        panel.add(filtroTipoTurno);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(labelFiltro2);
        panel.add(filtroSemana);
        
        return panel;
    }
    
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Contador de turnos
        labelTotal = new JLabel("Total de turnos: " + turnosEmpleado.size());
        labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotal.setForeground(new Color(70, 130, 180));
        labelTotal.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Panel con la lista de turnos
        panelTurnos = new JPanel();
        panelTurnos.setLayout(new BoxLayout(panelTurnos, BoxLayout.Y_AXIS));
        panelTurnos.setBackground(new Color(240, 248, 255));
        
        // Scroll pane para la lista
        JScrollPane scrollPane = new JScrollPane(panelTurnos);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        panel.add(labelTotal, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Cargar turnos inicialmente
        cargarTurnos(turnosEmpleado);
        
        return panel;
    }
    
    private void cargarTurnos(List<Turno> turnos) {
        panelTurnos.removeAll();
        
        if (turnos.isEmpty()) {
            JLabel mensajeVacio = new JLabel("No tienes turnos asignados para los filtros seleccionados.");
            mensajeVacio.setFont(new Font("Arial", Font.ITALIC, 14));
            mensajeVacio.setForeground(Color.GRAY);
            mensajeVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            mensajeVacio.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
            panelTurnos.add(mensajeVacio);
        } else {
            // Ordenar turnos por fecha
            turnos.sort((t1, t2) -> t1.getFecha().compareTo(t2.getFecha()));
            
            for (Turno turno : turnos) {
                JPanel panelTurno = crearPanelTurno(turno);
                panelTurnos.add(panelTurno);
                panelTurnos.add(Box.createVerticalStrut(8));
            }
        }
        
        // Actualizar contador
        labelTotal.setText("Total de turnos: " + turnos.size());
        
        panelTurnos.revalidate();
        panelTurnos.repaint();
    }
    
    private JPanel crearPanelTurno(Turno turno) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        
        // Panel central con información del turno
        JPanel panelInfo = crearPanelInfoTurno(turno);
        
        // Panel derecho con estado/acciones
        JPanel panelEstado = crearPanelEstado(turno);
        
        panel.add(panelInfo, BorderLayout.CENTER);
        panel.add(panelEstado, BorderLayout.EAST);
        
        return panel;
    }
    
    
    private JPanel crearPanelInfoTurno(Turno turno) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        // Tipo de turno
        String tipoTurnoTexto = (turno.getTipoTurno() != null) ? 
            turno.getTipoTurno().getDescripcion() : "No especificado";
        JLabel labelTipo = new JLabel("Turno: " + tipoTurnoTexto);
        labelTipo.setFont(new Font("Arial", Font.BOLD, 14));
        labelTipo.setForeground(new Color(70, 130, 180));
        
        // Lugar asignado
        String nombreLugar = obtenerNombreLugar(turno.getLugarAsignado());
        JLabel labelLugar = new JLabel("Lugar: " + nombreLugar);
        labelLugar.setFont(new Font("Arial", Font.PLAIN, 12));
        labelLugar.setForeground(Color.DARK_GRAY);
        
        // Fecha completa
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        JLabel labelFechaCompleta = new JLabel("Fecha: " + turno.getFecha().format(formatter));
        labelFechaCompleta.setFont(new Font("Arial", Font.PLAIN, 11));
        labelFechaCompleta.setForeground(Color.GRAY);
        
        panel.add(labelTipo);
        panel.add(Box.createVerticalStrut(5));
        panel.add(labelLugar);
        panel.add(Box.createVerticalStrut(3));
        panel.add(labelFechaCompleta);
        
        return panel;
    }
    
    private JPanel crearPanelEstado(Turno turno) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));
        panel.setPreferredSize(new Dimension(100, 90));
        
        // Estado del turno (basado en la fecha)
        LocalDate hoy = LocalDate.now();
        String estado;
        Color colorEstado;
        
        if (turno.getFecha().isBefore(hoy)) {
            estado = "COMPLETADO";
            colorEstado = new Color(34, 139, 34);
        } else if (turno.getFecha().isEqual(hoy)) {
            estado = "HOY";
            colorEstado = new Color(255, 140, 0);
        } else {
            estado = "PROGRAMADO";
            colorEstado = new Color(70, 130, 180);
        }
        
        JLabel labelEstado = new JLabel(estado);
        labelEstado.setFont(new Font("Arial", Font.BOLD, 10));
        labelEstado.setForeground(colorEstado);
        labelEstado.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Indicador visual
        JPanel indicador = new JPanel();
        indicador.setBackground(colorEstado);
        indicador.setPreferredSize(new Dimension(60, 3));
        indicador.setMaximumSize(new Dimension(60, 3));
        
        panel.add(Box.createVerticalGlue());
        panel.add(labelEstado);
        panel.add(Box.createVerticalStrut(5));
        panel.add(indicador);
        panel.add(Box.createVerticalGlue());
        
        return panel;
    }
    
    private String obtenerNombreLugar(Object lugar) {
        if (lugar instanceof Atraccion) {
            return ((Atraccion) lugar).getNombre();
        } else if (lugar instanceof LugarServicio) {
            return ((LugarServicio) lugar).getNombre();
        } else if (lugar instanceof String) {
            return (String) lugar;
        } else {
            return "Lugar no especificado";
        }
    }
    
    private void aplicarFiltros() {
        List<Turno> turnosFiltrados = new ArrayList<>(turnosEmpleado);
        
        // Filtro por tipo de turno
        String tipoSeleccionado = (String) filtroTipoTurno.getSelectedItem();
        if (!"Todos".equals(tipoSeleccionado)) {
            turnosFiltrados = turnosFiltrados.stream()
                .filter(turno -> turno.getTipoTurno() != null && 
                        turno.getTipoTurno().getDescripcion().equals(tipoSeleccionado))
                .collect(Collectors.toList());
        }
        
        // Filtro por período
        String periodoSeleccionado = (String) filtroSemana.getSelectedItem();
        if (!"Todos".equals(periodoSeleccionado)) {
            LocalDate hoy = LocalDate.now();
            switch (periodoSeleccionado) {
                case "Esta semana":
                    LocalDate inicioSemana = hoy.minusDays(hoy.getDayOfWeek().getValue() - 1);
                    LocalDate finSemana = inicioSemana.plusDays(6);
                    turnosFiltrados = turnosFiltrados.stream()
                        .filter(turno -> !turno.getFecha().isBefore(inicioSemana) && 
                                        !turno.getFecha().isAfter(finSemana))
                        .collect(Collectors.toList());
                    break;
                case "Próxima semana":
                    LocalDate inicioProxSemana = hoy.plusDays(7 - hoy.getDayOfWeek().getValue() + 1);
                    LocalDate finProxSemana = inicioProxSemana.plusDays(6);
                    turnosFiltrados = turnosFiltrados.stream()
                        .filter(turno -> !turno.getFecha().isBefore(inicioProxSemana) && 
                                        !turno.getFecha().isAfter(finProxSemana))
                        .collect(Collectors.toList());
                    break;
                case "Este mes":
                    turnosFiltrados = turnosFiltrados.stream()
                        .filter(turno -> turno.getFecha().getMonth() == hoy.getMonth() && 
                                        turno.getFecha().getYear() == hoy.getYear())
                        .collect(Collectors.toList());
                    break;
            }
        }
        
        cargarTurnos(turnosFiltrados);
    }
    
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(new Color(240, 248, 255));
        
        // Botón actualizar
        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setPreferredSize(new Dimension(120, 35));
        btnActualizar.setFont(new Font("Arial", Font.BOLD, 12));
        btnActualizar.setBackground(new Color(0, 30, 200));
        btnActualizar.setForeground(Color.BLUE);
        btnActualizar.setBorder(BorderFactory.createEmptyBorder());
        btnActualizar.setFocusPainted(false);
        btnActualizar.addActionListener(e -> {
            inicializarTurnos();
            aplicarFiltros();
            JOptionPane.showMessageDialog(this, "Turnos actualizados correctamente.", 
                "Actualización", JOptionPane.INFORMATION_MESSAGE);
        });
        
        // Botón cerrar
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.setFont(new Font("Arial", Font.BOLD, 12));
        btnCerrar.setBackground(new Color(255, 0, 0));
        btnCerrar.setForeground(Color.RED);
        btnCerrar.setBorder(BorderFactory.createEmptyBorder());
        btnCerrar.setFocusPainted(false);
        btnCerrar.addActionListener(e -> dispose());
        
        panel.add(btnActualizar);
        panel.add(btnCerrar);
        
        return panel;
    }
}

// Clase TipoTurno de ejemplo (si no la tienes ya)
class TipoTurno {
    private String nombre;
    private String horaInicio;
    private String horaFin;
    
    public TipoTurno(String nombre, String horaInicio, String horaFin) {
        this.nombre = nombre;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
    
    public TipoTurno() {}
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }
    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
    
    @Override
    public String toString() {
        return nombre + " (" + horaInicio + " - " + horaFin + ")";
    }
}