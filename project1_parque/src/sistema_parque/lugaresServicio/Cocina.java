package sistema_parque.lugaresServicio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import sistema_parque.usuarios.Cocinero;

public class Cocina {

    private List<Cocinero> listaCocinerosAsignados; 

    public Cocina() {
        this.listaCocinerosAsignados = new ArrayList<>();

    }

  
    public boolean agregarCocinero(Cocinero cocinero) {
        // Validación: Asegurarse de que el cocinero esté capacitado
        if (cocinero != null && cocinero.getEstaCapacitado()) { 
             if (!this.listaCocinerosAsignados.contains(cocinero)) {
                 this.listaCocinerosAsignados.add(cocinero);
                 System.out.println("Cocinero " + cocinero.getLogin() + " agregado a la cocina.");
                 return true;
             } else {
                  System.out.println("Cocinero " + cocinero.getLogin() + " ya estaba asignado a esta cocina.");
                  return false;
             }
        } else {
             System.err.println("Error: Solo Cocineros capacitados pueden ser agregados a la cocina.");
             return false;
        }
    }

   
    public void removerCocinero(Cocinero cocinero) {
         if (this.listaCocinerosAsignados.remove(cocinero)) {
             System.out.println("Cocinero " + cocinero.getLogin() + " removido de la cocina.");
         }
    }


    public boolean prepararProducto(String nombreProducto) {
    	if (nombreProducto == null) {
    		System.err.println("Error en Cocina: el producto esta vacio");
    		return false;
    	}
        if (listaCocinerosAsignados.isEmpty()) {
             System.err.println("Error en Cocina: No hay cocineros asignados para preparar " + nombreProducto);
             return false;
        }
       
         System.out.println("Cocina: Preparando " + nombreProducto + "...");
         // Simular tiempo de preparación, etc.
         System.out.println("Cocina: " + nombreProducto + " listo para ser entregado/servido.");

       
         return true;
    }

 
    public List<Cocinero> getListaCocinerosAsignados() {
        // Devuelve una copia para evitar modificaciones externas no deseadas
        return new ArrayList<>(listaCocinerosAsignados);
    }

}