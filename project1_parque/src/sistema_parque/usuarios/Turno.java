package sistema_parque.usuarios;

import java.time.LocalDate;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.lugaresServicio.LugarServicio;

/**
 * Representa un turno de trabajo asignado a un empleado en una atracción o lugar de servicio.
 */
public class Turno {
    private LocalDate fecha;
    private TipoTurno tipoTurno; // Ahora es un objeto TipoTurno en lugar de un enum
    private Empleado empleadoAsignado;
    private Object lugarAsignado; // Puede ser una Atraccion o un LugarServicio

    /**
     * Constructor completo para crear un turno.
     * 
     * @param empleado El empleado asignado al turno
     * @param lugar El lugar asignado (Atraccion o LugarServicio)
     * @param fecha La fecha del turno
     * @param tipoTurno El tipo de turno (objeto TipoTurno)
     */
    public Turno(Empleado empleado, Object lugar, LocalDate fecha, TipoTurno tipoTurno) {
        this.empleadoAsignado = empleado;
        this.lugarAsignado = lugar;
        this.fecha = fecha;
        this.tipoTurno = tipoTurno;
    }
    
    // Constructor sin argumentos requerido por Gson
    public Turno() {}
        // No es necesario inicializar los campos aquí

    // Getters y setters

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public TipoTurno getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(TipoTurno tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public Empleado getEmpleadoAsignado() {
        return empleadoAsignado;
    }

    public void setEmpleadoAsignado(Empleado empleadoAsignado) {
        this.empleadoAsignado = empleadoAsignado;
    }

    public Object getLugarAsignado() {
        return lugarAsignado;
    }

    public void setLugarAsignado(Object lugarAsignado) {
        this.lugarAsignado = lugarAsignado;
    }

    @Override
    public String toString() {
        String nombreLugar = "";
        if (lugarAsignado instanceof Atraccion) {
            nombreLugar = ((Atraccion) lugarAsignado).getNombre();
        } else if (lugarAsignado instanceof LugarServicio) {
            nombreLugar = ((LugarServicio) lugarAsignado).getNombre();
        }

        return "Turno{" +
                "fecha=" + fecha +
                ", tipo=" + tipoTurno +
                ", empleado=" + empleadoAsignado.getLogin() +
                ", lugar='" + nombreLugar + '\'' +
                '}';
    }
}