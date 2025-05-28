package project1_parque.inicio;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets; // Para los márgenes
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout; // Para el JFrame
import java.awt.Dimension; // Para PreferredSize, opcional
import java.awt.Cursor; // Para cambiar el cursor al pasar por encima
import java.awt.Color; // Para cambiar el color del texto
import java.awt.Font;

public class LoginFrame extends JFrame { // No es necesario implements ActionListener aquí si usas anónimas

    private JTextField campoUsuario;
    private JPasswordField campoPassword;
    private JButton botonLogin;
    private JButton botonCancelar;

    public LoginFrame() {
        setTitle("Iniciar Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // setSize(400, 300); // Ya no es necesario si usas pack()
        // setLocationRelativeTo(null); // Se llama después de pack()

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
                // Opcional: Cambiar el color o estilo al pasar el mouse por encima
            	Note_registro.setForeground(Color.RED); // Ejemplo: un color diferente
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Opcional: Volver al color original al salir el mouse
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
    }
    
    private void abrirVentanaRegistro() {
    	new RegistroFrame();
    	this.dispose();
    	
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