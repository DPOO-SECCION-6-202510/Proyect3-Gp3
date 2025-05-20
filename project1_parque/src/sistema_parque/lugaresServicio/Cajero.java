package sistema_parque.lugaresServicio;

import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado; // Para saber quién opera la caja

public class Cajero {

private final String idCaja;
private Empleado empleadoOperando;
private LugarServicio lugarAsociado;

public Cajero(String idCaja, LugarServicio lugarAsociado) {
    if (idCaja == null || idCaja.trim().isEmpty()) {
        throw new IllegalArgumentException("El ID de la caja no puede ser vacío.");
    }
    this.idCaja = idCaja.trim();
    this.lugarAsociado = lugarAsociado;
    this.empleadoOperando = null;
}

/**
 * Establece el lugar de servicio asociado a este cajero.
 * Este método es útil cuando se necesita establecer una relación bidireccional
 * entre el cajero y el lugar de servicio después de la inicialización.
 * 
 * @param lugarServicio El lugar de servicio a asociar con este cajero
 */
public void setLugarServicio(LugarServicio lugarServicio) {
    this.lugarAsociado = lugarServicio;
}

public void asignarEmpleado(Empleado empleado) {
    this.empleadoOperando = empleado;
    System.out.println("Empleado " + empleado.getLogin() + " asignado a la caja " + this.idCaja);
}

public void desasignarEmpleado() {
    if (this.empleadoOperando != null) {
        System.out.println("Empleado " + this.empleadoOperando.getLogin() + " desasignado de la caja " + this.idCaja);
    }
    this.empleadoOperando = null;
}

public boolean registrarVentaTiquete(Tiquete tiquete, Cliente cliente) {
    if (tiquete == null || cliente == null) {
        System.err.println("Error en caja " + idCaja + ": Tiquete o Cliente nulo.");
        return false;
    }
    if (this.empleadoOperando == null) {
        System.err.println("Error en caja " + idCaja + ": Ningún empleado operando.");
        return false;
    }

    System.out.println("Caja " + idCaja + ": Registrando venta de tiquete " + tiquete.getId() + " a cliente " + cliente.getLogin() + " por empleado " + this.empleadoOperando.getLogin());
    cobrar(calcularPrecio(tiquete), cliente);

    return true;
}

public boolean registrarVentaItem(String item, int cantidad, Cliente cliente) {
    if (this.empleadoOperando == null) {
        System.err.println("Error en caja " + idCaja + ": Ningún empleado operando.");
        return false;
    }
    System.out.println("Caja " + idCaja + ": Registrando venta de " + cantidad + "x " + item + ((cliente != null) ? " a " + cliente.getLogin() : ""));
    cobrar(calcularPrecioItem(item, cantidad), cliente);
    return true;
}

private void cobrar(double monto, Cliente cliente) {
    System.out.println("Caja " + idCaja + ": Cobrando monto $" + monto + ((cliente != null) ? " a " + cliente.getLogin() : ""));
}

private double calcularPrecio(Tiquete tiquete) {
    return 50.0;
}

private double calcularPrecioItem(String item, int cantidad) {
    return 10.0 * cantidad;
}

// --- Getters ---
public String getIdCaja() {
    return idCaja;
}

public Empleado getEmpleadoOperando() {
    return empleadoOperando;
}

public LugarServicio getLugarAsociado() {
    return lugarAsociado;
}
}