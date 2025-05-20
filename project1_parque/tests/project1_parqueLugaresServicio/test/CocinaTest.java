package project1_parqueLugaresServicio.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.AfterEach;

import sistema_parque.lugaresServicio.Cocina;
import sistema_parque.usuarios.Cocinero;
import java.util.List;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class CocinaTest {

    private Cocina cocina;
    private Cocinero cocineroCapacitado;
    private Cocinero cocineroNoCapacitado;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    void setUp() {
        cocina = new Cocina();
        
        // Crear cocineros para las pruebas
        cocineroCapacitado = new Cocinero("Chef Capacitado", "chef_cap", "pass123", 
                                         "Cocinero", new ArrayList<>(), true, false, true);
        
        cocineroNoCapacitado = new Cocinero("Ayudante", "ayudante", "pass123", 
                                           "Ayudante", new ArrayList<>(), false, false, false);
        
        // Redirigir System.out y System.err
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void tearDown() {
        // Restaurar System.out y System.err originales
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    @DisplayName("Constructor inicializa lista de cocineros vacía")
    void testConstructor() {
        assertNotNull(cocina.getListaCocinerosAsignados());
        assertTrue(cocina.getListaCocinerosAsignados().isEmpty());
    }

    @Test
    @DisplayName("Agregar cocinero capacitado exitosamente")
    void testAgregarCocineroCapacitado() {
        assertTrue(cocina.agregarCocinero(cocineroCapacitado));
        assertEquals(1, cocina.getListaCocinerosAsignados().size());
        assertTrue(cocina.getListaCocinerosAsignados().contains(cocineroCapacitado));
        assertTrue(outContent.toString().contains("Cocinero chef_cap agregado a la cocina."));
    }

    @Test
    @DisplayName("No agregar cocinero no capacitado")
    void testAgregarCocineroNoCapacitado() {
        assertFalse(cocina.agregarCocinero(cocineroNoCapacitado));
        assertTrue(cocina.getListaCocinerosAsignados().isEmpty());
        assertTrue(errContent.toString().contains("Error: Solo Cocineros capacitados pueden ser agregados a la cocina."));
    }

    @Test
    @DisplayName("No agregar cocinero nulo")
    void testAgregarCocineroNulo() {
        assertFalse(cocina.agregarCocinero(null));
        assertTrue(cocina.getListaCocinerosAsignados().isEmpty());
        assertTrue(errContent.toString().contains("Error: Solo Cocineros capacitados pueden ser agregados a la cocina."));
    }

    @Test
    @DisplayName("No agregar cocinero duplicado")
    void testAgregarCocineroDuplicado() {
        cocina.agregarCocinero(cocineroCapacitado);
        assertFalse(cocina.agregarCocinero(cocineroCapacitado));
        assertEquals(1, cocina.getListaCocinerosAsignados().size());
        assertTrue(outContent.toString().contains("Cocinero chef_cap ya estaba asignado a esta cocina."));
    }

    @Test
    @DisplayName("Remover cocinero existente")
    void testRemoverCocineroExistente() {
        cocina.agregarCocinero(cocineroCapacitado);
        cocina.removerCocinero(cocineroCapacitado);
        assertTrue(cocina.getListaCocinerosAsignados().isEmpty());
        assertTrue(outContent.toString().contains("Cocinero chef_cap removido de la cocina."));
    }

    @Test
    @DisplayName("Remover cocinero no existente")
    void testRemoverCocineroNoExistente() {
        cocina.removerCocinero(cocineroCapacitado);
        assertTrue(cocina.getListaCocinerosAsignados().isEmpty());
        // No debería haber mensaje de remoción ya que no estaba en la lista
        assertFalse(outContent.toString().contains("removido de la cocina"));
    }

    @Test
    @DisplayName("Preparar producto con cocineros disponibles")
    void testPrepararProductoConCocineros() {
        cocina.agregarCocinero(cocineroCapacitado);
        assertTrue(cocina.prepararProducto("Hamburguesa"));
        String output = outContent.toString();
        assertTrue(output.contains("Cocina: Preparando Hamburguesa..."));
        assertTrue(output.contains("Cocina: Hamburguesa listo para ser entregado/servido."));
    }

    @Test
    @DisplayName("Preparar producto sin cocineros disponibles")
    void testPrepararProductoSinCocineros() {
        assertFalse(cocina.prepararProducto("Hamburguesa"));
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error en Cocina: No hay cocineros asignados para preparar Hamburguesa"));
        assertFalse(outContent.toString().contains("Hamburguesa listo"));
    }

    @Test
    @DisplayName("GetListaCocinerosAsignados devuelve copia defensiva")
    void testGetListaCocinerosAsignadosCopiaDefensiva() {
        cocina.agregarCocinero(cocineroCapacitado);
        List<Cocinero> lista = cocina.getListaCocinerosAsignados();
        lista.remove(0); // Esto no debería afectar la lista original
        
        assertEquals(1, cocina.getListaCocinerosAsignados().size());
        assertEquals(0, lista.size());
    }

    @Test
    @DisplayName("Preparar producto con nombre vacío o nulo")
    void testPrepararProductoNombreInvalido() {
        cocina.agregarCocinero(cocineroCapacitado);
        
        assertFalse(cocina.prepararProducto(null));
        
        String errorOutput = errContent.toString();
        assertTrue(errorOutput.contains("Error en Cocina: el producto esta vacio"));
    }
}