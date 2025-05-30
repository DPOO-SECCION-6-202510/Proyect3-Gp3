package sistema_parque.tiquetes;

import java.time.LocalDate;
import sistema_parque.atracciones.Atraccion; // Asegúrate que esta clase exista

public class Tiquete {

    private String id;
    protected Categoria nivel;
    private boolean fueUsado;
    private java.time.LocalDate fechaExpiracion;
    // ¡¡¡CAMBIO CRÍTICO AQUÍ!!!
    // Renombrar 'tipo' a 'tipoTiquete' para que coincida con el campo en el JSON
    private String tipoTiquete; // <-- ¡Este es el cambio que necesitas aplicar!

    private Atraccion atraccion;

    // Constructor principal
    public Tiquete(Categoria nivel, boolean fueUsado, String id, String tipoTiquete) {
        // super(); // No es necesario si no extiende de otra clase base que lo requiera.
        this.nivel = nivel;
        this.fueUsado = fueUsado;
        this.id = id;
        this.tipoTiquete = tipoTiquete; // ¡Asigna el valor al atributo renombrado!
        // fechaExpiracion y atraccion permanecen null por defecto con este constructor
    }

    // Constructor por defecto
    public Tiquete() {
        this.fueUsado = false;
        // id, nivel, fechaExpiracion, atraccion, tipoTiquete permanecen null
    }

    // ¡¡¡CAMBIO CRÍTICO AQUÍ!!!
    // El getter ahora devuelve el atributo renombrado
    public String getTipo() {
        return tipoTiquete; // <-- ¡Este es el cambio que necesitas aplicar!
    }

    // Si necesitas un setter para tipoTiquete (es buena práctica tenerlo)
    public void setTipoTiquete(String tipoTiquete) {
        this.tipoTiquete = tipoTiquete;
    }
    
    public Atraccion getAtraccion() {
        return atraccion;
    }

    public java.time.LocalDate getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(java.time.LocalDate fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public Categoria getNivel() {
        return nivel;
    }

    public void setNivel(Categoria nivel) { // Agregado setter para nivel, si no lo tenías
        this.nivel = nivel;
    }

    public boolean isFueUsado() {
        return fueUsado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) { // Agregado setter para id, si no lo tenías
        this.id = id;
    }


    public boolean esValidoParaUsar(LocalDate now) {
        if (this.fechaExpiracion == null) {
            return false;
        }
        return !fueUsado && now.isBefore(this.fechaExpiracion);
    }

    public void setAtraccion(Atraccion atraccion) {
        this.atraccion = atraccion;
    }

    public void marcarComoUsado() {
        this.fueUsado = true;
    }
}