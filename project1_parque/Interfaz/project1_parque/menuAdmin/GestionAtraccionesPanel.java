package project1_parque.menuAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionCultural;
import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.atracciones.Espectaculo;
import sistema_parque.atracciones.NivelesRiesgo;

public class GestionAtraccionesPanel extends JPanel {
    private PrincipalParque parquePrincipal;
    private JTable tablaAtracciones;
    private DefaultTableModel modeloTabla;

    public GestionAtraccionesPanel(PrincipalParque parque) {
        this.parquePrincipal = parque;
        setLayout(new BorderLayout());

        // Título
        JLabel titulo = new JLabel("Gestión de Atracciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        add(titulo, BorderLayout.NORTH);

        // Configurar tabla
        String[] columnas = {"Nombre", "Ubicación", "Tipo", "Categoría", "Riesgo", "Capacidad", "Empleados Mínimos", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaAtracciones = new JTable(modeloTabla);
        tablaAtracciones.setPreferredScrollableViewportSize(new Dimension(900, 300));
        tablaAtracciones.setFillsViewportHeight(true);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar Atracción");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnAbrir = new JButton("Abrir");
        JButton btnCerrar = new JButton("Cerrar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnAbrir);
        panelBotones.add(btnCerrar);

        // Añadir componentes
        add(new JScrollPane(tablaAtracciones), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Cargar datos
        cargarAtracciones();

        // Listeners
        btnAgregar.addActionListener(e -> mostrarDialogoAgregarAtraccion());
        btnEditar.addActionListener(e -> editarAtraccionSeleccionada());
        btnEliminar.addActionListener(e -> eliminarAtraccionSeleccionada());
        btnAbrir.addActionListener(e -> cambiarEstadoAtraccion(false));
        btnCerrar.addActionListener(e -> cambiarEstadoAtraccion(true));
    }

    void cargarAtracciones() {
        modeloTabla.setRowCount(0);
        
        if (parquePrincipal.getListaAtracciones() != null) {
            for (Atraccion atraccion : parquePrincipal.getListaAtracciones()) {
                Object[] fila = {
                    atraccion.getNombre(),
                    atraccion.getUbicacion(),
                    obtenerTipoAtraccion(atraccion),
                    atraccion.getClasificacionCategoria(),
                    atraccion.getNivelRiesgo() != null ? atraccion.getNivelRiesgo().toString() : "N/A",
                    atraccion.getCupoMaximo(),
                    atraccion.getEmpleadosMinimos(),
                    atraccion.estaCerrada() ? "❌ Cerrada" : "✅ Abierta"
                };
                modeloTabla.addRow(fila);
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No se encontraron atracciones", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private String obtenerTipoAtraccion(Atraccion atraccion) {
        if (atraccion instanceof AtraccionMecanica) {
            return "Mecánica";
        } else if (atraccion instanceof AtraccionCultural) {
            return "Cultural";
        } else if (atraccion instanceof Espectaculo) {
            return "Espectáculo";
        }
        return "Desconocido";
    }

    private void mostrarDialogoAgregarAtraccion() {
        JDialog dialogo = new JDialog();
        dialogo.setTitle("Agregar Nueva Atracción");
        dialogo.setSize(600, 500);
        dialogo.setModal(true);
        dialogo.setLayout(new BorderLayout());

        JPanel panelControles = new JPanel(new GridLayout(0, 2, 5, 5));

        // Campos comunes
        JTextField txtNombre = new JTextField();
        JTextField txtUbicacion = new JTextField();
        JTextField txtCupoMaximo = new JTextField();
        JTextField txtEmpleadosMinimos = new JTextField();
        JComboBox<String> cmbCategoria = new JComboBox<>(new String[]{"FAMILIAR", "ORO", "DIAMANTE"});
        JComboBox<NivelesRiesgo> cmbRiesgo = new JComboBox<>(NivelesRiesgo.values());
        JCheckBox chkDeTemporada = new JCheckBox("Es de temporada");
        JTextField txtDetallesTemporada = new JTextField();
        JComboBox<String> cmbTipoAtraccion = new JComboBox<>(new String[]{"Mecánica", "Cultural", "Espectáculo"});

        // Campos específicos por tipo (inicialmente ocultos)
        JPanel panelMecanica = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtAlturaMinima = new JTextField();
        JTextField txtAlturaMaxima = new JTextField();
        JTextField txtPesoMinimo = new JTextField();
        JTextField txtPesoMaximo = new JTextField();
        panelMecanica.add(new JLabel("Altura Mínima:"));
        panelMecanica.add(txtAlturaMinima);
        panelMecanica.add(new JLabel("Altura Máxima:"));
        panelMecanica.add(txtAlturaMaxima);
        panelMecanica.add(new JLabel("Peso Mínimo:"));
        panelMecanica.add(txtPesoMinimo);
        panelMecanica.add(new JLabel("Peso Máximo:"));
        panelMecanica.add(txtPesoMaximo);
        panelMecanica.setVisible(false);

        JPanel panelCultural = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtTemaCultural = new JTextField();
        panelCultural.add(new JLabel("Tema Cultural:"));
        panelCultural.add(txtTemaCultural);
        panelCultural.setVisible(false);

        JPanel panelEspectaculo = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField txtHorario = new JTextField();
        JTextField txtEdadIngreso = new JTextField();
        panelEspectaculo.add(new JLabel("Horario (HHMM):"));
        panelEspectaculo.add(txtHorario);
        panelEspectaculo.add(new JLabel("Edad Mínima:"));
        panelEspectaculo.add(txtEdadIngreso);
        panelEspectaculo.setVisible(false);

        // Listener para cambiar campos según tipo
        cmbTipoAtraccion.addActionListener(e -> {
            panelMecanica.setVisible(false);
            panelCultural.setVisible(false);
            panelEspectaculo.setVisible(false);
            
            String tipo = (String) cmbTipoAtraccion.getSelectedItem();
            if ("Mecánica".equals(tipo)) {
                panelMecanica.setVisible(true);
            } else if ("Cultural".equals(tipo)) {
                panelCultural.setVisible(true);
            } else if ("Espectáculo".equals(tipo)) {
                panelEspectaculo.setVisible(true);
            }
        });

        // Añadir componentes comunes
        panelControles.add(new JLabel("Tipo de Atracción:"));
        panelControles.add(cmbTipoAtraccion);
        panelControles.add(new JLabel("Nombre:"));
        panelControles.add(txtNombre);
        panelControles.add(new JLabel("Ubicación:"));
        panelControles.add(txtUbicacion);
        panelControles.add(new JLabel("Cupo Máximo:"));
        panelControles.add(txtCupoMaximo);
        panelControles.add(new JLabel("Empleados Mínimos:"));
        panelControles.add(txtEmpleadosMinimos);
        panelControles.add(new JLabel("Categoría:"));
        panelControles.add(cmbCategoria);
        panelControles.add(new JLabel("Nivel de Riesgo:"));
        panelControles.add(cmbRiesgo);
        panelControles.add(new JLabel("De Temporada:"));
        panelControles.add(chkDeTemporada);
        panelControles.add(new JLabel("Detalles Temporada:"));
        panelControles.add(txtDetallesTemporada);

        // Panel para campos específicos
        JPanel panelEspecifico = new JPanel(new CardLayout());
        panelEspecifico.add(panelMecanica, "Mecánica");
        panelEspecifico.add(panelCultural, "Cultural");
        panelEspecifico.add(panelEspectaculo, "Espectáculo");

        // Botón de guardar
        JButton btnGuardar = new JButton("Guardar Atracción");

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos comunes
                if (txtNombre.getText().isEmpty() || txtUbicacion.getText().isEmpty() ||
                    txtCupoMaximo.getText().isEmpty() || txtEmpleadosMinimos.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Complete todos los campos obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear atracción según tipo
                String tipo = (String) cmbTipoAtraccion.getSelectedItem();
                Atraccion nuevaAtraccion = null;
                
                String nombre = txtNombre.getText();
                String ubicacion = txtUbicacion.getText();
                int cupoMaximo = Integer.parseInt(txtCupoMaximo.getText());
                int empleadosMinimos = Integer.parseInt(txtEmpleadosMinimos.getText());
                String categoria = (String) cmbCategoria.getSelectedItem();
                NivelesRiesgo riesgo = (NivelesRiesgo) cmbRiesgo.getSelectedItem();
                boolean deTemporada = chkDeTemporada.isSelected();
                String detallesTemporada = txtDetallesTemporada.getText();
                ArrayList<String> restriccionesClima = new ArrayList<>(); // Lista vacía por defecto

                if ("Mecánica".equals(tipo)) {
                    int alturaMinima = Integer.parseInt(txtAlturaMinima.getText());
                    int alturaMaxima = Integer.parseInt(txtAlturaMaxima.getText());
                    int pesoMinimo = Integer.parseInt(txtPesoMinimo.getText());
                    int pesoMaximo = Integer.parseInt(txtPesoMaximo.getText());
                    
                    nuevaAtraccion = new AtraccionMecanica(
                        ubicacion, nombre, cupoMaximo, empleadosMinimos, categoria, 
                        deTemporada, restriccionesClima, riesgo, detallesTemporada,
                        alturaMinima, alturaMaxima, pesoMinimo, pesoMaximo, new ArrayList<>()
                    );
                } else if ("Cultural".equals(tipo)) {
                    String temaCultural = txtTemaCultural.getText();
                    nuevaAtraccion = new AtraccionCultural(
                        ubicacion, nombre, cupoMaximo, empleadosMinimos, categoria,
                        deTemporada, restriccionesClima, riesgo, detallesTemporada
                    );
                    
                    // Si necesitas establecer el tema cultural y no está en el constructor:
                    // ((AtraccionCultural)nuevaAtraccion).setTemaCultural(temaCultural);
                } else if ("Espectáculo".equals(tipo)) {
                    int horario = Integer.parseInt(txtHorario.getText());
                    int edadIngreso = Integer.parseInt(txtEdadIngreso.getText());
                    nuevaAtraccion = new Espectaculo(
                        ubicacion, nombre, cupoMaximo, empleadosMinimos, categoria,
                        deTemporada, restriccionesClima, riesgo, detallesTemporada
                    );
                    
                    // Establecer propiedades específicas
                    ((Espectaculo)nuevaAtraccion).setHorario(horario);
                    ((Espectaculo)nuevaAtraccion).setEdadIngreso(edadIngreso);
                }

                if (nuevaAtraccion != null) {
                    parquePrincipal.getListaAtracciones().add(nuevaAtraccion);
                    cargarAtracciones();
                    dialogo.dispose();
                    JOptionPane.showMessageDialog(this, "Atracción agregada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialogo, "Ingrese valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al crear atracción: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Organizar componentes en el diálogo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelControles, BorderLayout.NORTH);
        panelPrincipal.add(panelEspecifico, BorderLayout.CENTER);
        panelPrincipal.add(btnGuardar, BorderLayout.SOUTH);

        dialogo.add(panelPrincipal);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void editarAtraccionSeleccionada() {
        int fila = tablaAtracciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una atracción para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Funcionalidad de edición en desarrollo", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarAtraccionSeleccionada() {
        int fila = tablaAtracciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una atracción para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombreAtraccion = (String) modeloTabla.getValueAt(fila, 0);
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea eliminar la atracción '" + nombreAtraccion + "'?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            parquePrincipal.getListaAtracciones().remove(fila);
            cargarAtracciones();
            JOptionPane.showMessageDialog(this, "Atracción eliminada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cambiarEstadoAtraccion(boolean cerrar) {
        int fila = tablaAtracciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una atracción", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Atraccion atraccion = parquePrincipal.getListaAtracciones().get(fila);
        if (cerrar) {
            atraccion.cerrar();
        } else {
            atraccion.abrir();
        }
        
        cargarAtracciones();
        JOptionPane.showMessageDialog(this, 
            "Atracción " + (cerrar ? "cerrada" : "abierta") + " con éxito", 
            "Éxito", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}