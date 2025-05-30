package sistema_parque.tiquetes;

public class TiqueteTemporada extends Tiquete {
	
	private String tipoTemporadaLapso;
	
	public TiqueteTemporada(Categoria nivel, boolean fueUsado, String tipoTemporadaLapso, String id) {
		super(nivel, fueUsado, id, "TEMPORADA");
		this.tipoTemporadaLapso = tipoTemporadaLapso;
	}
	
	public TiqueteTemporada() {
        super();
    }

	public String getTipoTemporadaLapso() {
		return tipoTemporadaLapso;
	}
}
