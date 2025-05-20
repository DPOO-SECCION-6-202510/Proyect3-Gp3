package project1_parqueLugaresServicio.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.lugaresServicio.Cajero;
import sistema_parque.lugaresServicio.LugarServicio;
import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CajeroTest {

    private Cajero cajero;
    private LugarServicio lugar;
    private Empleado empleado;
    private Cliente cliente;
    private Tiquete tiquete;

    // Clase concreta para LugarServicio (porque LugarServicio es abstracta)
    static class PuestoComida extends LugarServicio {
        public PuestoComida(String nombre) {
            super(nombre);
        }
    }

    @BeforeEach
    void setUp() {
        lugar = new PuestoComida("Comidas Rápidas");
        cajero = new Cajero("CAJA-01", lugar);

        ArrayList<String> capacitaciones = new ArrayList<>();
        capacitaciones.add("Manejo de caja");
        empleado = new Empleado("Juan Pérez", "juan123", "1234", "Cajero", capacitaciones, true, false);
        Categoria nivel = Categoria.Familiar;
        cliente = new Cliente("Laura Gómez", "laura456", "abcd");
        tiquete = new Tiquete(nivel, false, "TKT-001");
    }

    @Test
    void constructorValidoDebeCrearCajero() {
        assertEquals("CAJA-01", cajero.getIdCaja());
        assertEquals(lugar, cajero.getLugarAsociado());
        assertNull(cajero.getEmpleadoOperando());
    }

    @Test
    void constructorConIdVacioDebeLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Cajero("   ", lugar);
        });
    }

    @Test
    void asignarEmpleadoDebeActualizarEmpleadoOperando() {
        cajero.asignarEmpleado(empleado);
        assertEquals(empleado, cajero.getEmpleadoOperando());
    }

    @Test
    void desasignarEmpleadoDebeEstablecerEmpleadoOperandoComoNull() {
        cajero.asignarEmpleado(empleado);
        cajero.desasignarEmpleado();
        assertNull(cajero.getEmpleadoOperando());
    }

    @Test
    void registrarVentaTiqueteExitosamente() {
        cajero.asignarEmpleado(empleado);
        boolean resultado = cajero.registrarVentaTiquete(tiquete, cliente);
        assertTrue(resultado);
    }

    @Test
    void registrarVentaTiqueteFallaSinEmpleado() {
        boolean resultado = cajero.registrarVentaTiquete(tiquete, cliente);
        assertFalse(resultado);
    }

    @Test
    void registrarVentaTiqueteFallaConTiqueteNulo() {
        cajero.asignarEmpleado(empleado);
        boolean resultado = cajero.registrarVentaTiquete(null, cliente);
        assertFalse(resultado);
    }

    @Test
    void registrarVentaItemExitosamente() {
        cajero.asignarEmpleado(empleado);
        boolean resultado = cajero.registrarVentaItem("Hamburguesa", 3, cliente);
        assertTrue(resultado);
    }

    @Test
    void registrarVentaItemFallaSinEmpleado() {
        boolean resultado = cajero.registrarVentaItem("Hamburguesa", 3, cliente);
        assertFalse(resultado);
    }
}

