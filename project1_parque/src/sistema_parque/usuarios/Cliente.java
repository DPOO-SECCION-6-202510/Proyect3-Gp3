package sistema_parque.usuarios;

import java.util.ArrayList;
import java.util.List;
import sistema_parque.tiquetes.Tiquete;

public class Cliente extends Usuario {
    private static final int COMPRA = 0;

    public Cliente(String nombre, String login, String contrasena) {
        super(nombre, login, contrasena);
    }
    
    // Método para comprar un tiquete
    public void comprarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            listaTiquetesNoUsados.add(tiquete);
        }
    }
    
    // Método para usar un tiquete
    public boolean usarTiquete(Tiquete tiquete) {
        if (listaTiquetesNoUsados.contains(tiquete)) {
        	listaTiquetesNoUsados.remove(tiquete);
            tiquete.marcarComoUsado();
            listaTiquetesUsados.add(tiquete);
        }
		return false;
    }
    
    // Getters para los tiquetes
    public List<Tiquete> getTiquetesNoUsados() {
        return new ArrayList<>(listaTiquetesNoUsados);
    }
    
    public List<Tiquete> getTiquetesUsados() {
        return new ArrayList<>(listaTiquetesUsados);
    }
    
    // Método para mostrar tiquetes
    public void mostrarTiquetes() {
        System.out.println("=== Tiquetes No Usados ===");
        if (listaTiquetesNoUsados.isEmpty()) {
            System.out.println("No hay tiquetes disponibles.");
        } else {
        	listaTiquetesNoUsados.forEach(System.out::println);
        }
        
        System.out.println("\n=== Tiquetes Usados ===");
        if (listaTiquetesUsados.isEmpty()) {
            System.out.println("No hay tiquetes usados.");
        } else {
        	listaTiquetesUsados.forEach(System.out::println);
        }
    }
	
	
	public Cliente() {
        super();
    }

	
	public static int getCompra() {
		return COMPRA;
	}
	
	public String toString() {
        // Llama al toString() de Usuario y añade la especificación del tipo
        return "Cliente{" +
               "nombre='" + getNombre() + '\'' +
               ", login='" + getLogin() + '\'' +
               '}';
	}
	
	
	
	
}
