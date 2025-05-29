package project1_parque.menuCliente;
import sistema_parque.sisParque.PrincipalParque;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Cliente;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;

public class MenuClienteFrame extends JFrame {

	private PrincipalParque parquePrincipal;
    private String nombreCliente;
	
	public MenuClienteFrame(String nombre, PrincipalParque parquePrincipal) {
		this.nombreCliente = nombre;
        this.parquePrincipal = parquePrincipal;

        setTitle("Menú Cliente - " + nombre);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar esta, cierra la app
        setLocationRelativeTo(null);
		
		// TODO Auto-generated constructor stub
		
	}
	
	

}
