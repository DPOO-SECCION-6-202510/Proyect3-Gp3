package project1_parque.inicio;

//Importa las clases necesarias
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane; // Para mensajes
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

import sistema_parque.sisParque.PrincipalParque; // Importa PrincipalParque
import sistema_parque.usuarios.Cliente; // Importa Cliente

//Asume que esta es tu clase RegistroFrame
public class RegistroFrame extends JFrame {

 private JTextField campoNombre;
 private JTextField campoLogin;
 private JPasswordField campoContrasena;
 private JButton botonRegistrar;
 private JButton botonVolver;

 // --- NUEVO: Instancia de PrincipalParque ---
 private PrincipalParque parquePrincipal;

 public RegistroFrame(PrincipalParque parquePrincipal) { // Recibe la instancia del parque
     this.parquePrincipal = parquePrincipal; // Guarda la referencia

     setTitle("Registrar Nuevo Cliente");
     setSize(450, 350);
     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
     setLocationRelativeTo(null);

     JPanel panelRegistro = new JPanel(new GridBagLayout());
     GridBagConstraints gbc = new GridBagConstraints();

     // Etiquetas y Campos de Texto
     gbc.insets = new Insets(10, 10, 5, 5);
     gbc.anchor = GridBagConstraints.WEST;

     gbc.gridx = 0; gbc.gridy = 0;
     panelRegistro.add(new JLabel("Nombre Completo:"), gbc);
     gbc.gridx = 1; gbc.gridy = 0;
     campoNombre = new JTextField(20);
     campoNombre.setPreferredSize(new Dimension(200, 28));
     gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
     panelRegistro.add(campoNombre, gbc);

     gbc.gridx = 0; gbc.gridy = 1;
     panelRegistro.add(new JLabel("Usuario (Login):"), gbc);
     gbc.gridx = 1; gbc.gridy = 1;
     campoLogin = new JTextField(20);
     campoLogin.setPreferredSize(new Dimension(200, 28));
     panelRegistro.add(campoLogin, gbc);

     gbc.gridx = 0; gbc.gridy = 2;
     panelRegistro.add(new JLabel("Contraseña:"), gbc);
     gbc.gridx = 1; gbc.gridy = 2;
     campoContrasena = new JPasswordField(20);
     campoContrasena.setPreferredSize(new Dimension(200, 28));
     panelRegistro.add(campoContrasena, gbc);

     // Botones
     gbc.fill = GridBagConstraints.HORIZONTAL; // Para que los botones se expandan
     gbc.insets = new Insets(15, 10, 5, 10); // Más espacio entre campos y botones
     gbc.gridwidth = 2; // Ocupar dos columnas para centrar

     gbc.gridx = 0; gbc.gridy = 3;
     botonRegistrar = new JButton("Registrar");
     panelRegistro.add(botonRegistrar, gbc);

     gbc.gridx = 0; gbc.gridy = 4;
     botonVolver = new JButton("Volver al Login");
     panelRegistro.add(botonVolver, gbc);

     add(panelRegistro);
     pack(); // Ajusta el tamaño de la ventana al contenido
     setLocationRelativeTo(null); // Centra la ventana

     // --- ActionListener para el botón "Registrar" ---
     botonRegistrar.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             String nombre = campoNombre.getText();
             String login = campoLogin.getText();
             char[] passwordChars = campoContrasena.getPassword();
             String contrasena = new String(passwordChars);

             // Crear un nuevo objeto Cliente
             Cliente nuevoCliente = new Cliente(nombre, login, contrasena);

             // Llamar al método de registro de PrincipalParque
             boolean registrado = parquePrincipal.registrarUsuario(nuevoCliente);

             if (registrado) {
                 JOptionPane.showMessageDialog(RegistroFrame.this,
                                               "¡Registro exitoso! Ya puedes iniciar sesión.",
                                               "Registro Completado",
                                               JOptionPane.INFORMATION_MESSAGE);
                 // Opcional: limpiar campos o cerrar la ventana de registro y volver al login
                 dispose(); // Cierra esta ventana
                 // Si quieres que al cerrar esta ventana se abra la de login, puedes instanciarla aquí
                 // new LoginFrame().setVisible(true); // Requiere un constructor por defecto en LoginFrame
                 // O si LoginFrame ya está abierta, no la abras de nuevo.
             } else {
                 // registrarUsuario ya imprime errores en System.err.
                 // Aquí, puedes mostrar un mensaje más amigable en la GUI.
                 JOptionPane.showMessageDialog(RegistroFrame.this,
                                               "Error al registrar usuario. Asegúrate de que todos los campos estén llenos y el usuario no exista.",
                                               "Error de Registro",
                                               JOptionPane.ERROR_MESSAGE);
             }
             // Limpiar el array de caracteres de la contraseña por seguridad
             java.util.Arrays.fill(passwordChars, '0');
         }
     });

     // --- ActionListener para el botón "Volver al Login" ---
     botonVolver.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
             dispose(); // Cierra la ventana de registro
             
             // Opcional: Reabrir el LoginFrame si no está ya abierto
             new LoginFrame().setVisible(true); // Si quieres que al volver se abra una nueva ventana de login
         }
     });
 }

 // Método para ser llamado desde LoginFrame cuando se hace clic en "Regístrate aquí"
 // Asegúrate de que el MouseListener en LoginFrame llame a esto:
 // new RegistroFrame(parquePrincipal).setVisible(true);
}