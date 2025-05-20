package project1_parqueLugaresServicio.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sistema_parque.lugaresServicio.Cafeteria;
import sistema_parque.lugaresServicio.Cajero;
import sistema_parque.lugaresServicio.Cocina;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Cocinero;
import sistema_parque.usuarios.Empleado;

public class CafeteriaTest {

    private Cafeteria cafeteria;
    private PrintStream salidaOriginal;
    private PrintStream errorOriginal;
    private ByteArrayOutputStream salidaEstandar;
    private ByteArrayOutputStream salidaError;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializar el administrador para cada prueba
        Cajero cajero = new Cajero("10001", null);
        Cocina cocina = new Cocina();
        
        // Crear y agregar cocineros a la cocina
        ArrayList<String> capacitaciones = new ArrayList<>();
        capacitaciones.add("Cocina Gourmet");
        
        Cocinero cocinero1 = new Cocinero("Cocinero Uno", "cocinero1", "pass123", "chef", 
                                         capacitaciones, true, false, true);
        
        Cocinero cocinero2 = new Cocinero("Cocinero Dos", "cocinero2", "pass456", "sous-chef", 
                                         capacitaciones, true, true, true);
        
        cocina.agregarCocinero(cocinero1);
        cocina.agregarCocinero(cocinero2);
        
        cafeteria = new Cafeteria("Cafe Majestuoso", cajero, cocina);
        cajero.setLugarServicio(cafeteria);
        cafeteria.setCajeroPrincipal(cajero);

        // Configurar la captura de la salida estándar y error para verificar mensajes de consola
        salidaOriginal = System.out;
        errorOriginal = System.err;
        salidaEstandar = new ByteArrayOutputStream();
        salidaError = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaEstandar));
        System.setErr(new PrintStream(salidaError));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Restaurar la salida estándar y error originales
        System.setOut(salidaOriginal);
        System.setErr(errorOriginal);
    }

    @Test
    @DisplayName("Test constructor con parámetros")
    void testConstructorConParametros() {
        // Crear cocineros para la cocina de prueba
        ArrayList<String> capacitaciones = new ArrayList<>();
        capacitaciones.add("Repostería");
        
        Cocinero cocinero = new Cocinero("Cocinero Test", "cocinero_test", "pass789", "pastry", 
                                       capacitaciones, false, true, true);
        
        Cajero cajero = new Cajero("id_test", null);
        Cocina cocina = new Cocina();
        cocina.agregarCocinero(cocinero);
        
        Cafeteria cafeteria = new Cafeteria("Cafe_test", cajero, cocina);
        cajero.setLugarServicio(cafeteria);
        cafeteria.setCajeroPrincipal(cajero);
        
        assertEquals("Cafe_test", cafeteria.getNombre());
        assertEquals(cajero.getIdCaja(), cafeteria.getCajeroPrincipal().getIdCaja());
        assertEquals(cocina.getListaCocinerosAsignados(), cafeteria.getCocina().getListaCocinerosAsignados());
        assertFalse(cafeteria.isAbierto());
        assertEquals("Cafe_test", cafeteria.getNombre());
        assertNotNull(Cafeteria.getProductosVendidos());
    }

    @Test
    @DisplayName("Test constructor sin parámetros")
    void testConstructorSinParametros() {
        // Prueba el constructor sin parámetros
        Cafeteria cafeteria = new Cafeteria();

        // Verificar que el rol se haya asignado correctamente
        assertNotNull(Cafeteria.getProductosVendidos());
    }

    @Test
    @DisplayName("Test solicitar preparacion producto con éxito")
    void testSolicitarPreparacionProducto() {
        // Preparar mockup de cocina que devuelve true (simulando éxito)
        Cocina cocina = cafeteria.getCocina();
        
        // Solicitar la preparación de un producto
        boolean resultado = cafeteria.solicitarPreparacionProducto("Café Americano");
        
        // Verificar el resultado
        assertTrue(resultado, "La preparación del producto debería ser exitosa");
        
        // Verificar que se imprimió el mensaje de confirmación en la salida estándar
        String mensajeEsperado = "Cafetería Cafe Majestuoso: Solicitando preparación de Café Americano";
        assertTrue(salidaEstandar.toString().contains(mensajeEsperado), 
                "Debe mostrar mensaje de solicitud de preparación");
        
        String mensajePreparacion = "Cafetería Cafe Majestuoso: Café Americano está listo/en preparación.";
        assertTrue(salidaEstandar.toString().contains(mensajePreparacion), 
                "Debe mostrar mensaje de confirmación de preparación");
        
        // Verificar que no hay mensajes de error
        assertEquals("", salidaError.toString().trim(), 
                "No debe haber mensajes de error en caso de éxito");
    }
    
    @Test
    @DisplayName("Test solicitar preparacion producto sin cocina")
    void testSolicitarPreparacionProductoSinCocina() {
        // Establecer cocina como null
        cafeteria.setCocina(null);
        
        // Intentar solicitar preparación
        boolean resultado = cafeteria.solicitarPreparacionProducto("Café Americano");
        
        // Verificar resultado
        assertFalse(resultado, "La preparación debe fallar si no hay cocina");
        
        // Mostrar las salidas para diagnóstico (solo en caso de depuración)
        System.setOut(salidaOriginal);
        System.setErr(errorOriginal);
        System.out.println("Salida estándar capturada: " + salidaEstandar.toString());
        System.out.println("Salida de error capturada: " + salidaError.toString());
        
        // Verificar mensaje de error
        String mensajeError = "Error: Cafetería Cafe Majestuoso no tiene cocina asignada.";
        assertTrue(salidaError.toString().contains(mensajeError), 
                "Debe mostrar mensaje de error por falta de cocina en System.err");
    }
    
    @Test
    @DisplayName("Test solicitar preparacion producto fallido")
    void testSolicitarPreparacionProductoFallido() {
        // Crear una cocina sin cocineros para simular fallo
        Cocina cocinaVacia = new Cocina();
        cafeteria.setCocina(cocinaVacia);
        
        // Intentar solicitar preparación - fallará porque no hay cocineros
        boolean resultado = cafeteria.solicitarPreparacionProducto("Producto No Disponible");
        
        // Verificar resultado
        assertFalse(resultado, "La preparación debe fallar si no hay cocineros");
        
        // Mostrar las salidas para diagnóstico
        System.setOut(salidaOriginal);
        System.setErr(errorOriginal);
        System.out.println("Salida estándar capturada: " + salidaEstandar.toString());
        System.out.println("Salida de error capturada: " + salidaError.toString());
        
        // Verificar que el mensaje de solicitud está en la salida estándar
        assertTrue(salidaEstandar.toString().contains("Cafetería Cafe Majestuoso: Solicitando preparación de Producto No Disponible"), 
                "Debe mostrar mensaje de solicitud en System.out");
                
        // Verificar el mensaje de error (adaptado a los mensajes que realmente produciría la clase Cocina)
        // Este mensaje debería estar en la salida de error System.err
        assertTrue(salidaError.toString().contains("Cafetería Cafe Majestuoso: Cocina no pudo preparar Producto No Disponible") || 
                  salidaError.toString().contains("Error en Cocina") ||
                  salidaError.toString().contains("No hay cocineros asignados"), 
                  "Debe mostrar mensaje de error específico en System.err");
    }
    
    @Test
    @DisplayName("Test registrar venta producto con éxito")
    void testRegistrarVentaProducto() {
        // Crear un cliente para la prueba
        Cliente cliente = new Cliente("ClienteTest", "ClienteTest123", "10203040");
        ArrayList<String> capacitaciones = new ArrayList<>();
        Empleado empleado = new Empleado("empleadoTest", "empleadoTest123", "123", "prueba",capacitaciones ,true, false);
        cafeteria.getCajeroPrincipal().asignarEmpleado(empleado);
        // Registrar venta
        boolean resultado = cafeteria.registrarVentaProducto("Café Expreso", cliente);
        
        // Verificar resultado
        assertTrue(resultado, "El registro de venta debería ser exitoso");
        
        // Verificar que se agregó a productos vendidos
        assertTrue(Cafeteria.getProductosVendidos().contains("Café Expreso"), 
                "El producto debería estar en la lista de vendidos");
        
        // Verificar mensajes en la salida estándar
        String mensajeVenta = "Cafetería Cafe Majestuoso: Intentando vender Café Expreso";
        assertTrue(salidaEstandar.toString().contains(mensajeVenta), 
                "Debe mostrar mensaje de intento de venta en System.out");
        
        String mensajeExito = "Cafetería Cafe Majestuoso: Venta de Café Expreso completada.";
        assertTrue(salidaEstandar.toString().contains(mensajeExito), 
                "Debe mostrar mensaje de venta completada en System.out");
        
        // Verificar que no hay mensajes de error
        assertEquals("", salidaError.toString().trim(), 
                "No debe haber mensajes de error en caso de éxito");
    }
    
    @Test
    @DisplayName("Test registrar venta producto sin cajero")
    void testRegistrarVentaProductoSinCajero() {
        // Establecer cajero como null
        cafeteria.setCajeroPrincipal(null);
        
        // Crear un cliente para la prueba
        Cliente cliente = new Cliente("ClienteTest", "ClienteTest123", "10203040");
        
        // Intentar registrar venta
        boolean resultado = cafeteria.registrarVentaProducto("Café Expreso", cliente);
        
        // Verificar resultado
        assertFalse(resultado, "La venta debe fallar si no hay cajero");
        
        // Mostrar las salidas para diagnóstico
        System.setOut(salidaOriginal);
        System.setErr(errorOriginal);
        System.out.println("Salida estándar capturada: " + salidaEstandar.toString());
        System.out.println("Salida de error capturada: " + salidaError.toString());
        
        // Verificar mensaje de error en la salida de error
        String mensajeError = "Error: Cafetería Cafe Majestuoso no tiene cajero asignado.";
        assertTrue(salidaError.toString().contains(mensajeError), 
                "Debe mostrar mensaje de error por falta de cajero en System.err");
    }
    
    @Test
    @DisplayName("Test registrar venta producto fallido")
    void testRegistrarVentaProductoFallido() {
        // Crear un cliente para la prueba
        Cliente cliente = new Cliente("ClienteTest", "ClienteTest123", "10203040");
        
        // Modificar el cajero para simular un fallo en la venta
        // (Este método depende de cómo esté implementado el Cajero para simular fallos)
        // Por ahora asumimos que podemos crear un cajero que siempre falla
        Cajero cajeroFallido = new Cajero("99999", cafeteria) {
            @Override
            public boolean registrarVentaItem(String nombreItem, int cantidad, Cliente cliente) {
                return false; // Simular fallo
            }
        };
        cafeteria.setCajeroPrincipal(cajeroFallido);
        
        // Intentar registrar venta
        boolean resultado = cafeteria.registrarVentaProducto("Producto Inexistente", cliente);
        
        // Verificar resultado
        assertFalse(resultado, "La venta debe fallar con un cajero que siempre falla");
        
        // Mostrar las salidas para diagnóstico
        System.setOut(salidaOriginal);
        System.setErr(errorOriginal);
        System.out.println("Salida estándar capturada: " + salidaEstandar.toString());
        System.out.println("Salida de error capturada: " + salidaError.toString());
        
        // Verificar el mensaje en la salida estándar
        assertTrue(salidaEstandar.toString().contains("Cafetería Cafe Majestuoso: Intentando vender Producto Inexistente"), 
                "Debe mostrar mensaje de intento de venta en System.out");
                
        // Verificar el mensaje de error en la salida de error
        String mensajeError = "Cafetería Cafe Majestuoso: Venta de Producto Inexistente falló.";
        assertTrue(salidaError.toString().contains(mensajeError), 
                "Debe mostrar mensaje de error por fallo en venta en System.err");
    }
    
    @Test
    @DisplayName("Test método estático venderProducto")
    void testVenderProducto() {
        // Guardar el tamaño inicial de la lista
        int tamañoInicial = Cafeteria.getProductosVendidos().size();
        
        // Llamar al método estático
        Cafeteria.venderProducto("Sándwich");
        
        // Verificar que se añadió el producto
        assertEquals(tamañoInicial + 1, Cafeteria.getProductosVendidos().size(), 
                "La lista debe aumentar en un elemento");
        assertTrue(Cafeteria.getProductosVendidos().contains("Sándwich"), 
                "El producto debe estar en la lista");
        
        // Verificar mensaje en la salida estándar
        String mensajeRegistro = "Registro estático: Añadido 'Sándwich' a productosVendidos.";
        assertTrue(salidaEstandar.toString().contains(mensajeRegistro), 
                "Debe mostrar mensaje de registro de venta en System.out");
        
        // Verificar que no hay mensajes de error
        assertEquals("", salidaError.toString().trim(), 
                "No debe haber mensajes de error al registrar un producto");
    }
}