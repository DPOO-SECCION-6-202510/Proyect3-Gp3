package project1_parqueUsuarios.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.FastPass;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Cliente;

public class ClienteTest {

    private Cliente cliente1;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Tiquete tiqueteF;

    @BeforeEach
    void setUp() throws Exception {
        cliente1 = new Cliente("Felipe", "f.rojasd", "1234");
        tiqueteF = new Tiquete(Categoria.Diamante, true, "FP001", null);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() throws Exception {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testConstructorConArgumentos() {
        Cliente cliente = new Cliente("Carlos", "c.perez", "5678");
        assertEquals("Carlos", cliente.getNombre());
        assertEquals("c.perez", cliente.getLogin());
        // Usa los getters específicos para las listas
        assertNotNull(cliente.getTiquetesNoUsados());
        assertTrue(cliente.getTiquetesNoUsados().isEmpty());
        assertNotNull(cliente.getTiquetesUsados());
        assertTrue(cliente.getTiquetesUsados().isEmpty());
    }

    @Test
    void testConstructorPorDefecto() {
        Cliente clienteDefault = new Cliente();
        assertNull(clienteDefault.getNombre());
        assertNull(clienteDefault.getLogin());
        assertNotNull(clienteDefault.getTiquetesNoUsados());
        assertTrue(clienteDefault.getTiquetesNoUsados().isEmpty());
        assertNotNull(clienteDefault.getTiquetesUsados());
        assertTrue(clienteDefault.getTiquetesUsados().isEmpty());
        // No necesitas probar la salida por consola aquí, eso se hace en testMostrarTiquetes
    }

    @Test
    void testGetTiquetes_ListaVacia() {
        // El método mostrarTiquetes es el que imprime, no los getters.
        // Este test debería verificar que las listas están vacías directamente.
        assertTrue(cliente1.getTiquetesNoUsados().isEmpty(), "Inicialmente no debería haber tiquetes no usados.");
        assertTrue(cliente1.getTiquetesUsados().isEmpty(), "Inicialmente no debería haber tiquetes usados.");
    }

    @Test
    void testGetCompra() {
        assertEquals(0, Cliente.getCompra(), "getCompra debería devolver 0.");
    }

    @Test
    void testToString_FormatoCorrecto() {
        assertEquals("Cliente{nombre='Felipe', login='f.rojasd'}", cliente1.toString(), "toString debería tener el formato correcto.");
    }

    @Test
    void testComprarTiquete() {
        Tiquete tiquete1 = new FastPass(Categoria.Diamante, false, new Date(), "FP002");
        cliente1.comprarTiquete(tiquete1);
        assertEquals(1, cliente1.getTiquetesNoUsados().size(), "Debería haber un tiquete no usado después de la compra.");
        assertTrue(cliente1.getTiquetesNoUsados().contains(tiquete1), "La lista de tiquetes no usados debería contener el tiquete comprado.");
    }

    @Test
    void testComprarTiquete_TiqueteNull() {
        cliente1.comprarTiquete(null);
        assertTrue(cliente1.getTiquetesNoUsados().isEmpty(), "No debería agregar un tiquete si es null.");
    }

    @Test
    void testUsarTiquete_TiqueteExistente() {
        cliente1.comprarTiquete(tiqueteF);
        boolean resultado = cliente1.usarTiquete(tiqueteF);
        assertTrue(resultado, "usarTiquete debería devolver true si el tiquete existía.");
        assertTrue(cliente1.getTiquetesNoUsados().isEmpty(), "El tiquete debería moverse de la lista de no usados.");
        assertEquals(1, cliente1.getTiquetesUsados().size(), "El tiquete debería estar en la lista de usados.");
        assertTrue(cliente1.getTiquetesUsados().contains(tiqueteF), "La lista de tiquetes usados debería contener el tiquete usado.");
        assertTrue(tiqueteF.isFueUsado(), "El tiquete debería estar marcado como usado.");
    }

    
    @Test
    void testUsarTiquete_TiqueteNoExistente() {
        Tiquete otroTiquete = new FastPass(Categoria.Familiar, false, new Date(), "FP003");
        boolean resultado = cliente1.usarTiquete(otroTiquete);
        assertFalse(resultado, "usarTiquete debería devolver false si el tiquete no existe en los no usados.");
        assertTrue(cliente1.getTiquetesNoUsados().isEmpty(), "La lista de no usados no debería cambiar.");
        assertTrue(cliente1.getTiquetesUsados().isEmpty(), "La lista de usados no debería cambiar.");
        assertFalse(otroTiquete.isFueUsado(), "El tiquete no debería ser marcado como usado.");
    }

    @Test
    void testGetTiquetesNoUsados() {
        Tiquete tiquete1 = new FastPass(Categoria.Diamante, false, new Date(), "FP002");
        Tiquete tiquete2 = new FastPass(Categoria.Oro, true, new Date(), "FP003");
        cliente1.comprarTiquete(tiquete1);
        cliente1.comprarTiquete(tiquete2);
        List<Tiquete> noUsados = cliente1.getTiquetesNoUsados();
        assertEquals(2, noUsados.size(), "Debería devolver la lista de tiquetes no usados.");
        assertTrue(noUsados.contains(tiquete1));
        assertTrue(noUsados.contains(tiquete2));
        // Verificar que la lista devuelta es una copia (para evitar modificaciones externas)
        noUsados.clear();
        assertEquals(2, cliente1.getTiquetesNoUsados().size(), "La lista original no debería ser modificada.");
    }

    @Test
    void testGetTiquetesUsados() {
        cliente1.comprarTiquete(tiqueteF);
        cliente1.usarTiquete(tiqueteF);
        List<Tiquete> usados = cliente1.getTiquetesUsados();
        assertEquals(1, usados.size(), "Debería devolver la lista de tiquetes usados.");
        assertTrue(usados.contains(tiqueteF));
        // Verificar que la lista devuelta es una copia
        usados.clear();
        assertEquals(1, cliente1.getTiquetesUsados().size(), "La lista original no debería ser modificada.");
    }

    @Test
    void testMostrarTiquetes_SinTiquetes() {
        cliente1.mostrarTiquetes();
        String expectedOutput = "=== Tiquetes No Usados ===\n" +
                                "No hay tiquetes disponibles.\n" +
                                "\n" +
                                "=== Tiquetes Usados ===\n" +
                                "No hay tiquetes usados.\n";
        assertEquals(expectedOutput, outContent.toString(), "Debería mostrar el mensaje correcto cuando no hay tiquetes.");
    }

    @Test
    void testMostrarTiquetes_ConTiquetes() {
        Tiquete tiquete1 = new FastPass(Categoria.Diamante, false, new Date(), "FP002");
        Tiquete tiqueteUsado = new FastPass(Categoria.Oro, true, new Date(), "FP003");
        cliente1.comprarTiquete(tiquete1);
        cliente1.comprarTiquete(tiqueteUsado);
        cliente1.usarTiquete(tiqueteUsado);

        cliente1.mostrarTiquetes();
        String expectedOutput = "=== Tiquetes No Usados ===\n" +
                                tiquete1.toString() + "\n" +
                                "\n" +
                                "=== Tiquetes Usados ===\n" +
                                tiqueteUsado.toString() + "\n";
        assertEquals(expectedOutput, outContent.toString(), "Debería mostrar la información de los tiquetes.");
    }
}