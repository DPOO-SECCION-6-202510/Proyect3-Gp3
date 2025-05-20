package project1_parqueLugaresServicio.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import sistema_parque.lugaresServicio.LugarServicio;

// Clase concreta para probar la clase abstracta
class LugarServicioConcreto extends LugarServicio {
    public LugarServicioConcreto(String nombre) {
        super(nombre);
    }
    
    public LugarServicioConcreto() {
        super();
    }
}

public class LugarServicioTest {

    private LugarServicio lugarConNombre;
    private LugarServicio lugarSinNombre;

    @BeforeEach
    void setUp() {
        lugarConNombre = new LugarServicioConcreto("Restaurante Principal");
        lugarSinNombre = new LugarServicioConcreto();
    }

    @Test
    @DisplayName("Constructor con nombre inicializa correctamente")
    void testConstructorConNombre() {
        assertEquals("Restaurante Principal", lugarConNombre.getNombre());
        assertFalse(lugarConNombre.isAbierto());
    }

    @Test
    @DisplayName("Constructor sin nombre inicializa correctamente")
    void testConstructorSinNombre() {
        assertNull(lugarSinNombre.getNombre());
        assertFalse(lugarSinNombre.isAbierto());
    }

    @Test
    @DisplayName("GetNombre devuelve el nombre correcto")
    void testGetNombre() {
        assertEquals("Restaurante Principal", lugarConNombre.getNombre());
    }

    @Test
    @DisplayName("IsAbierto devuelve false inicialmente")
    void testIsAbiertoInicialmente() {
        assertFalse(lugarConNombre.isAbierto());
        assertFalse(lugarSinNombre.isAbierto());
    }

    @Test
    @DisplayName("Abrir cambia el estado a abierto")
    void testAbrir() {
        lugarConNombre.abrir();
        assertTrue(lugarConNombre.isAbierto());
    }

    @Test
    @DisplayName("Cerrar cambia el estado a cerrado")
    void testCerrar() {
        lugarConNombre.abrir(); // Primero lo abrimos
        lugarConNombre.cerrar();
        assertFalse(lugarConNombre.isAbierto());
    }

    @Test
    @DisplayName("Secuencia abrir/cerrar funciona correctamente")
    void testSecuenciaAbrirCerrar() {
        // Estado inicial
        assertFalse(lugarConNombre.isAbierto());
        
        // Abrir
        lugarConNombre.abrir();
        assertTrue(lugarConNombre.isAbierto());
        
        // Cerrar
        lugarConNombre.cerrar();
        assertFalse(lugarConNombre.isAbierto());
        
        // Volver a abrir
        lugarConNombre.abrir();
        assertTrue(lugarConNombre.isAbierto());
    }

    @Test
    @DisplayName("Cerrar un lugar ya cerrado no tiene efecto")
    void testCerrarLugarYaCerrado() {
        assertFalse(lugarConNombre.isAbierto()); // Ya está cerrado
        lugarConNombre.cerrar(); // Intentar cerrar de nuevo
        assertFalse(lugarConNombre.isAbierto()); // Sigue cerrado
    }

    @Test
    @DisplayName("Abrir un lugar ya abierto no tiene efecto")
    void testAbrirLugarYaAbierto() {
        lugarConNombre.abrir();
        assertTrue(lugarConNombre.isAbierto()); // Está abierto
        lugarConNombre.abrir(); // Intentar abrir de nuevo
        assertTrue(lugarConNombre.isAbierto()); // Sigue abierto
    }
}
