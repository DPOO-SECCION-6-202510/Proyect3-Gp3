package sistema_parque.tiquetes;

import java.util.Date;

public class FastPass extends Tiquete {
	
	private Date fecha;
	
	public FastPass(Categoria nivel, boolean fueUsado, Date fecha, String id, String tipoTiquete) {
		super(nivel, fueUsado, id, tipoTiquete);
		this.fecha = fecha;
	}
	
	public FastPass() {
        super();
    }

	public Date getFecha() {
		return fecha;
	}
}
