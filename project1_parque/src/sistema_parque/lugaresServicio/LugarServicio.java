package sistema_parque.lugaresServicio;

public abstract class LugarServicio {

	private String nombre;
	private boolean estaAbierto;

	public LugarServicio(String nombre) {

		this.nombre = nombre;
		this.estaAbierto = false;

	}
	
	protected LugarServicio() {
        this.estaAbierto = false;
    }

	public String getNombre() {
		return nombre;
	}

	
	public boolean isAbierto() {
		return estaAbierto;
	}
	
	public void abrir() {
		this.estaAbierto = true;
	}
	
	public void cerrar() {
		this.estaAbierto = false;
	}

}
