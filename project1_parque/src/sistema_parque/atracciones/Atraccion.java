package sistema_parque.atracciones;

import java.util.ArrayList;

public abstract class Atraccion {
	
	private String ubicacion;
	private String nombre;
	private int cupoMaximo;
	private int empleadosMinimos;
	private String clasificacionCategoria;
	private boolean deTemporada;
	private ArrayList<String> restriccionesClima;
	public NivelesRiesgo nivelRiesgo;
	private String deTemporadaDetalles;
	private static boolean estaCerrada;
	
	protected Atraccion() {

        this.restriccionesClima = new java.util.ArrayList<>();
	}
	
	public Atraccion(String ubicacion, String nombre, int cupoMaximo, int empleadosMinimos,
			String clasificacionCategoria, boolean deTemporada, ArrayList<String> restriccionesClima,
			NivelesRiesgo nivelRiesgo, String deTemporadaDetalles) {
		super();
		this.ubicacion = ubicacion;
		this.nombre = nombre;
		this.cupoMaximo = cupoMaximo;
		this.empleadosMinimos = empleadosMinimos;
		this.clasificacionCategoria = clasificacionCategoria;
		this.deTemporada = deTemporada;
		this.restriccionesClima = restriccionesClima;
		this.nivelRiesgo = nivelRiesgo;
		this.deTemporadaDetalles = deTemporadaDetalles;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	public String getNombre() {
		return nombre;
	}
	public int getCupoMaximo() {
		return cupoMaximo;
	}
	public int getEmpleadosMinimos() {
		return empleadosMinimos;
	}
	public String getClasificacionCategoria() {
		return clasificacionCategoria;
	}
	public boolean isDeTemporada() {
		return deTemporada;
	}
	public ArrayList<String> getRestriccionesClima() {
		return restriccionesClima;
	}
	public NivelesRiesgo getNivelRiesgo() {
		return nivelRiesgo;
	}
	
	public String getdeTemporadaDetalles() {
		return deTemporadaDetalles;
	}
	
	public static boolean estaCerrada () {
		return estaCerrada;
	}
	
	public static void abrir() {
		Atraccion.estaCerrada = false;
	}
	
	public static void cerrar() {
		Atraccion.estaCerrada = true;
	}
	
	public boolean CerrarDeTemporada(Atraccion atraccion, String fecha) {
		if (atraccion.deTemporadaDetalles == fecha) {
			Atraccion.abrir();
			return false;
		}
		String dia1 = atraccion.deTemporadaDetalles.substring(8, 10);
		String dia2 = atraccion.deTemporadaDetalles.substring(21, 23);
		String mes1 = atraccion.deTemporadaDetalles.substring(5, 7);
		String mes2 = atraccion.deTemporadaDetalles.substring(18, 20);
		String dia = fecha.substring(8, 10);
		String mes = fecha.substring(5, 7);
		if ((dia.compareTo(dia1) > 0 && dia.compareTo(dia2) < 0) && (mes.compareTo(mes1) > 0 && mes.compareTo(mes2) < 0)){
			Atraccion.abrir();
			return false;
		}
		Atraccion.cerrar();
		return true; 
	}
	
	private int restriccionEdad;
    public int getRestriccionEdad() {
        return restriccionEdad;
    }
    
    private int espacioConstruido;
    public int getEspacioConstruido() {
        return espacioConstruido;
    }
	
}
