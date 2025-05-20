package CONSOLA;

import java.io.File;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.*;
import sistema_parque.atracciones.*;
import sistema_parque.tiquetes.*;
import java.time.LocalDate;
import CONTROL_PERSISTENCIA.CentralPersistencia;

public class ConsolaBasica {
    // Ruta relativa al archivo JSON (opción 5)
    private static final String RUTA_ARCHIVO_JSON = "Data" + File.separator + "parque_inicial.json";
    
    private PrincipalParque parque;
    private Scanner scanner;
    private Usuario usuarioAutenticado;

    public ConsolaBasica() {
        parque = new PrincipalParque();
        scanner = new Scanner(System.in);
        usuarioAutenticado = null;
    }

    public void iniciarSistema() {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║          SISTEMA DE GESTIÓN - PARQUE DE ATRACCIONES       ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");

        cargarDatosIniciales();

        int opcion = -1;
        while (opcion != 0) {
            mostrarMenuPrincipal();
            
            try {
                System.out.print("Seleccione una opción (0 para salir): ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        iniciarSesionYRedirigir();
                        break;
                    case 2:
                        registrarCliente();
                        break;
                    case 3:
                        mostrarInfoPublica();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema...");
                        guardarDatosFinales();
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrarse como cliente");
        System.out.println("3. Ver información pública");
        System.out.println("0. Salir del sistema");
    }

    private void cargarDatosIniciales() {
        try {
            System.out.println("\nBuscando archivo de datos en: " + RUTA_ARCHIVO_JSON);
            
            // Verificar si el directorio Data existe, si no, crearlo
            File directorioData = new File("Data");
            if (!directorioData.exists()) {
                directorioData.mkdir();
                System.out.println("✓ Directorio 'Data' creado");
            }
            
            // Verificar si el archivo existe
            File archivo = new File(RUTA_ARCHIVO_JSON);
            if (!archivo.exists()) {
                System.out.println("⚠ Archivo no encontrado. Se creará uno nuevo al guardar.");
                return;
            }
            
            parque.cargarParque(RUTA_ARCHIVO_JSON, CentralPersistencia.JSON);
            System.out.println("✓ Datos cargados correctamente");
        } catch (Exception e) {
            System.err.println("✗ Error al cargar datos: " + e.getMessage());
            System.out.println("El sistema iniciará con datos vacíos");
        }
    }

    private void guardarDatosFinales() {
        try {
            // Asegurarse que el directorio existe
            new File("Data").mkdirs();
            
            parque.salvarParque(RUTA_ARCHIVO_JSON, CentralPersistencia.JSON);
            System.out.println("✓ Datos guardados correctamente en: " + RUTA_ARCHIVO_JSON);
        } catch (Exception e) {
            System.err.println("✗ Error al guardar datos: " + e.getMessage());
        }
    }

    private void iniciarSesionYRedirigir() {
        System.out.println("\n--- INICIO DE SESIÓN ---");
        System.out.print("Ingrese su login: ");
        String login = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String password = scanner.nextLine();

        usuarioAutenticado = parque.autenticarUsuario(login, password);
        
        if (usuarioAutenticado != null) {
            System.out.println("\n✓ Bienvenido, " + usuarioAutenticado.getNombre() + "!");
            redirigirAConsolaEspecifica();
        } else {
            System.err.println("✗ Credenciales incorrectas. Intente nuevamente.");
        }
    }

    private void redirigirAConsolaEspecifica() {
        if (usuarioAutenticado instanceof Administrador) {
            ConsolaAdministrador.main(parque, (Administrador) usuarioAutenticado);
        } else if (usuarioAutenticado instanceof Empleado) {
            ConsolaEmpleado.main(parque, (Empleado) usuarioAutenticado);
        } else if (usuarioAutenticado instanceof Cliente) {
            ConsolaCliente.main(parque, (Cliente) usuarioAutenticado);
        }
    }

    private void registrarCliente() {
        System.out.println("\n--- REGISTRO DE CLIENTE ---");
        System.out.print("Ingrese su nombre completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Elija un nombre de usuario: ");
        String login = scanner.nextLine();
        System.out.print("Elija una contraseña: ");
        String password = scanner.nextLine();

        try {
            Cliente nuevoCliente = new Cliente(nombre, login, password);
            if (parque.registrarUsuario(nuevoCliente)) {
                System.out.println("✓ Registro exitoso! Ahora puede iniciar sesión.");
            } else {
                System.err.println("✗ El nombre de usuario ya está en uso.");
            }
        } catch (Exception e) {
            System.err.println("✗ Error en el registro: " + e.getMessage());
        }
    }

    private void mostrarInfoPublica() {
        System.out.println("\n--- INFORMACIÓN PÚBLICA ---");
        int opcion = -1;
        while (opcion != 0) {
            mostrarMenuDemostraciones();
            try {
                System.out.print("Seleccione la demostración a ejecutar (0 para salir): ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el newline

                switch (opcion) {
                    case 1:
                        demostrarVerAtracciones();
                        break;
                    case 2:
                        demostrarVerUsuarios(); // Example: Show users
                        break;
                    case 3:
                        demostrarAsignarTurno(); // Example: Demo shift assignment
                        break;
                    case 0:
                        System.out.println("Saliendo de la consola de demostración.");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer del scanner
                opcion = -1; // Resetear opción para evitar bucle infinito si no era 0
            } catch (Exception e) {
                // Captura genérica para otros errores inesperados durante las demos
                System.err.println("Ocurrió un error inesperado durante la demostración: " + e.getMessage());
                e.printStackTrace(); // Útil para depuración
            }
            if (opcion != 0) {
                System.out.println("\nPresione Enter para continuar...");
                scanner.nextLine(); // Pausa para que el usuario pueda leer la salida
            }
        }
        System.out.println("--- Fin de la Demostración ---");
    }

    /**
     * Muestra el menú de opciones de demostración disponibles.
     */
    private void mostrarMenuDemostraciones() {
        System.out.println("\n--- Menú de Demostraciones ---");
        System.out.println("1. Ver lista de Atracciones");
        System.out.println("2. Ver lista de Usuarios");
        System.out.println("3. Demostrar Asignación de Turno (Admin)");
        System.out.println("0. Salir");
        System.out.println("-----------------------------");
    }
    
    /**
     * Demuestra cómo visualizar las atracciones cargadas.
     */
    private void demostrarVerAtracciones() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                LISTADO DE ATRACCIONES                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        List<Atraccion> atracciones = parque.getListaAtracciones();
        if (atracciones == null || atracciones.isEmpty()) {
            System.out.println("> No hay atracciones cargadas en el parque.");
            return;
        }
        
        System.out.println("✓ Total atracciones: " + atracciones.size());
        System.out.println("┌───────────────────────────────────────────────────────────┐");
        
        for (Atraccion atr : atracciones) {
            String tipo = atr.getClass().getSimpleName();
            String categoria = atr.getClasificacionCategoria();
            
            System.out.println("│ " + atr.getNombre());
            System.out.println("├───────────────────────────────────────────────────────────┤");
            System.out.println("│  - Tipo: " + String.format("%-15s", tipo) + "  - Categoría: " + categoria);
            
            // Información específica según tipo de atracción
            if (tipo.equals("AtraccionCultural")) {
                System.out.println("│  - Ubicación: " + atr.getUbicacion());
                System.out.println("│  - Restricción edad: " + formatearRestriccion(atr.getRestriccionEdad()));
            } else if (tipo.equals("AtraccionMecanica")) {
            } else if (tipo.equals("Espectaculo")) {
            }
            
            System.out.println("│  - Aforo máximo: " + atr.getCupoMaximo() + " personas");
            System.out.println("└───────────────────────────────────────────────────────────┘");
        }
    }

    private String formatearRestriccion(int edad) {
        if (edad == 0) return "Sin restricción";
        return edad + "+ años";
    }

    /**
     * Demuestra cómo visualizar los usuarios cargados.
     */
    private void demostrarVerUsuarios() {
        System.out.println("\n╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║                   LISTADO DE USUARIOS                    ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝");
        
        List<Usuario> usuarios = parque.getListaUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            System.out.println("> No hay usuarios cargados en el parque.");
            return;
        }
        
        System.out.println("- Total usuarios: " + usuarios.size());
        
        for (Usuario usr : usuarios) {
            
            System.out.println("┌───────────────────────────────────────────────────────────┐");
            System.out.println("│ " + "Nombre: " + usr.getNombre() + ", Login: " + usr.getLogin());
            System.out.println("├───────────────────────────────────────────────────────────┤");
            
            // Mostrar información de tiquetes
            if (!usr.getListaTiquetesNoUsados().isEmpty()) {
                System.out.println("│ Tiquetes No Usados: " + usr.getListaTiquetesNoUsados().size());
                // mostrar detalles de tiquetes no usados
                mostrarDetalleTiquetes(usr.getListaTiquetesNoUsados(), false);
            } else {
                System.out.println("│ Tiquetes No Usados: 0");
            }
            
            if (!usr.getListaTiquetesUsados().isEmpty()) {
                System.out.println("│ - Tiquetes Usados: " + usr.getListaTiquetesUsados().size());
                // mostrar detalles de tiquetes usados
                mostrarDetalleTiquetes(usr.getListaTiquetesUsados(), true);
            } else {
                System.out.println("│ - Tiquetes Usados: 0");
            }
            
            System.out.println("└───────────────────────────────────────────────────────────┘");
        }
    }

    // Método para mostrar detalles de tiquetes
    private void mostrarDetalleTiquetes(List<Tiquete> tiquetes, boolean usados) {
        String status = usados ? "usado" : "disponible";
        // Si queremos mostrar más detalles
        int limite = Math.min(tiquetes.size(), 3);
        for (int i = 0; i < limite; i++) {
            Tiquete t = tiquetes.get(i);
            System.out.println("│     ↳ Tiquete #" + t.getId() + " - " + " (" + status + ")");
        }
        if (tiquetes.size() > 3) {
            System.out.println("│     ↳ ... y " + (tiquetes.size() - 3) + " tiquete(s) más");
        }
    }

    /**
     * Demuestra la funcionalidad de asignar un turno por parte del Admin.
     * Utiliza datos hardcodeados o busca datos específicos cargados.
     */
    private void demostrarAsignarTurno() {
        System.out.println("\n--- Demostración: Asignar Turno ---");
        Administrador admin = parque.getAdministrador(); // Asume que PrincipalParque lo gestiona
        if (admin == null) {
            System.err.println("Error: No se encontró un Administrador en el sistema.");
            return;
        }

        // --- OBTENER DATOS PARA LA DEMO ---
        // Esto debería buscar en las listas cargadas o usar datos fijos

        Empleado empleado = buscarEmpleadoPorLogin("empleado1"); // Necesitarías un método de búsqueda
        Atraccion lugar = buscarAtraccionPorNombre("Montaña Rusa X"); // Necesitarías un método de búsqueda
        LocalDate fecha = LocalDate.now(); // O una fecha específica
        
        // Cambio aquí: Crear un objeto TipoTurno en lugar de usar un enum
        TipoTurno tipo = new TipoTurno("APERTURA", "Turno de Apertura");
        
        List<Turno> listaTurnos = parque.getListaTurnos();

        if (empleado == null || lugar == null || listaTurnos == null) {
             System.err.println("Error: No se encontraron los datos necesarios (empleado, lugar o lista de turnos) para la demostración.");
             return;
        }

        System.out.println("Intentando asignar a " + empleado.getLogin() + " a " + lugar.getNombre() + " el " + fecha + " en turno " + tipo);

        // Crear un nuevo objeto Turno con el TipoTurno actualizado
        Turno nuevoTurno = new Turno(empleado, lugar, fecha, tipo);
        
        // Llamar al método del administrador
        boolean exito = admin.asignarTurno(empleado, lugar, nuevoTurno, listaTurnos);

        if (exito) {
            System.out.println("Asignación de turno registrada exitosamente (simulado).");
            // Mostrar la lista de turnos actualizada o el turno específico
            System.out.println("Turnos actuales (" + listaTurnos.size() + "):");
             for(Turno t : listaTurnos){
                 if(t.getEmpleadoAsignado().equals(empleado) && t.getFecha().equals(fecha) && t.getTipoTurno().equals(tipo)){
                    System.out.println("-> " + t.toString()); // Resaltar el turno añadido/modificado
                 } else {
                 }
             }
        } else {
            System.err.println("Fallo al registrar la asignación de turno.");
        }
        System.out.println("------------------------------------");
    }
    
    private Empleado buscarEmpleadoPorLogin(String login) {
        if (parque.getListaUsuarios() == null) return null; // Asegurar que la lista exista
        for (Usuario u : parque.getListaUsuarios()) {
            if (u instanceof Empleado && u.getLogin().equalsIgnoreCase(login)) {
                return (Empleado) u;
            }
        }
        System.err.println("Advertencia: No se encontró empleado con login: " + login);
        return null;
    }

     private Cliente buscarClientePorLogin(String login) {
         if (parque.getListaUsuarios() == null) return null;
         for (Usuario u : parque.getListaUsuarios()) {
             if (u instanceof Cliente && u.getLogin().equalsIgnoreCase(login)) {
                 return (Cliente) u;
             }
         }
          System.err.println("Advertencia: No se encontró cliente con login: " + login);
         return null;
     }

    private Atraccion buscarAtraccionPorNombre(String nombre) {
         if (parque.getListaAtracciones() == null) return null;
         for (Atraccion a : parque.getListaAtracciones()) {
             if (a.getNombre().equalsIgnoreCase(nombre)) {
                 return a;
             }
         }
         System.err.println("Advertencia: No se encontró atracción con nombre: " + nombre);
         return null;
    }


    public static void main(String[] args) {
        ConsolaBasica consola = new ConsolaBasica();
        consola.iniciarSistema();
    }
}