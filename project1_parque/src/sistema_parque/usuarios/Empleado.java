package sistema_parque.usuarios;

import java.util.ArrayList;

public class Empleado extends Usuario {
	
	private String rol;
	private ArrayList<String> capacitaciones;
	private boolean turnoDiurno;
	private boolean turnoNocturno;
	private static final int DESCUENTOCOMPRA = 1;
	

	public Empleado(String nombre, String login, String contrasena,String rol ,ArrayList<String> capacitaciones, boolean turnoDiurno, boolean turnoNocturno) {
		super(nombre, login, contrasena);
		this.capacitaciones = new ArrayList<>(capacitaciones);
		this.rol = rol;
		this.turnoDiurno = turnoDiurno;
		this.turnoNocturno = turnoNocturno;
		
	}
	
	
	public Empleado() {
        super();
        // Inicializa listas específicas de Empleado
        this.capacitaciones = new ArrayList<>();
    }
	  
	public String getRol() {
		return rol;
	}

	public ArrayList<String> getCapacitaciones() {
		return capacitaciones;
	}


	public boolean isTurnoDiurno() {
		return turnoDiurno;
	}


	public boolean isTurnoNocturno() {
		return turnoNocturno;
	}


	public static int getDESCUENTOCOMPRA() {
		return DESCUENTOCOMPRA;
	}
	

}
