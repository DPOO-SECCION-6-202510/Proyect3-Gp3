package project1_parque.inicio;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import CONTROL_PERSISTENCIA.CentralPersistencia;
import CONTROL_PERSISTENCIA.PersistenciaParque;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Administrador;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Cocinero;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Usuario;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets; // Para los márgenes
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.BorderLayout; // Para el JFrame
import java.awt.Dimension; // Para PreferredSize, opcional
import java.awt.Cursor; // Para cambiar el cursor al pasar por encima
import java.awt.Color; // Para cambiar el color del texto

import project1_parque.menuAdmin.MenuAdminFrame;    // Ajusta el paquete si es diferente
import project1_parque.menuCliente.MenuClienteFrame; // Ajusta el paquete si es diferente
import project1_parque.menuEmpleado.MenuEmpleadoFrame;

public class LoginFrame extends JFrame { // No es necesario implements ActionListener aquí si usas anónimas

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JButton botonCancelar;
    private static final String ARCHIVO_DATOS_PARQUE = "data/parque.json";
    private PrincipalParque parquePrincipal;
    

    public LoginFrame() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear componentes
        JLabel etiquetaUsuario = new JLabel("Usuario:");
        campoUsuario = new JTextField(15); // Un poco más grande
        JLabel etiquetaPassword = new JLabel("Contraseña:");
        campoPassword = new JPasswordField(15); // Un poco más grande
        botonLogin = new JButton("Ingresar");
        botonCancelar = new JButton("Cancelar");
        JLabel Note_registro = new JLabel("¿No tiene cuenta?, Registrese aquí");

        // Opcional: Establecer tamaño preferido para los campos para un control más fino
        campoUsuario.setPreferredSize(new Dimension(200, 28)); // Ancho, Alto
        campoPassword.setPreferredSize(new Dimension(200, 28));

        // Panel principal con GridBagLayout
        JPanel panelLogin = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // --- Añadir componentes al panelLogin ---

        // Etiqueta Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Alinear a la izquierda
        gbc.insets = new Insets(20, 20, 5, 5); // top, left, bottom, right
        panelLogin.add(etiquetaUsuario, gbc);

        // Campo Usuario
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir horizontalmente
        gbc.weightx = 1.0; // Darle peso para que ocupe espacio si la ventana crece
        gbc.insets = new Insets(20, 5, 5, 20);
        panelLogin.add(campoUsuario, gbc);

        // Etiqueta Contraseña
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 20, 10, 5);
        panelLogin.add(etiquetaPassword, gbc);

        // Campo Contraseña
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 5, 10, 20);
        panelLogin.add(campoPassword, gbc);

        // Botón Ingresar
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir
        gbc.insets = new Insets(20, 20, 5, 20); // Más espacio arriba
        panelLogin.add(botonLogin, gbc);

        // Botón Cancelar
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Ocupa 2 columnas
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir
        gbc.insets = new Insets(5, 20, 20, 20); // Espacio abajo
        panelLogin.add(botonCancelar, gbc);
        
        // Botón Registrarse (ir a RegistroFrame)
	     // Etiqueta Registrarse
	    gbc.gridx = 0; // Inicia en la columna 0
	    gbc.gridy = 4; // En la siguiente fila después de los botones (fila 2 y 3 para botones)
	    gbc.gridwidth = 2; // Ocupa las 2 columnas para centrarse
	    gbc.anchor = GridBagConstraints.CENTER; // Centra el JLabel en su espacio
	    gbc.fill = GridBagConstraints.NONE; // No expandir (el anchor lo centra)
	    gbc.insets = new Insets(15, 20, 20, 20); // Margen superior para separarlo de los botones, y margen inferior
	    panelLogin.add(Note_registro, gbc);
	                
        Note_registro.setForeground(Color.BLUE.darker());
        Note_registro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        Note_registro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Lógica cuando se hace clic en el enlace
                abrirVentanaRegistro(); // Llama a un método que crearás
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	Note_registro.setForeground(Color.RED); // Ejemplo: un color diferente
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	Note_registro.setForeground(Color.BLUE.darker());
            }
        });
        
        
        // Añadir el panel de login al centro del JFrame
        add(panelLogin, BorderLayout.CENTER);

        // Ajustar el tamaño de la ventana y centrar
        pack(); // Ajusta la ventana al tamaño preferido de sus componentes
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false); // Opcional: no permitir redimensionar la ventana
        setVisible(true);

        // AQUI IRÍA LA CONFIGURACION DE LOS ActionListener (Paso 5)
        // botonLogin.addActionListener(new ActionListener() { ... });
        // botonCancelar.addActionListener(new ActionListener() { ... });
        
        parquePrincipal = new PrincipalParque();
        CentralPersistencia central = new CentralPersistencia();
        PersistenciaParque persistencia = central.getPersistenciaParque(CentralPersistencia.JSON);
        
        try {
            persistencia.cargarParque(ARCHIVO_DATOS_PARQUE, parquePrincipal);
            System.out.println("Datos del parque cargados al inicio.");
            // Opcional: Si el archivo está vacío o no existe y quieres un admin por defecto
            if (parquePrincipal.getListaUsuarios().isEmpty()) {
                System.out.println("No se encontraron usuarios. Creando administrador por defecto.");
                // Constructor del Administrador: nombre, login, contrasena
                // Asegúrate de que el constructor de Administrador que estás usando sea este:
                // public Administrador(String nombre, String login, String contrasena)
                Administrador adminDefecto = new Administrador("Admin Default", "admin", "admin123");
                parquePrincipal.getListaUsuarios().add(adminDefecto); // Añadirlo a la lista de usuarios
                parquePrincipal.setAdministrador(adminDefecto); // Asignarlo como administrador
                // Guarda este administrador por defecto inmediatamente
                persistencia.salvarParque(ARCHIVO_DATOS_PARQUE, parquePrincipal);
                JOptionPane.showMessageDialog(this, "No se encontraron datos del parque. Se ha creado un administrador por defecto (usuario: admin, contraseña: admin123).", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar los datos iniciales del parque: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error de carga inicial: " + e.getMessage());
            e.printStackTrace();
        }
        
        botonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = campoUsuario.getText();
                char[] passwordChars = campoPassword.getPassword();
                String password = new String(passwordChars); // Convertir a String para la comparación

                if (login.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(LoginFrame.this,
                                                  "Por favor, ingrese usuario y contraseña.",
                                                  "Campos Vacíos",
                                                  JOptionPane.WARNING_MESSAGE);
                } else {
                    // Delegar la autenticación a la instancia de PrincipalParque
                    Usuario usuarioAutenticado = parquePrincipal.autenticarUsuario(login, password);

                    if (usuarioAutenticado != null) {
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                                      "¡Bienvenido, " + usuarioAutenticado.getNombre() + "!",
                                                      "Inicio de Sesión Exitoso",
                                                      JOptionPane.INFORMATION_MESSAGE);

                        // Llama al método auxiliar que decide qué ventana abrir
                        abrirMenuPrincipal(usuarioAutenticado);
                        dispose(); // Cierra la ventana de login
                    } else {
                        // Si autenticarUsuario devuelve null
                        JOptionPane.showMessageDialog(LoginFrame.this,
                                                      "Usuario o contraseña incorrectos. Por favor, intente de nuevo.",
                                                      "Error de Autenticación",
                                                      JOptionPane.ERROR_MESSAGE);
                        campoUsuario.setText("");
                        campoPassword.setText("");
                        campoUsuario.requestFocusInWindow();
                    }
                }
                // Limpiar el array de caracteres de la contraseña por seguridad
                java.util.Arrays.fill(passwordChars, '0');
            }
        });
        
        botonCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(LoginFrame.this,
                                                           "¿Estás seguro que quieres salir de la aplicación?",
                                                           "Confirmar Salida",
                                                           JOptionPane.YES_NO_OPTION,
                                                           JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0); // Cierra la aplicación por completo
                }
            }
        });
        
        Note_registro.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Abre el RegistroFrame y le pasa la instancia de parquePrincipal
                new RegistroFrame(parquePrincipal).setVisible(true);
                // Opcional: puedes ocultar o cerrar LoginFrame aquí
                // LoginFrame.this.setVisible(false); // Oculta
                // dispose(); // Cierra
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            	Note_registro.setForeground(Color.RED); // Cambia el color al pasar el ratón
            }

            @Override
            public void mouseExited(MouseEvent e) {
            	Note_registro.setForeground(Color.BLUE); // Vuelve al color original
            }
        });


        pack(); // Ajusta el tamaño de la ventana a los componentes
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setVisible(true);
    } // Fin del constructor de LoginFrame


    private void abrirMenuPrincipal(Usuario usuarioAutenticado) {
        if (usuarioAutenticado instanceof Administrador) {
            // Abre MenuAdminFrame
            // Asegúrate de que el constructor de MenuAdminFrame reciba el nombre y parquePrincipal
            new MenuAdminFrame(usuarioAutenticado.getNombre(), parquePrincipal).setVisible(true);
        } else if (usuarioAutenticado instanceof Cliente) {
            // Abre MenuClienteFrame
            // Asegúrate de que el constructor de MenuClienteFrame reciba el nombre y parquePrincipal
            new MenuClienteFrame(usuarioAutenticado.getNombre(), parquePrincipal).setVisible(true);
        } else if (usuarioAutenticado instanceof Cocinero) { // Si tienes un menú específico para Cocinero
            // Abre MenuEmpleadoFrame o un MenuCocineroFrame si lo creas
            new MenuEmpleadoFrame(usuarioAutenticado.getNombre(), parquePrincipal).setVisible(true); // O un MenuCocineroFrame
        } else if (usuarioAutenticado instanceof Empleado) { // Para cualquier otro tipo de empleado no clasificado arriba
            // Abre MenuEmpleadoFrame
            new MenuEmpleadoFrame(usuarioAutenticado.getNombre(), parquePrincipal).setVisible(true);
        } else {
            // En caso de un tipo de usuario desconocido o no manejado
            JOptionPane.showMessageDialog(this,
                                          "Tipo de usuario no reconocido. Contacte al administrador.",
                                          "Error de Rol",
                                          JOptionPane.ERROR_MESSAGE);
            // Podrías cerrar la sesión o hacer algo más aquí
            System.err.println("ERROR: Tipo de usuario desconocido en login: " + usuarioAutenticado.getClass().getName());
        }
    }
    
    
    private void abrirVentanaRegistro() {
    	new RegistroFrame(parquePrincipal).setVisible(true);
        dispose();
    	
    }
    
    
   
    public static void main(String[] args) {
        // Mejorar el Look and Feel para que se parezca más al sistema operativo
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
    }
    
    
    
}