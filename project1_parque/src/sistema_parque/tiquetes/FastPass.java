package sistema_parque.tiquetes;

import java.util.Date;

public class FastPass extends Tiquete {
	
	private Date fecha;
	
	public FastPass(Categoria nivel, boolean fueUsado, Date fecha, String id) {
		super(nivel, fueUsado, id, "FASTPASS");
		this.fecha = fecha;
	}
	
	public FastPass() {
        super(); // Llama al constructor sin argumentos de la clase base Tiquete
    }

	public Date getFecha() {
		return fecha;
	}
}
