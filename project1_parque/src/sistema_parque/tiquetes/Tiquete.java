package sistema_parque.tiquetes;

import java.time.LocalDate;
import sistema_parque.atracciones.Atraccion; // Asegúrate que esta clase exista

public class Tiquete {

    private String id;
    protected Categoria nivel; // 'protected' para acceso desde el mismo paquete y subclases
    private boolean fueUsado;
    private java.time.LocalDate fechaExpiracion;
    private String tipoTiquete; 

    private Atraccion atraccion;

    // Constructor principal
    public Tiquete(Categoria nivel, boolean fueUsado, String id, String tipoTiquete) {
    	super();
        this.nivel = nivel;
        this.fueUsado = fueUsado;
        this.id = id;
        this.tipoTiquete = tipoTiquete;
        // fechaExpiracion y atraccion permanecen null por defecto con este constructor
    }

    // Constructor por defecto
    public Tiquete() {
        this.fueUsado = false;
        // id, nivel, fechaExpiracion, atraccion permanecen null
    }

    public String getTipo() {
    	return tipoTiquete;
    }
    
    public Atraccion getAtraccion() {
        return atraccion;
    }

    public java.time.LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    // Setter añadido para facilitar la prueba y el uso
    public void setFechaExpiracion(java.time.LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Categoria getNivel() {
        return nivel;
    }

    public boolean isFueUsado() {
        return fueUsado;
    }

    public String getId() {
        return id;
    }


    public boolean esValidoParaUsar(LocalDate now) {
        if (this.fechaExpiracion == null) {
            return false; // O lanzar una excepción si se prefiere: throw new IllegalStateException("Fecha de expiración no establecida.");
        }
        return now.isBefore(this.fechaExpiracion);
    }

    // Protected: puede ser accedido por clases en el mismo paquete o subclases
    public void setAtraccion(Atraccion atraccion) {
        this.atraccion = atraccion;
    }

    public void marcarComoUsado() {
        this.fueUsado = true;
    }
}