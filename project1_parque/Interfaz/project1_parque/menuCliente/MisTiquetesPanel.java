package project1_parque.menuCliente;

import javax.swing.JPanel;

import sistema_parque.sisParque.PrincipalParque;

public class MisTiquetesPanel extends JPanel {
	
	private PrincipalParque parquePrincipal;
	private String nombreCliente; 

	public MisTiquetesPanel(PrincipalParque parquePrincipal, String nombreCliente) {
		this.parquePrincipal = parquePrincipal;
		this.nombreCliente = nombreCliente; 
	}

}
