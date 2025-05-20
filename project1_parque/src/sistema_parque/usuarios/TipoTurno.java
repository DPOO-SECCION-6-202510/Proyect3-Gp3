package sistema_parque.usuarios;

/**
 * Representa los tipos de turnos de trabajo en el parque.
 */
public class TipoTurno {
    private String codigo;
    private String descripcion;

    public TipoTurno(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
    // Constructor sin argumentos para Gson
    public TipoTurno() {
    }

    // Es importante para las pruebas que TipoTurno tenga una implementación
    // de equals y hashCode si se van a comparar instancias de TipoTurno
    // directamente o si se usan en colecciones que dependan de estos métodos.
    // Por ahora, las pruebas de Turno comparan referencias o usan la descripción.
    // Si Turno.equals compara objetos TipoTurno, esto sería necesario.
    // Basado en la implementación de Turno.equals que haré (comparando objetos TipoTurno),
    // necesitamos equals y hashCode aquí.

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoTurno tipoTurno = (TipoTurno) o;
        return java.util.Objects.equals(codigo, tipoTurno.codigo) &&
               java.util.Objects.equals(descripcion, tipoTurno.descripcion);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(codigo, descripcion);
    }

    @Override
    public String toString() {
        return descripcion; // Esto es lo que usa Turno.toString() indirectamente
    }
}