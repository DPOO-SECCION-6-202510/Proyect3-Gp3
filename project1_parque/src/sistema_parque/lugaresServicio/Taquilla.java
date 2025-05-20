package sistema_parque.lugaresServicio;

import java.util.ArrayList;
import java.util.List;

import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;

public class Taquilla extends LugarServicio{
	
	private List<Empleado> listaEmpleados;
	private List<Tiquete> listaTiquetesVender;
	
	public Taquilla(String nombre, Cajero cajeros) {
		super(nombre);
		this.listaEmpleados = new ArrayList<Empleado>();
		this.listaTiquetesVender = new ArrayList<Tiquete>();
	}
	
	public Taquilla() {
        super(); 
        this.listaEmpleados = new ArrayList<>();
        this.listaTiquetesVender = new ArrayList<>();
    }
	
	public void registrarVenta(Cliente usuario, Tiquete tiquete) {
		usuario.getListaTiquetesNoUsados().add(tiquete);
	}
	
	public List<Empleado> getListaEmpleados(){
		return listaEmpleados;
	}
	
	public void agregarEmpleado(Empleado empleado) {
		listaEmpleados.add(empleado);
	}
	
	public List<Tiquete> getListaTiquetesVender(){
		return listaTiquetesVender;
	}
	
	public void agregarTiqueteVender(Tiquete tiquete) {
		if (tiquete != null) {
			listaTiquetesVender.add(tiquete);
		}
	}
}
