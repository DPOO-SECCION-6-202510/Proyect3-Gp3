package project1_parqueUsuarios.test;

import sistema_parque.usuarios.Cocinero;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Usuario;
import sistema_parque.tiquetes.*;
import java.util.Date;


public class CocineroTest {

    private Cocinero cocinero;

    // Este método se ejecuta antes de cada prueba.
    // Para probar ambos constructores, a veces es mejor inicializar
    // el objeto dentro de cada método de prueba o tener múltiples setups.
    // Por ahora, lo dejaremos así y crearemos instancias específicas en los tests si es necesario.
    @BeforeEach
    void setUp() throws Exception {
        // Inicializamos un cocinero básico para algunas pruebas.
        // Los valores específicos no importan mucho aquí si solo probamos `estaCapacitado`.
        cocinero = new Cocinero("Chef Test", "chef_login", "pass123", "Cocinero",
                new ArrayList<>(Arrays.asList("Manipulación de Alimentos")), true, false, false);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Se ejecuta después de cada prueba. Útil para limpiar recursos.
        cocinero = null;
    }

    @Test
    @DisplayName("Test para el constructor por defecto de Cocinero")
    void testConstructorPorDefecto() {
        Cocinero cocineroPorDefecto = new Cocinero();
        assertNotNull(cocineroPorDefecto, "El cocinero por defecto no debería ser null.");
        // Asumimos que un cocinero creado por defecto no está capacitado inicialmente.
        // Es importante notar que tu constructor por defecto no inicializa 'estaCapacitado'.
        // En Java, los booleanos se inicializan a 'false' por defecto si son miembros de instancia.
        assertFalse(cocineroPorDefecto.getEstaCapacitado(), "Un cocinero por defecto debería tener 'estaCapacitado' en false.");
    }

    @Test
    @DisplayName("Test para el constructor con parámetros de Cocinero")
    void testConstructorConParametros() {
        ArrayList<String> capacitaciones = new ArrayList<>(Arrays.asList("Cocina Internacional", "Repostería"));
        Cocinero cocineroConParams = new Cocinero("Ana Chef", "ana_chef", "ana_pass", "Cocinero Principal",
                capacitaciones, true, true, true);

        assertNotNull(cocineroConParams, "El cocinero no debería ser null.");
        assertEquals("Ana Chef", cocineroConParams.getNombre(), "El nombre no coincide.");
        assertEquals("ana_chef", cocineroConParams.getLogin(), "El login no coincide.");
        assertEquals("ana_pass", cocineroConParams.getContrasena(), "La contraseña no coincide.");
        assertEquals("Cocinero Principal", cocineroConParams.getRol(), "El rol no coincide.");
        assertEquals(capacitaciones, cocineroConParams.getCapacitaciones(), "Las capacitaciones no coinciden.");
        assertTrue(cocineroConParams.isTurnoDiurno(), "El turno diurno no coincide.");
        assertTrue(cocineroConParams.isTurnoNocturno(), "El turno nocturno no coincide.");
        assertTrue(cocineroConParams.getEstaCapacitado(), "El estado de capacitación no coincide con el parámetro (true).");

        Cocinero cocineroNoCapacitado = new Cocinero("Juan Ayudante", "juan_ayu", "juan_pass", "Ayudante de Cocina",
                new ArrayList<>(), false, true, false);
        assertFalse(cocineroNoCapacitado.getEstaCapacitado(), "El estado de capacitación no coincide con el parámetro (false).");
    }

    @Test
    @DisplayName("Test para getEstaCapacitado cuando es true")
    void testGetEstaCapacitadoTrue() {
        ArrayList<String> capacitaciones = new ArrayList<>();
        Cocinero cocineroCapacitado = new Cocinero("Pedro", "pedro_log", "pedro_con", "Cocinero", capacitaciones, true, false, true);
        assertTrue(cocineroCapacitado.getEstaCapacitado(), "getEstaCapacitado debería devolver true.");
    }

    @Test
    @DisplayName("Test para getEstaCapacitado cuando es false")
    void testGetEstaCapacitadoFalse() {
        // Usamos el cocinero del setUp que se inicializó con estaCapacitado = false
        assertFalse(cocinero.getEstaCapacitado(), "getEstaCapacitado debería devolver false.");
    }

    @Test
    @DisplayName("Test para el método Capacitado")
    void testCapacitado() {
        // Aseguramos que inicialmente no está capacitado (según el setUp)
        assertFalse(cocinero.getEstaCapacitado(), "El cocinero no debería estar capacitado inicialmente para este test.");

        // Llamamos al método para capacitarlo
        cocinero.Capacitado();

        // Verificamos que ahora está capacitado
        assertTrue(cocinero.getEstaCapacitado(), "Después de llamar a Capacitado(), getEstaCapacitado debería devolver true.");
    }

    @Test
    @DisplayName("Test para el método Capacitado cuando ya está capacitado")
    void testCapacitadoCuandoYaEstaCapacitado() {
        // Creamos un cocinero que ya está capacitado
        Cocinero cocineroYaCapacitado = new Cocinero("Laura", "laura_log", "lau_pass", "Cocinera",
                new ArrayList<>(), true, false, true);
        assertTrue(cocineroYaCapacitado.getEstaCapacitado(), "El cocinero debería estar capacitado inicialmente para este test.");

        // Llamamos al método Capacitado()
        cocineroYaCapacitado.Capacitado();

        // Verificamos que sigue capacitado
        assertTrue(cocineroYaCapacitado.getEstaCapacitado(), "Llamar a Capacitado() en un cocinero ya capacitado debería mantenerlo capacitado.");
    }
}
