package project1_parque.inicio;

import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame{

	public LoginFrame() {
		setSize(350, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel etiquetaUsuario = new JLabel("Usuario:");
		JLabel etiquetaPassword = new JLabel("Contraseña:");
		
		JTextField campoUsuario = new JTextField(20);
		JPasswordField compoContraseña = new JPasswordField(20);
		
		JButton botonLogin = new JButton("Ingresar");
		JButton Cancelar = new JButton("Cancelar");
		
		JPanel principal = new JPanel();
		principal.setLayout(new GridLayout(3, 2, 5, 5));

		principal.add(etiquetaUsuario);
		principal.add(etiquetaPassword);
		principal.add(campoUsuario);
		principal.add(compoContraseña);
		principal.add(botonLogin);
		principal.add(Cancelar);
		add(principal);
		

		
	}
	
	public static void main(String[] pArgs) {
		LoginFrame inte = new LoginFrame();
        inte.setVisible(true);
    }

}
