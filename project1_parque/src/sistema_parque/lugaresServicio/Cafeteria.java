package sistema_parque.lugaresServicio;

import java.util.ArrayList;
import java.util.List;
import sistema_parque.usuarios.Cliente; 

public class Cafeteria extends LugarServicio {

    private Cocina cocina;
   
    private Cajero cajeroPrincipal; 

    private static List<String> productosVendidos;

    public Cafeteria() {
        super();
        inicializarListaVendidos();
    }

    public Cafeteria(String nombre, Cajero cajero, Cocina cocina) { 
        super(nombre); 
        this.cajeroPrincipal = cajero;
        this.cocina = cocina;
        inicializarListaVendidos();
    }

    private static synchronized void inicializarListaVendidos() {
         if (productosVendidos == null) {
            productosVendidos = new ArrayList<>();
         }
    }


   
    public boolean solicitarPreparacionProducto(String nombreProducto) {
        if (cocina == null) {
            System.err.println("Error: Cafetería " + getNombre() + " no tiene cocina asignada.");
            return false;
        }
        System.out.println("Cafetería " + getNombre() + ": Solicitando preparación de " + nombreProducto);
        // Llama al método CORREGIDO/RENOMBRADO en Cocina
        boolean exitoPreparacion = cocina.prepararProducto(nombreProducto);

        if (exitoPreparacion) {
            System.out.println("Cafetería " + getNombre() + ": " + nombreProducto + " está listo/en preparación.");
            // Aquí NO se registra la venta todavía, solo se confirma preparación.
        } else {
             System.err.println("Cafetería " + getNombre() + ": Cocina no pudo preparar " + nombreProducto);
        }
        return exitoPreparacion;
    }

    
    public boolean registrarVentaProducto(String nombreProducto, Cliente cliente) {
        if (this.cajeroPrincipal == null) {
            System.err.println("Error: Cafetería " + getNombre() + " no tiene cajero asignado.");
            return false;
        }

        System.out.println("Cafetería " + getNombre() + ": Intentando vender " + nombreProducto);
        // Usa el Cajero para registrar la venta y cobrar
        boolean exitoVenta = this.cajeroPrincipal.registrarVentaItem(nombreProducto, 1, cliente);

        if (exitoVenta) {
            // Si se quiere mantener la lista estática global (aunque no sea ideal)
             Cafeteria.venderProducto(nombreProducto);
             System.out.println("Cafetería " + getNombre() + ": Venta de " + nombreProducto + " completada.");
        } else {
             System.err.println("Cafetería " + getNombre() + ": Venta de " + nombreProducto + " falló.");
        }
        return exitoVenta;
    }


    
    public static void venderProducto(String producto) {
        inicializarListaVendidos(); 
        productosVendidos.add(producto);
        System.out.println("Registro estático: Añadido '" + producto + "' a productosVendidos.");
    }

    public Cocina getCocina() {
        return cocina;
    }

    public void setCocina(Cocina cocina) {
        this.cocina = cocina;
    }

    public Cajero getCajeroPrincipal() {
        return cajeroPrincipal;
    }

    public void setCajeroPrincipal(Cajero cajeroPrincipal) {
        this.cajeroPrincipal = cajeroPrincipal;
    }

	public static List<String> getProductosVendidos() {
		return productosVendidos;
	}    
    
}