package sistema_parque.lugaresServicio;

import java.util.ArrayList;

import sistema_parque.usuarios.Empleado;

public class Tienda extends LugarServicio{

	private ArrayList<Empleado> empleados;
	

	public Tienda(String nombre, ArrayList<Empleado> empleados, Cajero cajeros ) {
		super(nombre);
		
		this.empleados = empleados;
	
	}
	
	public Tienda() {
        super();
         this.empleados = new ArrayList<>();
    }
	
	public ArrayList<Empleado> getEmpleados() {
		return empleados;
	}
}
