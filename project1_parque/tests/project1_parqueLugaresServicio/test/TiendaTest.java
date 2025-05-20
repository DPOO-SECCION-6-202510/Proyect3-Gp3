package project1_parqueLugaresServicio.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import sistema_parque.lugaresServicio.Tienda;
import sistema_parque.lugaresServicio.Cajero;
import sistema_parque.lugaresServicio.LugarServicio;
import sistema_parque.usuarios.Empleado;

import java.util.ArrayList;
import java.util.List;

public class TiendaTest {

    private Tienda tienda;
    private ArrayList<Empleado> empleados;
    private Cajero cajero;
    private LugarServicio lugarServicio;

    @BeforeEach
    void setUp() {
        lugarServicio = new LugarServicio("Área de Comidas") {};
        
        // Crear empleados para la tienda
        empleados = new ArrayList<>();
        empleados.add(new Empleado("Juan", "juan_emp", "pass123", "Vendedor", 
                                 new ArrayList<>(List.of("Ventas")), true, false));
        empleados.add(new Empleado("Ana", "ana_emp", "pass456", "Gerente", 
                                 new ArrayList<>(List.of("Gestión")), false, true));
        
        cajero = new Cajero("CAJ-001", lugarServicio);
        
        tienda = new Tienda("Tienda Principal", empleados, cajero);
    }

    @Test
    @DisplayName("Constructor con parámetros inicializa correctamente")
    void testConstructorConParametros() {
        assertEquals("Tienda Principal", tienda.getNombre());
        assertFalse(tienda.isAbierto());
        assertEquals(2, tienda.getEmpleados().size());
        assertTrue(tienda.getEmpleados().containsAll(empleados));
    }

    @Test
    @DisplayName("Constructor por defecto inicializa correctamente")
    void testConstructorPorDefecto() {
        Tienda tiendaDefault = new Tienda();
        assertNull(tiendaDefault.getNombre());
        assertFalse(tiendaDefault.isAbierto());
        assertTrue(tiendaDefault.getEmpleados().isEmpty());
    }


    @Test
    @DisplayName("Constructor con lista de empleados nula")
    void testConstructorConEmpleadosNulos() {
        Tienda tiendaConNulos = new Tienda("Tienda Secundaria", null, cajero);
        assertNull(tiendaConNulos.getEmpleados());
    }

    @Test
    @DisplayName("Hereda correctamente de LugarServicio")
    void testHerenciaLugarServicio() {
        // Probar métodos heredados
        tienda.abrir();
        assertTrue(tienda.isAbierto());
        
        tienda.cerrar();
        assertFalse(tienda.isAbierto());
    }

    @Test
    @DisplayName("Empleados mantienen sus propiedades")
    void testPropiedadesEmpleados() {
        Empleado primerEmpleado = tienda.getEmpleados().get(0);
        assertEquals("Vendedor", primerEmpleado.getRol());
        assertTrue(primerEmpleado.isTurnoDiurno());
        assertFalse(primerEmpleado.isTurnoNocturno());
        assertEquals(1, Empleado.getDESCUENTOCOMPRA());
        assertTrue(primerEmpleado.getCapacitaciones().contains("Ventas"));
    }

    @Test
    @DisplayName("Cajero asociado se crea correctamente")
    void testCajeroAsociado() {
        // Verificar que el cajero se creó correctamente
        assertEquals("CAJ-001", cajero.getIdCaja());
        assertEquals(lugarServicio, cajero.getLugarAsociado());
        assertNull(cajero.getEmpleadoOperando());
    }
}