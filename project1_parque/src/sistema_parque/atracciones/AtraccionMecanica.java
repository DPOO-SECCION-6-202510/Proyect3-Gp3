package sistema_parque.atracciones;

import java.util.ArrayList;

public class AtraccionMecanica extends Atraccion {
	
	public AtraccionMecanica(String ubicacion, String nombre, int cupoMaximo, int empleadosMinimos,
			String clasificacionCategoria, boolean deTemporada, ArrayList<String> restriccionesClima,
			NivelesRiesgo nivelRiesgo, String deTemporadaDetalles, int altmax, int altmin, int pesmax, int pesmin,
			ArrayList<String> restriccionesSalud) {
		super(ubicacion, nombre, cupoMaximo, empleadosMinimos, clasificacionCategoria, deTemporada, restriccionesClima,
				nivelRiesgo, deTemporadaDetalles);
        this.alturaMinima = altmin;
        this.alturaMaxima = altmax;
        this.pesoMinimo = pesmax;
        this.pesoMaximo = pesmin;
        this.nivelRiesgo = nivelRiesgo;
        this.restriccionesSalud = restriccionesSalud;
	}
	
	public AtraccionMecanica() {
        super(); // Llama al constructor sin argumentos de Atraccion
         this.restriccionesSalud = new java.util.ArrayList<>();
    }

	private int alturaMinima;
	private int alturaMaxima;
	private int pesoMinimo;
	private int pesoMaximo;
	private ArrayList<String> restriccionesSalud;
	
	public int getAlturaMinima() {
		return alturaMinima;
	}

	public int getAlturaMaxima() {
		return alturaMaxima;
	}

	public int getPesoMinimo() {
		return pesoMinimo;
	}

	public int getPesoMaximo() {
		return pesoMaximo;
	}

	public ArrayList<String> getRestriccionesSalud() {
		return restriccionesSalud;
	}
    
    public boolean estaAbierta(String fecha) {
		if (Atraccion.estaCerrada() == false) {
		return true;
		}
		return false;
    }
}
