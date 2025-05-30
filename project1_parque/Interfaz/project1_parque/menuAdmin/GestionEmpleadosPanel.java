package project1_parque.menuAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Empleado;

public class GestionEmpleadosPanel extends JPanel {
    private PrincipalParque parquePrincipal;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;

    public GestionEmpleadosPanel(PrincipalParque parque) {
        this.parquePrincipal = parque;
        setLayout(new BorderLayout());

        // Crear modelo de tabla con columnas que coincidan con los atributos de Empleado
        String[] columnas = {"Nombre", "Login", "Rol", "Turno Diurno", "Turno Nocturno", "Capacitaciones"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer que la tabla no sea editable directamente
            }
        };
        tablaEmpleados = new JTable(modeloTabla);
        tablaEmpleados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Botones de gestión
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar Empleado");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        btnAgregar.addActionListener(e -> mostrarDialogoAgregarEmpleado());
        btnEditar.addActionListener(e -> editarEmpleadoSeleccionado());
        btnEliminar.addActionListener(e -> eliminarEmpleadoSeleccionado());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarEmpleados();
    }

    void cargarEmpleados() {
        modeloTabla.setRowCount(0); // Limpiar tabla
        
        for (Empleado empleado : parquePrincipal.getListaEmpleados()) {
            Object[] fila = {
                empleado.getNombre(),
                empleado.getLogin(),
                empleado.getRol(),
                empleado.isTurnoDiurno() ? "Sí" : "No",
                empleado.isTurnoNocturno() ? "Sí" : "No",
                String.join(", ", empleado.getCapacitaciones())
            };
            modeloTabla.addRow(fila);
        }
    }

    private void mostrarDialogoAgregarEmpleado() {
        JDialog dialogo = new JDialog();
        dialogo.setTitle("Agregar Nuevo Empleado");
        dialogo.setSize(500, 400);
        dialogo.setModal(true);
        dialogo.setLayout(new GridLayout(0, 2, 5, 5));

        // Componentes del formulario
        JTextField txtNombre = new JTextField();
        JTextField txtLogin = new JTextField();
        JTextField txtContrasena = new JPasswordField();
        JTextField txtRol = new JTextField();
        
        JCheckBox chkTurnoDiurno = new JCheckBox("Turno Diurno");
        JCheckBox chkTurnoNocturno = new JCheckBox("Turno Nocturno");
        
        JPanel panelCapacitaciones = new JPanel(new BorderLayout());
        DefaultListModel<String> modeloCapacitaciones = new DefaultListModel<>();
        JList<String> listaCapacitaciones = new JList<>(modeloCapacitaciones);
        JTextField txtNuevaCapacitacion = new JTextField();
        JButton btnAgregarCapacitacion = new JButton("Agregar");
        
        btnAgregarCapacitacion.addActionListener(e -> {
            if (!txtNuevaCapacitacion.getText().isEmpty()) {
                modeloCapacitaciones.addElement(txtNuevaCapacitacion.getText());
                txtNuevaCapacitacion.setText("");
            }
        });
        
        JPanel panelCapacitacionInput = new JPanel(new BorderLayout());
        panelCapacitacionInput.add(txtNuevaCapacitacion, BorderLayout.CENTER);
        panelCapacitacionInput.add(btnAgregarCapacitacion, BorderLayout.EAST);
        
        panelCapacitaciones.add(new JScrollPane(listaCapacitaciones), BorderLayout.CENTER);
        panelCapacitaciones.add(panelCapacitacionInput, BorderLayout.SOUTH);
        
        JButton btnGuardar = new JButton("Guardar");

        // Añadir componentes al diálogo
        dialogo.add(new JLabel("Nombre:"));
        dialogo.add(txtNombre);
        dialogo.add(new JLabel("Login:"));
        dialogo.add(txtLogin);
        dialogo.add(new JLabel("Contraseña:"));
        dialogo.add(txtContrasena);
        dialogo.add(new JLabel("Rol:"));
        dialogo.add(txtRol);
        dialogo.add(new JLabel("Turnos:"));
        
        JPanel panelTurnos = new JPanel(new FlowLayout());
        panelTurnos.add(chkTurnoDiurno);
        panelTurnos.add(chkTurnoNocturno);
        dialogo.add(panelTurnos);
        
        dialogo.add(new JLabel("Capacitaciones:"));
        dialogo.add(panelCapacitaciones);
        dialogo.add(new JLabel());
        dialogo.add(btnGuardar);

        btnGuardar.addActionListener(e -> {
            try {
                // Validar campos obligatorios
                if (txtNombre.getText().isEmpty() || txtLogin.getText().isEmpty() || 
                    txtContrasena.getText().isEmpty() || txtRol.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Crear lista de capacitaciones
                ArrayList<String> capacitaciones = new ArrayList<>();
                for (int i = 0; i < modeloCapacitaciones.size(); i++) {
                    capacitaciones.add(modeloCapacitaciones.getElementAt(i));
                }
                
                // Crear nuevo empleado según los datos ingresados
                Empleado nuevoEmpleado = new Empleado(
                    txtNombre.getText(),
                    txtLogin.getText(),
                    txtContrasena.getText(),
                    txtRol.getText(),
                    capacitaciones,
                    chkTurnoDiurno.isSelected(),
                    chkTurnoNocturno.isSelected()
                );
                
                // Agregar empleado al parque (usando el método existente)
                parquePrincipal.getListaEmpleados().add(nuevoEmpleado);
                
                // Actualizar tabla
                cargarEmpleados();
                dialogo.dispose();
                JOptionPane.showMessageDialog(this, "Empleado agregado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialogo, "Error al crear empleado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void editarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para editar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String loginEmpleado = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        // Solución para el problema de la variable en el lambda
        final Empleado[] empleadoRef = new Empleado[1];
        
        // Buscar empleado por login
        for (Empleado emp : parquePrincipal.getListaEmpleados()) {
            if (emp.getLogin().equals(loginEmpleado)) {
                empleadoRef[0] = emp;
                break;
            }
        }

        if (empleadoRef[0] != null) {
            JDialog dialogo = new JDialog();
            dialogo.setTitle("Editar Empleado");
            dialogo.setSize(500, 400);
            dialogo.setModal(true);
            dialogo.setLayout(new GridLayout(0, 2, 5, 5));

            // Componentes del formulario con datos actuales
            JTextField txtNombre = new JTextField(empleadoRef[0].getNombre());
            JTextField txtLogin = new JTextField(empleadoRef[0].getLogin());
            txtLogin.setEditable(false); // No se puede cambiar el login
            JTextField txtContrasena = new JPasswordField(empleadoRef[0].getContrasena());
            JTextField txtRol = new JTextField(empleadoRef[0].getRol());
            
            JCheckBox chkTurnoDiurno = new JCheckBox("Turno Diurno", empleadoRef[0].isTurnoDiurno());
            JCheckBox chkTurnoNocturno = new JCheckBox("Turno Nocturno", empleadoRef[0].isTurnoNocturno());
            
            JPanel panelCapacitaciones = new JPanel(new BorderLayout());
            DefaultListModel<String> modeloCapacitaciones = new DefaultListModel<>();
            for (String cap : empleadoRef[0].getCapacitaciones()) {
                modeloCapacitaciones.addElement(cap);
            }
            JList<String> listaCapacitaciones = new JList<>(modeloCapacitaciones);
            JTextField txtNuevaCapacitacion = new JTextField();
            JButton btnAgregarCapacitacion = new JButton("Agregar");
            JButton btnEliminarCapacitacion = new JButton("Eliminar");
            
            btnAgregarCapacitacion.addActionListener(e -> {
                if (!txtNuevaCapacitacion.getText().isEmpty()) {
                    modeloCapacitaciones.addElement(txtNuevaCapacitacion.getText());
                    txtNuevaCapacitacion.setText("");
                }
            });
            
            btnEliminarCapacitacion.addActionListener(e -> {
                if (!listaCapacitaciones.isSelectionEmpty()) {
                    modeloCapacitaciones.remove(listaCapacitaciones.getSelectedIndex());
                }
            });
            
            JPanel panelCapacitacionInput = new JPanel(new BorderLayout());
            panelCapacitacionInput.add(txtNuevaCapacitacion, BorderLayout.CENTER);
            
            JPanel panelBotonesCapacitacion = new JPanel(new GridLayout(1, 2));
            panelBotonesCapacitacion.add(btnAgregarCapacitacion);
            panelBotonesCapacitacion.add(btnEliminarCapacitacion);
            
            panelCapacitacionInput.add(panelBotonesCapacitacion, BorderLayout.EAST);
            
            panelCapacitaciones.add(new JScrollPane(listaCapacitaciones), BorderLayout.CENTER);
            panelCapacitaciones.add(panelCapacitacionInput, BorderLayout.SOUTH);
            
            JButton btnGuardar = new JButton("Guardar Cambios");

            // Añadir componentes al diálogo
            dialogo.add(new JLabel("Nombre:"));
            dialogo.add(txtNombre);
            dialogo.add(new JLabel("Login:"));
            dialogo.add(txtLogin);
            dialogo.add(new JLabel("Contraseña:"));
            dialogo.add(txtContrasena);
            dialogo.add(new JLabel("Rol:"));
            dialogo.add(txtRol);
            dialogo.add(new JLabel("Turnos:"));
            
            JPanel panelTurnos = new JPanel(new FlowLayout());
            panelTurnos.add(chkTurnoDiurno);
            panelTurnos.add(chkTurnoNocturno);
            dialogo.add(panelTurnos);
            
            dialogo.add(new JLabel("Capacitaciones:"));
            dialogo.add(panelCapacitaciones);
            dialogo.add(new JLabel());
            dialogo.add(btnGuardar);

            btnGuardar.addActionListener(e -> {
                try {
                    // Validar campos obligatorios
                    if (txtNombre.getText().isEmpty() || txtContrasena.getText().isEmpty() || 
                        txtRol.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(dialogo, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Actualizar datos del empleado usando la referencia del array
                    empleadoRef[0].setNombre(txtNombre.getText());
                    empleadoRef[0].setContrasena(txtContrasena.getText());
                    empleadoRef[0].setRol(txtRol.getText());
                    empleadoRef[0].setTurnoDiurno(chkTurnoDiurno.isSelected());
                    empleadoRef[0].setTurnoNocturno(chkTurnoNocturno.isSelected());
                    
                    // Actualizar capacitaciones
                    ArrayList<String> nuevasCapacitaciones = new ArrayList<>();
                    for (int i = 0; i < modeloCapacitaciones.size(); i++) {
                        nuevasCapacitaciones.add(modeloCapacitaciones.getElementAt(i));
                    }
                    empleadoRef[0].getCapacitaciones().clear();
                    empleadoRef[0].getCapacitaciones().addAll(nuevasCapacitaciones);
                    
                    // Actualizar tabla
                    cargarEmpleados();
                    dialogo.dispose();
                    JOptionPane.showMessageDialog(this, "Cambios guardados con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialogo, "Error al guardar cambios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            dialogo.setLocationRelativeTo(this);
            dialogo.setVisible(true);
        }
    }

    private void eliminarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un empleado para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String loginEmpleado = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro que desea eliminar este empleado?", 
            "Confirmar Eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            // Buscar y eliminar empleado por login
            for (int i = 0; i < parquePrincipal.getListaEmpleados().size(); i++) {
                if (parquePrincipal.getListaEmpleados().get(i).getLogin().equals(loginEmpleado)) {
                    parquePrincipal.getListaEmpleados().remove(i);
                    break;
                }
            }
            
            cargarEmpleados();
            JOptionPane.showMessageDialog(this, "Empleado eliminado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}