package sistema_parque.usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import sistema_parque.atracciones.Atraccion; // Needed for managing attractions
// Import other necessary classes like Turno, LugarServicio etc. when defining method parameters
import sistema_parque.usuarios.Turno; // Assuming a Turno class exists
import sistema_parque.lugaresServicio.LugarServicio; // Needed for assignments


/**
 * Representa un empleado con rol de Administrador.
 * Tiene permisos especiales para gestionar empleados, turnos y atracciones.
 * Hereda las características básicas de un Empleado.
 */
public class Administrador extends Empleado {

    // Rol fijo para cualquier administrador.
    private static final String ROL_ADMINISTRADOR = "Administrador";

    
    public Administrador(String nombre, String login, String contrasena) {
        
        super(nombre, login, contrasena, ROL_ADMINISTRADOR, new ArrayList<>(), false, false);
    }
    
    public Administrador() {
        super(); // Llama al constructor sin args de Empleado
    } 

  
    public boolean cambiarInfoEmpleado(Empleado empleadoAModificar, String nuevoRol, List<String> nuevasCapacitaciones, boolean asignadoTurnoDiurno, boolean asignadoTurnoNocturno) {
        Objects.requireNonNull(empleadoAModificar, "El empleado a modificar no puede ser nulo.");

        System.out.println("ADMIN: Intentando modificar empleado: " + empleadoAModificar.getLogin());

        
        System.out.println("ADMIN: Información de empleado " + empleadoAModificar.getLogin() + " actualizada (simulado).");
        // En un sistema real, también habría que persistir estos cambios.
        return true; // Simulación de éxito
    }

   
    public boolean asignarTurno(Empleado empleado, Object lugar, Turno turnoInfo, List<Turno> listaDeTurnos) {
         Objects.requireNonNull(empleado, "El empleado no puede ser nulo.");
         Objects.requireNonNull(lugar, "El lugar no puede ser nulo.");
         Objects.requireNonNull(turnoInfo, "La información del turno no puede ser nula.");
         Objects.requireNonNull(listaDeTurnos, "La lista de turnos no puede ser nula.");

         // Verificar si el lugar es una instancia válida
         if (!(lugar instanceof Atraccion) && !(lugar instanceof LugarServicio)) {
              System.err.println("ADMIN ERROR: El lugar asignado no es una Atraccion ni un LugarServicio.");
              return false;
         }

         System.out.println("ADMIN: Asignando a " + empleado.getLogin() + " al lugar " +
                           (lugar instanceof Atraccion ? ((Atraccion)lugar).getNombre() : ((LugarServicio)lugar).getNombre()) +
                           " para el turno " + turnoInfo);

         // Crear y añadir la asignación (objeto Turno) a la lista central
         // Turno nuevaAsignacion = new Turno(turnoInfo.getFecha(), turnoInfo.getTipo(), empleado, lugar);
         listaDeTurnos.add(turnoInfo); // Asumiendo que turnoInfo ya está construido correctamente

         System.out.println("ADMIN: Asignación registrada.");
         // Persistir cambios si es necesario.
         return true;
    }

   
    public boolean agregarAtraccion(Atraccion atraccionNueva, List<Atraccion> listaAtracciones) {
        Objects.requireNonNull(atraccionNueva, "La atracción a agregar no puede ser nula.");
        Objects.requireNonNull(listaAtracciones, "La lista de atracciones no puede ser nula.");

        // Verificar si ya existe (por nombre, por ejemplo)
        for (Atraccion existente : listaAtracciones) {
            if (existente.getNombre().equalsIgnoreCase(atraccionNueva.getNombre())) {
                System.err.println("ADMIN ERROR: Ya existe una atracción con el nombre: " + atraccionNueva.getNombre());
                return false;
            }
        }

        listaAtracciones.add(atraccionNueva);
        System.out.println("ADMIN: Atracción agregada: " + atraccionNueva.getNombre());
        // Persistir cambios.
        return true;
    }

     
    public boolean modificarAtraccion(Atraccion atraccionAModificar /*, Otros parámetros con nuevos datos */) {
        Objects.requireNonNull(atraccionAModificar, "La atracción a modificar no puede ser nula.");

        System.out.println("ADMIN: Intentando modificar atracción: " + atraccionAModificar.getNombre());

        // Aquí iría la lógica para actualizar atributos de la atracción.
        // Necesitaría Setters en la clase Atraccion y/o sus subclases.
        // Ejemplo: atraccionAModificar.setCupoMaximo(nuevoCupo);

        System.out.println("ADMIN: Información de atracción " + atraccionAModificar.getNombre() + " actualizada (simulado).");
        // Persistir cambios.
        return true;
    }

    // El método para comprar tiquetes con descuento se hereda de Empleado.

    @Override
    public String toString() {
        // Incluir el rol fijo en el toString
        return "Administrador{" +
               "nombre='" + getNombre() + '\'' +
               ", login='" + getLogin() + '\'' +
               ", rol='" + getRol() + '\'' + // Obtener rol de la superclase
               '}';
    }
}