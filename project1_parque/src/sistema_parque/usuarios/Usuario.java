package sistema_parque.usuarios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import sistema_parque.tiquetes.Tiquete;


public abstract class Usuario {

    private String nombre;
    private String login; 
    private String contrasena;

    protected List<Tiquete> listaTiquetesNoUsados;
    protected List<Tiquete> listaTiquetesUsados;

    
    public Usuario(String nombre, String login, String contrasena) {
    	
        this.nombre = Objects.requireNonNull(nombre, "El nombre no puede ser nulo.").trim();
        this.login = Objects.requireNonNull(login, "El login no puede ser nulo.").trim();
        this.contrasena = Objects.requireNonNull(contrasena, "La contraseña no puede ser nula."); 

        if (this.nombre.isEmpty() || this.login.isEmpty() || this.contrasena.isEmpty()) {
            throw new IllegalArgumentException("Nombre, login y contraseña no pueden estar vacíos.");
        }

        this.listaTiquetesNoUsados = new ArrayList<>();
        this.listaTiquetesUsados = new ArrayList<>();
    }
    
    protected Usuario() {
        // Inicializa las listas para evitar NullPointerException más tarde
        this.listaTiquetesNoUsados = new ArrayList<>();
        this.listaTiquetesUsados = new ArrayList<>();
    }

    // --- Getters ---

    public String getNombre() {
        return nombre;
    }

    public String getLogin() {
        return login;
    }


    public String getContrasena() {
        return contrasena;
    }

   
    public List<Tiquete> getListaTiquetesNoUsados() {
        return listaTiquetesNoUsados;
    }


    public List<Tiquete> getListaTiquetesUsados() {
        return listaTiquetesUsados;
    }

    
    public void agregarTiqueteComprado(Tiquete tiquete) {
        Objects.requireNonNull(tiquete, "El tiquete a agregar no puede ser nulo.");
        this.listaTiquetesNoUsados.add(tiquete);
        System.out.println("Tiquete agregado a " + this.login + ": " + tiquete); // Log simple
    }

    /**
     * Intenta usar un tiquete de la lista de no usados.
     * Si tiene éxito, mueve el tiquete a la lista de usados.
     * Se asume que la clase Tiquete tiene un método marcarComoUsado()
     * que gestiona su estado interno y previene doble uso.
     *
    
     */
    public boolean usarTiquete(Tiquete tiqueteAUsar) {
        if (tiqueteAUsar != null && this.listaTiquetesNoUsados.contains(tiqueteAUsar)) {
               boolean exitoAlMarcar = tiqueteAUsar.isFueUsado(); 

               if (exitoAlMarcar) {
                this.listaTiquetesNoUsados.remove(tiqueteAUsar);
                this.listaTiquetesUsados.add(tiqueteAUsar);
                System.out.println("Tiquete usado por " + this.login + ": " + tiqueteAUsar); // Log simple
                return true;
               } else {
                  System.out.println("Fallo al marcar tiquete como usado (quizás ya estaba usado): " + tiqueteAUsar);
                  return false; 
               }
        }
        System.out.println("Intento de usar tiquete fallido (no encontrado o nulo) por " + this.login + ": " + tiqueteAUsar);
        return false; 
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return login.equalsIgnoreCase(usuario.login); 
    }


    @Override
    public int hashCode() {
        return Objects.hash(login.toLowerCase());
    }


    @Override
    public String toString() {
        return "Usuario{" +
               "nombre='" + nombre + '\'' +
               ", login='" + login + '\'' +
               '}';
    }
}