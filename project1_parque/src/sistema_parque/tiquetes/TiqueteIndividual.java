package sistema_parque.tiquetes;

import sistema_parque.atracciones.Atraccion;

public class TiqueteIndividual extends Tiquete {

    public TiqueteIndividual(Categoria nivel, boolean fueUsado, Atraccion atraccion, String id) {
       
        super(nivel, fueUsado, id);
        
        setAtraccion(atraccion); 
    }
    
    
    public TiqueteIndividual() {
        super(); // Llama al constructor sin args de Tiquete
    }
  
    @Override
    public String toString() {
        Atraccion atraccionAsociada = getAtraccion();
        String nombreAtraccion = (atraccionAsociada != null) ? atraccionAsociada.getNombre() : "Ninguna";

        return "TiqueteIndividual{" +
               "id='" + getId() + '\'' +
               ", nivel=" + getNivel() +
               ", fueUsado=" + isFueUsado() +
               ", atraccion='" + nombreAtraccion + '\'' +
               ", fechaExpiracion=" + getFechaExpiracion() +
               '}';
    }
}