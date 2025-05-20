package sistema_parque.usuarios;

import java.util.ArrayList;
import java.util.List;
import sistema_parque.tiquetes.Tiquete;

public class Cliente extends Usuario {
    private static final int COMPRA = 0;
    private List<Tiquete> tiquetesNoUsados;
    private List<Tiquete> tiquetesUsados;

    public Cliente(String nombre, String login, String contrasena) {
        super(nombre, login, contrasena);
        this.tiquetesNoUsados = new ArrayList<>();
        this.tiquetesUsados = new ArrayList<>();
    }
    
    // Método para comprar un tiquete
    public void comprarTiquete(Tiquete tiquete) {
        if (tiquete != null) {
            tiquetesNoUsados.add(tiquete);
        }
    }
    
    // Método para usar un tiquete
    public boolean usarTiquete(Tiquete tiquete) {
        if (tiquetesNoUsados.contains(tiquete)) {
            tiquetesNoUsados.remove(tiquete);
            tiquete.marcarComoUsado();
            tiquetesUsados.add(tiquete);
        }
		return false;
    }
    
    // Getters para los tiquetes
    public List<Tiquete> getTiquetesNoUsados() {
        return new ArrayList<>(tiquetesNoUsados);
    }
    
    public List<Tiquete> getTiquetesUsados() {
        return new ArrayList<>(tiquetesUsados);
    }
    
    // Método para mostrar tiquetes
    public void mostrarTiquetes() {
        System.out.println("=== Tiquetes No Usados ===");
        if (tiquetesNoUsados.isEmpty()) {
            System.out.println("No hay tiquetes disponibles.");
        } else {
            tiquetesNoUsados.forEach(System.out::println);
        }
        
        System.out.println("\n=== Tiquetes Usados ===");
        if (tiquetesUsados.isEmpty()) {
            System.out.println("No hay tiquetes usados.");
        } else {
            tiquetesUsados.forEach(System.out::println);
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
