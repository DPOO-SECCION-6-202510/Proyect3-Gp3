package CONSOLA;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Cliente;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.tiquetes.Tiquete;

public class ConsolaCliente {
    private final PrincipalParque parque;
    private final Cliente cliente;
    private final Scanner scanner;

    public ConsolaCliente(PrincipalParque parque, Cliente cliente) {
        this.parque = parque;
        this.cliente = cliente;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          BIENVENIDO/A, " + cliente.getNombre().toUpperCase() + "         ║");
        System.out.println("╚══════════════════════════════════════════╝");

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            System.out.println("1. Obtener tiquetes");
            System.out.println("2. Ver mis tiquetes");
            System.out.println("3. Usar tiquete en atracción");
            System.out.println("4. Ver catálogo de atracciones");
            System.out.println("5. Ver mi información");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        obtenerTiquetes();
                        break;
                    case 2:
                        listarTiquetesDisponibles();
                        break;
                    case 3:
                        usarTiquete();
                        break;
                    case 4:
                        listarAtracciones();
                        break;
                    case 5:
                        mostrarInformacionCliente();
                        break;
                    case 0:
                        System.out.println("Cerrando sesión de " + cliente.getNombre() + "...");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        }
    }

    private void obtenerTiquetes() {
        System.out.println("\n--- OBTENCIÓN DE TIQUETES ---");
        System.out.println("Atracciones disponibles:");
        
        List<Atraccion> atracciones = parque.getListaAtracciones();
        for (int i = 0; i < atracciones.size(); i++) {
            System.out.printf("%d. %s - %s%n", 
                i+1, 
                atracciones.get(i).getNombre(), 
                atracciones.get(i).getClasificacionCategoria());
        }

        try {
            System.out.print("\nSeleccione una atracción (número): ");
            int seleccion = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (seleccion >= 0 && seleccion < atracciones.size()) {
                Atraccion seleccionada = atracciones.get(seleccion);
                System.out.print("¿Confirmar obtención de tiquete para " + seleccionada.getNombre() + "? (S/N): ");
                String confirmacion = scanner.nextLine();
                
                if (confirmacion.equalsIgnoreCase("S")) {
                    Tiquete nuevoTiquete = parque.obtenerTiquete(cliente, seleccionada);
                    System.out.println("✓ Tiquete obtenido! ID: " + nuevoTiquete.getId());
                } else {
                    System.out.println("Operación cancelada");
                }
            } else {
                System.err.println("Número de atracción no válido");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido");
            scanner.nextLine();
        }
    }

    private void listarTiquetesDisponibles() {
        System.out.println("\n--- MIS TIQUETES DISPONIBLES ---");
        if (cliente.getListaTiquetesNoUsados().isEmpty()) {
            System.out.println("No tienes tiquetes disponibles");
            return;
        }
        
        cliente.getListaTiquetesNoUsados().forEach(tiquete -> {
            System.out.printf("- ID: %s | Atracción: %s | Categoría: %s%n",
                tiquete.getId(),
                tiquete.getAtraccion().getNombre(),
                tiquete.getAtraccion().getClasificacionCategoria());
        });
    }

    private void usarTiquete() {
        System.out.println("\n--- USAR TIQUETE ---");
        listarTiquetesDisponibles();
        
        if (cliente.getListaTiquetesNoUsados().isEmpty()) {
            return;
        }
        
        try {
            System.out.print("Ingrese el ID del tiquete a usar: ");
            String idTiquete = scanner.nextLine();
            boolean usado = false;
            for(Tiquete tiquete: cliente.getListaTiquetesNoUsados())
            	if (tiquete.getId() == idTiquete) {
            		usado = cliente.usarTiquete(tiquete);
            		break;
            	}
            if (usado) {
                System.out.println("✓ Tiquete usado exitosamente. ¡Disfrute su atracción!");
            } else {
                System.err.println("✗ No se encontró el tiquete o ya fue usado");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar tiquete: " + e.getMessage());
        }
    }

    private void listarAtracciones() {
        System.out.println("\n--- CATÁLOGO DE ATRACCIONES ---");
        parque.getListaAtracciones().forEach(atr -> {
            System.out.printf("- %s (%s) | Categoría: %s | Cupo: %d/%d%n",
                atr.getNombre(),
                atr.getClass().getSimpleName(),
                atr.getClasificacionCategoria(),
                atr.getCupoMaximo());
        });
    }

    private void mostrarInformacionCliente() {
        System.out.println("\n--- MI INFORMACIÓN ---");
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Tiquetes disponibles: " + cliente.getListaTiquetesNoUsados().size());
        System.out.println("Tiquetes usados: " + cliente.getListaTiquetesUsados().size());
    }

    public static void main(PrincipalParque parque, Cliente cliente) {
        new ConsolaCliente(parque, cliente).mostrarMenu();
    }
}