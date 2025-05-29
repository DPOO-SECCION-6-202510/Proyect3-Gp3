package project1_parque.menuEmpleado;

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Empleado;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;

public class MenuEmpleadoFrame extends JFrame {
	
	private PrincipalParque parquePrincipal;
    private String nombreEmpleado;

	public MenuEmpleadoFrame(String nombre, PrincipalParque parquePrincipal) {
		
		this.nombreEmpleado = nombre;
        this.parquePrincipal = parquePrincipal;

        setTitle("Menú Empleado - " + nombre);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Al cerrar esta, cierra la app
        setLocationRelativeTo(null);
		
		// TODO Auto-generated constructor stub
	}

}
