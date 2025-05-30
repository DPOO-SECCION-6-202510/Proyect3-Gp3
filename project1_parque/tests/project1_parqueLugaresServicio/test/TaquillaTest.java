package project1_parqueLugaresServicio.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import sistema_parque.lugaresServicio.Cajero;
import sistema_parque.lugaresServicio.Taquilla;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;
import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.Tiquete;

import java.util.List;
import java.util.ArrayList;

public class TaquillaTest {

    private Taquilla taquilla;
    private Cajero cajero;
    private Cliente cliente;
    private Tiquete tiquete;
    private Empleado empleado;

    @BeforeEach
    void setUp() {
        cajero = new Cajero("TR-1001", null);
        taquilla = new Taquilla("Taquilla Principal", cajero);
        cliente = new Cliente("Ana", "ana_cliente", "pass456");
        tiquete = new Tiquete(Categoria.Familiar, false, "TQ-001", null);
        empleado = new Empleado("Pedro", "pedro_emp", "pass789", "manejador caja", new ArrayList<>(), true, false);
    }

    @Test
    @DisplayName("Constructor con nombre y cajero inicializa correctamente")
    void testConstructorConParametros() {
        assertEquals("Taquilla Principal", taquilla.getNombre());
        assertFalse(taquilla.isAbierto());
        assertEquals(0, taquilla.getListaEmpleados().size());
        taquilla.agregarEmpleado(empleado);
        assertTrue(taquilla.getListaEmpleados().contains(empleado));
        assertTrue(taquilla.getListaTiquetesVender().isEmpty());
    }

    @Test
    @DisplayName("Constructor por defecto inicializa correctamente")
    void testConstructorPorDefecto() {
        Taquilla taquillaDefault = new Taquilla();
        assertNull(taquillaDefault.getNombre());
        assertFalse(taquillaDefault.isAbierto());
        assertTrue(taquillaDefault.getListaEmpleados().isEmpty());
        assertTrue(taquillaDefault.getListaTiquetesVender().isEmpty());
    }

    @Test
    @DisplayName("Registrar venta añade tiquete al cliente")
    void testRegistrarVenta() {
        int tiquetesIniciales = cliente.getListaTiquetesNoUsados().size();
        taquilla.registrarVenta(cliente, tiquete);
        
        assertEquals(tiquetesIniciales + 1, cliente.getListaTiquetesNoUsados().size());
        assertTrue(cliente.getListaTiquetesNoUsados().contains(tiquete));
    }

    @Test
    @DisplayName("Agregar empleado funciona correctamente")
    void testAgregarEmpleado() {
        int empleadosIniciales = taquilla.getListaEmpleados().size();
        
        taquilla.agregarEmpleado(empleado);
        
        assertEquals(empleadosIniciales + 1, taquilla.getListaEmpleados().size());
        assertTrue(taquilla.getListaEmpleados().contains(empleado));
    }

    @Test
    @DisplayName("Agregar tiquete para vender funciona correctamente")
    void testAgregarTiqueteVender() {
        int tiquetesIniciales = taquilla.getListaTiquetesVender().size();
        
        taquilla.agregarTiqueteVender(tiquete);
        
        assertEquals(tiquetesIniciales + 1, taquilla.getListaTiquetesVender().size());
        assertTrue(taquilla.getListaTiquetesVender().contains(tiquete));
    }

    @Test
    @DisplayName("Registrar venta con cliente nulo causa excepción")
    void testRegistrarVentaClienteNulo() {
    	assertThrows(NullPointerException.class, () -> taquilla.registrarVenta(null, tiquete));
    }

    @Test
    @DisplayName("Registrar venta con tiquete nulo causa excepción")
    void testRegistrarVentaTiqueteNulo() {
    	assertThrows(NullPointerException.class, () -> taquilla.registrarVenta(null, tiquete));
    }

    @Test
    @DisplayName("Agregar empleado nulo no causa excepción")
    void testAgregarEmpleadoNulo() {
        assertDoesNotThrow(() -> taquilla.agregarEmpleado(null));
        assertEquals(1, taquilla.getListaEmpleados().size()); // Solo el cajero inicial
    }

    @Test
    @DisplayName("Agregar tiquete nulo no causa excepción")
    void testAgregarTiqueteNulo() {
        assertDoesNotThrow(() -> taquilla.agregarTiqueteVender(null));
        assertTrue(taquilla.getListaTiquetesVender().isEmpty());
    }
}