package sistema_parque.usuarios;

import java.util.ArrayList;

public class Cocinero extends Empleado implements CapacitadoManejo {
	
	private boolean estaCapacitado;
	
	public Cocinero(String nombre, String login, String contrasena, String rol, ArrayList<String> capacitaciones,
			boolean turnoDiurno, boolean turnoNocturno, boolean estaCapacitado) {
		super(nombre, login, contrasena, rol, capacitaciones, turnoDiurno, turnoNocturno);
		this.estaCapacitado = estaCapacitado;
	}
	
	public Cocinero() {
        super();
    }
	
	public boolean getEstaCapacitado() {
		return estaCapacitado;
	}
	
	@Override
	public void Capacitado() {
		estaCapacitado = true;
	}
}
