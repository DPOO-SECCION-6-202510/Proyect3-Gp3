package sistema_parque.sisParque;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import CONTROL_PERSISTENCIA.CentralPersistencia;
import CONTROL_PERSISTENCIA.PersistenciaParque;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.lugaresServicio.LugarServicio;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Administrador;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Turno; // <-- *** IMPORTANTE: Añadir import para Turno ***
import sistema_parque.usuarios.Usuario;

public class PrincipalParque {

    private List<Atraccion> listaAtracciones;
    private List<Usuario> listaUsuarios;
    private List<Empleado> listaEmpleados; // Considera si esta lista es redundante si ya tienes listaUsuarios
    private List<LugarServicio> listaLugaresServicio;
    private List<Tiquete> listaTiquetes;
    private List<Turno> listaTurnos; // <-- *** NUEVO: Lista para almacenar los turnos ***
    private Administrador administrador;

    public PrincipalParque() {
        this.listaAtracciones = new ArrayList<>();
        this.listaUsuarios = new ArrayList<>();
        this.listaEmpleados = new ArrayList<>(); // Asegúrate de que esta lista se pueble correctamente o elimina si es redundante
        this.listaLugaresServicio = new ArrayList<>();
        this.listaTiquetes = new ArrayList<>();
        this.listaTurnos = new ArrayList<>(); // <-- *** NUEVO: Inicializar la lista de turnos ***
        this.administrador = null;
    }

    public List<Atraccion> getListaAtracciones() {
        // Considera devolver una copia no modificable para proteger la lista original
        // return Collections.unmodifiableList(listaAtracciones);
        return listaAtracciones;
    }

    public List<Usuario> getListaUsuarios() {
        // return Collections.unmodifiableList(listaUsuarios);
        return listaUsuarios;
    }

    public List<Empleado> getListaEmpleados() {
         // return Collections.unmodifiableList(listaEmpleados);
         // TODO: Asegúrate de cómo se llena esta lista. Si todos los empleados están en
         // listaUsuarios, quizás podrías filtrarla en lugar de tener una lista separada.
        return listaEmpleados;
    }
    
    public Empleado buscarEmpleadoPorNombre(String nombre) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getNombre().equalsIgnoreCase(nombre)) {
                return empleado;
            }
        }
        return null;
    }
    
    public List<LugarServicio> getListaLugaresServicio() {
        // return Collections.unmodifiableList(listaLugaresServicio);
        return listaLugaresServicio;
    }

    public List<Tiquete> getListaTiquetes() {
         // return Collections.unmodifiableList(listaTiquetes);
        return listaTiquetes;
    }
    
    // --- Setter y Getter para Administrador ---
    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
        if (administrador != null && !this.listaUsuarios.contains(administrador)) {
             this.listaUsuarios.add(administrador);
        }
         if (administrador != null && !this.listaEmpleados.contains(administrador)) {
             this.listaEmpleados.add(administrador);
         }
    }

    public Administrador getAdministrador() {
        return administrador;
    }

    // --- *** NUEVO: Getter para la lista de Turnos *** ---
    /**
     * Devuelve la lista de todos los turnos asignados en el parque.
     * @return Una lista de objetos Turno.
     */
    public List<Turno> getListaTurnos() {
   
        return listaTurnos; 
    }


 // --- Métodos de Persistencia ---
    public void cargarParque(String archivo, String tipoArchivo) {
        CentralPersistencia central = new CentralPersistencia();
        PersistenciaParque persistencia = central.getPersistenciaParque(tipoArchivo);
        // *** IMPORTANTE: La lógica de carga (en PersistenciaParqueJson)
        persistencia.cargarParque(archivo, this);
        System.out.println("Advertencia: La carga de turnos desde el archivo aún no está implementada en la persistencia.");
    }

    public void salvarParque(String archivo, String tipoArchivo) {
        CentralPersistencia central = new CentralPersistencia();
        PersistenciaParque persistencia = central.getPersistenciaParque(tipoArchivo);
         // *** IMPORTANTE: La lógica de guardado (en PersistenciaParqueJson)
        persistencia.salvarParque(archivo, this);
        System.out.println("Advertencia: El guardado de turnos en el archivo aún no está implementado en la persistencia.");
    }

	public static void main(String[] args) {
		PrincipalParque parque = new PrincipalParque();

		String path;
		System.out.println("Ingrese la ruta del archivo para cargar el parque: ");
		try (Scanner scanner = new Scanner(System.in)) {
			path = scanner.nextLine();
		}
		parque.cargarParque(path, CentralPersistencia.JSON);
		parque.salvarParque(path, CentralPersistencia.JSON);
	}

	public void consultarTemporada() {
		System.out.println("Ingrese la fecha para consultar la temporada(YYYY-MM-DD): ");
		try (Scanner scanner = new Scanner(System.in)) {
			String fecha = scanner.nextLine();
			String mes = fecha.substring(5, 7); 
			if (mes.compareTo("03") >= 0 && mes.compareTo("05") <= 0) {
				System.out.println("la temporada es primavera");
			}
			else if(mes.compareTo("06") >= 0 && mes.compareTo("08") <= 0) {
				System.out.println("la temporada es verano");
			}
			else if(mes.compareTo("09") >= 0 && mes.compareTo("11") <= 0) {
				System.out.println("la temporada es otoño");
			}
			else if(mes.compareTo("12") >= 0 && mes.compareTo("02") <= 0) {
				System.out.println("la temporada es invierno");
			}
		}
	}

	public void abrirAtraccion() {
		System.out.println("Ingrese el nombre de la atraccion que desea abrir: ");
		try (Scanner scanner = new Scanner(System.in)) {
			String nombreAtraccion = scanner.nextLine();

			Atraccion atraccion = buscarAtraccionPorNombre(nombreAtraccion);

			if (atraccion != null) {
				if (atraccion.isDeTemporada() == false) {
					atraccion.abrir();
					System.out.println("La atraccion " + nombreAtraccion + " ha sido abierta.");
				}
				else {
					System.out.println("La atraccion es de temporada");
					System.out.println("Ingrese la fecha a buscar para cerrar la atraccion: ");
					String fecha = scanner.nextLine();
					boolean cerrado = atraccion.CerrarDeTemporada(atraccion, fecha);
					if (cerrado == false) {
						System.out.println("La atraccion " + nombreAtraccion + " ha sido abierta.");
					}
					else {
						System.out.println("La atraccion no se puede abrir ya que esta cerrada en la actual temporada");
					}
				}
			} else {
				System.out.println("No se encontró ninguna atraccion con ese nombre.");
			}
		}
	}

	public void cerrarAtraccion() {
		System.out.println("Ingrese el nombre de la atraccion que desea cerrar: ");
		try (Scanner scanner = new Scanner(System.in)) {
			String nombreAtraccion = scanner.nextLine();

			Atraccion atraccion = buscarAtraccionPorNombre(nombreAtraccion);

			if (atraccion != null) {
				if (atraccion.isDeTemporada() == false) {
					atraccion.cerrar();
					System.out.println("La atraccion " + nombreAtraccion + " ha sido cerrada.");
				}
				else {
					System.out.println("La atraccion es de temporada");
					System.out.println("Ingrese la fecha a buscar para cerrar la atraccion: ");
					String fecha = scanner.nextLine();
					boolean cerrado = atraccion.CerrarDeTemporada(atraccion, fecha);
					if (cerrado == true) {
						System.out.println("La atraccion " + nombreAtraccion + " ha sido cerrada.");
					}
					else {
						System.out.println("La atraccion no se puede cerrar ya que esta abierta en la actual temporada");
					}
				}
			} else {
				System.out.println("No se encontró ninguna atraccion con ese nombre.");
			}
		}
	}

	private Atraccion buscarAtraccionPorNombre(String nombre) {

		for (Atraccion atraccion : listaAtracciones) {
			if (atraccion.getNombre().equalsIgnoreCase(nombre)) {
				return atraccion;
			}
		}
		return null;
	}

	public void agregarEmpleado() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Ingrese el nombre del empleado: ");
			String nombre = scanner.nextLine();
			
			System.out.println("Ingrese el login del empleado: ");
			String login = scanner.nextLine();
			
			System.out.println("Ingrese la contrasena del login: ");
			String contrasena = scanner.nextLine();
			
			System.out.println("Ingrese el rol del empleado: ");
			String rol = scanner.nextLine();
			
			ArrayList<String> capacitaciones = new ArrayList<String>();
			System.out.println("¿Cuantas capacitaciones tiene el empleado?: ");
			int numCapacitaciones = Integer.parseInt(scanner.nextLine());
			for (int i = 0; i < numCapacitaciones; i ++) {
				System.out.println("Ingrese la capacitacion #" + (i+1) + ":");
				capacitaciones.add(scanner.nextLine());
			}
			
			boolean turnoDiurno = false;
			boolean turnoNocturno = false;
			
			System.out.println("¿El empleado tiene turno diurno? (s/n): ");
			String respuestaDiurno = scanner.nextLine().toLowerCase();
			if (respuestaDiurno == "s" || respuestaDiurno == "si") {
				turnoDiurno = true;
			}
			
			System.out.println("¿El empleado tiene turno nocturno? (s/n): ");
			String respuestaNocturno = scanner.nextLine().toLowerCase();
			if (respuestaNocturno == "s" || respuestaNocturno == "si") {
				turnoNocturno = true;
			}
			
			Empleado nuevoEmpleado = new Empleado(nombre, login, contrasena, rol, capacitaciones, turnoDiurno, turnoNocturno);
			listaEmpleados.add(nuevoEmpleado);
			System.out.println("Empleado creado correctamente.");
			
		} catch (Exception e) {
			System.out.println("Error al agregar empleado: " + e.getMessage());
		}
	}

	public void eliminarEmpleado() {
		if (listaEmpleados.isEmpty()) {
			System.out.print("No hay empleados registrados en el sistema");
		} 
		
		System.out.println("Imprimiendo listado de empleados...");
		for (int i = 0; i < listaEmpleados.size(); i++) {
			Empleado empleado = listaEmpleados.get(i);
			System.out.println((i+1) + "." + empleado.getNombre() + " - " + empleado.getRol());
		}
		
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Ingrese el numero del empleado que quiera eliminar: ");
			int opcion = Integer.parseInt(scanner.nextLine());
			
			if (opcion > 0 && opcion <= listaEmpleados.size()) {
				Empleado empleadoEliminado = listaEmpleados.remove(opcion-1);
				System.out.println("Se ha eliminado al empleado: " + empleadoEliminado.getNombre() + " - " + empleadoEliminado.getRol());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void abrirLugarServicio() {
		System.out.println("Ingrese el nombre del lugar de servicio que desea abrir: ");
		try (Scanner scanner = new Scanner(System.in)) {
			String nombreLugar = scanner.nextLine();

			LugarServicio lugar = buscarLugarServicioPorNombre(nombreLugar);

			if (lugar != null) {
				lugar.abrir();
				System.out.println("El lugar de servicio " + nombreLugar + " ha sido abierto.");
			} else {
				System.out.println("No se encontró ningún lugar de servicio con ese nombre.");
			}
		}
	}

	public void cerrarLugarServicio() {
		System.out.println("Ingrese el nombre del lugar de servicio que desea cerrar: ");
		try (Scanner scanner = new Scanner(System.in)) {
			String nombreLugar = scanner.nextLine();

			LugarServicio lugar = buscarLugarServicioPorNombre(nombreLugar);

			if (lugar != null) {
				lugar.cerrar();
				System.out.println("El lugar de servicio " + nombreLugar + " ha sido cerrado.");
			} else {
				System.out.println("No se encontró ningún lugar de servicio con ese nombre.");
			}
		}
	}

	private LugarServicio buscarLugarServicioPorNombre(String nombre) {

		for (LugarServicio lugar : listaLugaresServicio) {
			if (lugar.getNombre().equalsIgnoreCase(nombre)) {
				return lugar;
			}
		}
		return null;
	}

	public void verAtracciones() {
		
		if (!listaAtracciones.isEmpty()) {
			for (int i = 0; i < listaAtracciones.size() ; i++) {
				System.out.println(i + ". " + listaAtracciones.get(i));
			}
		} else {
			System.out.println("No hay atracciones disponibles.");
		}

	}
	
	/**
     * Busca los turnos asignados a un empleado específico en una fecha dada.
     * (Esto sería útil para la funcionalidad donde un empleado consulta su turno [source: 174])
     * @param empleado El empleado a buscar.
     * @param fecha La fecha para la cual buscar turnos.
     * @return Una lista de turnos para ese empleado en esa fecha.
     */
    public List<Turno> getTurnosPorEmpleadoYFecha(Empleado empleado, java.time.LocalDate fecha) {
        List<Turno> turnosEncontrados = new ArrayList<>();
        for (Turno turno : this.listaTurnos) {
            if (turno.getEmpleadoAsignado().equals(empleado) && turno.getFecha().equals(fecha)) {
                turnosEncontrados.add(turno);
            }
        }
        return turnosEncontrados;
    }

     /**
      * Busca todos los turnos asignados para una fecha específica.
      * (Útil para planificación o reportes diarios)
      * @param fecha La fecha a consultar.
      * @return Lista de turnos para esa fecha.
      */
     public List<Turno> getTurnosPorFecha(java.time.LocalDate fecha) {
         List<Turno> turnosEnFecha = new ArrayList<>();
         for (Turno turno : this.listaTurnos) {
             if (turno.getFecha().equals(fecha)) {
                 turnosEnFecha.add(turno);
             }
         }
         return turnosEnFecha;
     }

     
     /**
      * Autentica un usuario basado en su login y contraseña
      * @param login El nombre de usuario
      * @param password La contraseña
      * @return El objeto Usuario si las credenciales son válidas, null en caso contrario
      */
     public Usuario autenticarUsuario(String login, String password) {
    	    if (login == null || password == null || login.isEmpty() || password.isEmpty()) {
    	        System.err.println("Error: Login y contraseña no pueden estar vacíos");
    	        return null;
    	    }
    	    
    	    for (Usuario usuario : listaUsuarios) {
    	        if (usuario.getLogin().equalsIgnoreCase(login)) {
    	            // Verificar contraseña
    	            if (usuario.getContrasena().equals(password)) {
    	                return usuario;
    	            } else {
    	                System.err.println("Contraseña incorrecta");
    	                return null;
    	            }
    	        }
    	    }
    	    
    	    System.err.println("No se encontró usuario con ese login");
    	    return null;
    	}

     /**
      * Registra un nuevo cliente en el sistema y persiste los cambios en el archivo JSON
      * @param nuevoCliente El cliente a registrar
      * @return true si el registro fue exitoso, false si el usuario ya existe
      */
     public boolean registrarUsuario(Cliente nuevoCliente) {
         if (nuevoCliente == null) {
             System.err.println("Error: El cliente no puede ser nulo");
             return false;
         }
         
         // Validar campos obligatorios
         if (nuevoCliente.getNombre() == null || nuevoCliente.getNombre().isEmpty() ||
             nuevoCliente.getLogin() == null || nuevoCliente.getLogin().isEmpty() ||
             nuevoCliente.getContrasena() == null || nuevoCliente.getContrasena().isEmpty()) {
             System.err.println("Error: Nombre, login y contraseña son obligatorios");
             return false;
         }
         
         // Validar que no exista un usuario con el mismo login
         for (Usuario usuario : listaUsuarios) {
             if (usuario.getLogin().equalsIgnoreCase(nuevoCliente.getLogin())) {
                 System.err.println("Error: Ya existe un usuario con ese login");
                 return false;
             }
         }
         
         try {
             // 1. Agregar el nuevo cliente a la lista de usuarios
             listaUsuarios.add(nuevoCliente);
             System.out.println("Cliente registrado exitosamente: " + nuevoCliente.getNombre());
             
             // 2. Persistir los cambios en el archivo JSON
             guardarCambios();
             
             return true;
         } catch (Exception e) {
             System.err.println("Error al registrar cliente: " + e.getMessage());
             return false;
         }
     }

     /**
      * Método auxiliar para guardar los cambios en el archivo JSON predeterminado
      */
     private void guardarCambios() {
         try {
             // Usamos la ruta predeterminada "data/parque.json"
             salvarParque("data/parque.json", CentralPersistencia.JSON);
             System.out.println("Cambios guardados exitosamente en el archivo JSON");
         } catch (Exception e) {
             System.err.println("Error al guardar los cambios en el archivo JSON: " + e.getMessage());
             throw new RuntimeException("No se pudo persistir el registro del usuario", e);
         }
     }

	public List<Turno> obtenerTurnosEmpleado(Empleado emp) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tiquete obtenerTiquete(Cliente cliente, Atraccion seleccionada) {
		// TODO Auto-generated method stub
		return null;
	}

	public Atraccion obtenerAtraccionAsignada(Empleado empleado) {
		// TODO Auto-generated method stub
		return null;
	}	
}

