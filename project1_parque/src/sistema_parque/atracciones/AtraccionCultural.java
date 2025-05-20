package sistema_parque.atracciones;

import java.util.ArrayList;

public class AtraccionCultural extends Atraccion {

    
    public AtraccionCultural(String ubicacion, String nombre, int cupoMaximo, int empleadosMinimos,
            String clasificacionCategoria, boolean deTemporada, ArrayList<String> restriccionesClima,
            NivelesRiesgo nivelRiesgo, String deTemporadaDetalles) {
        // Llama al constructor de la clase padre con todos los parámetros necesarios.
        super(ubicacion, nombre, cupoMaximo, empleadosMinimos, clasificacionCategoria, deTemporada, restriccionesClima,
                nivelRiesgo, deTemporadaDetalles);
    }
    
    public AtraccionCultural() {
        super();
    }

    @Override
    public String toString() {
        return "AtraccionCultural{" +
               "nombre='" + getNombre() + '\'' +
               ", ubicacion='" + getUbicacion() + '\'' +
               ", clasificacion='" + getClasificacionCategoria() + '\'' +
               ", cupoMaximo=" + getCupoMaximo() +
               ", estaCerrada=" + estaCerrada() + // Usar el método heredado
               ", restriccionEdad=" + getRestriccionEdad() + // Accede al campo/getter heredado
               ", espacioConstruido=" + getEspacioConstruido() + // Accede al campo/getter heredado
               '}';
    }

    
}