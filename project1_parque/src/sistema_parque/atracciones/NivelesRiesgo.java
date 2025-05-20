package sistema_parque.atracciones;

public enum NivelesRiesgo {
    BAJO("bajo"),
    MEDIO("medio"),
    ALTO("alto");
    
    private String valor;
    
    NivelesRiesgo(String valor) {
        this.valor = valor;
    }
    
    public String getValor() {
        return valor;
    }
    
    @Override
    public String toString() {
        return this.valor;
    }
}