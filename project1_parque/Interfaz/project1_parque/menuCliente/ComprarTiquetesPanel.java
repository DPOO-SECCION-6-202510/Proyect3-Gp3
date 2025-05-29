package project1_parque.menuCliente;

import javax.swing.JPanel;

import sistema_parque.sisParque.PrincipalParque;

public class ComprarTiquetesPanel extends JPanel {
	
	private PrincipalParque parquePrincipal;

	public ComprarTiquetesPanel(PrincipalParque parquePrincipal, String nombreCliente) {
		this.parquePrincipal = parquePrincipal;
	}

}
