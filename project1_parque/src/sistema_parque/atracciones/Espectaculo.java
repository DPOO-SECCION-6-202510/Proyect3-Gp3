package sistema_parque.atracciones;

import java.util.ArrayList;
import java.util.Date;

public class Espectaculo extends Atraccion {
	public Espectaculo(String ubicacion, String nombre, int cupoMaximo, int empleadosMinimos,
			String clasificacionCategoria, boolean deTemporada, ArrayList<String> restriccionesClima,
			NivelesRiesgo nivelRiesgo, String deTemporadaDetalles) {
		super(ubicacion, nombre, cupoMaximo, empleadosMinimos, clasificacionCategoria, deTemporada, restriccionesClima,
				nivelRiesgo, deTemporadaDetalles);

	}
	
	public Espectaculo() {
        super();
	}    
    
	public int getHorario() {
		return horario;
	}
	public Date getFecha() {
		return fecha;
	}
	public int getEdadIngreso() {
		return edadIngreso;
	}

	private int horario;
	public void setHorario(int horario) {
		this.horario = horario;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public void setEdadIngreso(int edadIngreso) {
		this.edadIngreso = edadIngreso;
	}

	private Date fecha;
	private int edadIngreso;
	
	
}
