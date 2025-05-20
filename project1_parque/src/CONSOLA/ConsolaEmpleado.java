package CONSOLA;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Turno;
import sistema_parque.atracciones.Atraccion;

public class ConsolaEmpleado {
    private final PrincipalParque parque;
    private final Empleado empleado;
    private final Scanner scanner;

    public ConsolaEmpleado(PrincipalParque parque, Empleado empleado) {
        this.parque = parque;
        this.empleado = empleado;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          BIENVENIDO/A, " + empleado.getNombre().toUpperCase() + "       ║");
        System.out.println("╚══════════════════════════════════════════╝");

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== MENÚ EMPLEADO ===");
            System.out.println("1. Ver mis turnos");
            System.out.println("2. Gestionar atracción asignada");
            System.out.println("3. Ver catálogo de atracciones");
            System.out.println("4. Ver información de empleado");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        verTurnosAsignados();
                        break;
                    case 2:
                        gestionarAtraccionAsignada();
                        break;
                    case 3:
                        listarAtracciones();
                        break;
                    case 4:
                        mostrarInformacionEmpleado();
                        break;
                    case 0:
                        System.out.println("Cerrando sesión...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido");
                scanner.nextLine();
            }
        }
    }

    private void verTurnosAsignados() {
        System.out.println("\n--- MIS TURNOS ---");
        List<Turno> turnos = parque.obtenerTurnosEmpleado(empleado);
        
        if (turnos.isEmpty()) {
            System.out.println("No tienes turnos asignados");
            return;
        }
        
        turnos.forEach(turno -> {
            System.out.printf("- %s | %s | %s%n",
                turno.getFecha(),
                turno.getTipoTurno());
        });
    }

    private void gestionarAtraccionAsignada() {
        System.out.println("\n--- GESTIÓN DE ATRACCIÓN ---");
        Atraccion asignada = parque.obtenerAtraccionAsignada(empleado);
        
        if (asignada == null) {
            System.out.println("No tienes una atracción asignada actualmente");
            return;
        }
    }

    private void listarAtracciones() {
        System.out.println("\n--- CATÁLOGO DE ATRACCIONES ---");
        parque.getListaAtracciones().forEach(atr -> {
            System.out.printf("- %s (%s) | Estado: %s | Cupo: %d/%d%n",
                atr.getNombre(),
                atr.getClass().getSimpleName(),
                atr.getCupoMaximo());
        });
    }

    private void mostrarInformacionEmpleado() {
        System.out.println("\n--- MI INFORMACIÓN ---");
        System.out.println("Nombre: " + empleado.getNombre());
        System.out.println("Rol: " + empleado.getRol());
        System.out.println("Turnos asignados: " + parque.obtenerTurnosEmpleado(empleado).size());
    }

    public static void main(PrincipalParque parque, Empleado empleado) {
        new ConsolaEmpleado(parque, empleado).mostrarMenu();
    }
}