package project1_parqueAtracciones.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionCultural;
import sistema_parque.atracciones.NivelesRiesgo;

public class AtraccionCulturalTest {

    private AtraccionCultural atraccionCultural1;
    private AtraccionCultural atraccionCultural2;
    private ArrayList<String> restriccionesClima1;
    private ArrayList<String> restriccionesClima2;

    
    @BeforeEach
    void setUp() throws Exception {
        restriccionesClima1 = new ArrayList<>();
        restriccionesClima1.add("Lluvia");

        atraccionCultural1 = new AtraccionCultural(
                "Zona Histórica", "Museo de Cera", 50, 3, "Familiar", false, restriccionesClima1,
                NivelesRiesgo.BAJO, "Todo el año");

        restriccionesClima2 = new ArrayList<>();
        restriccionesClima2.add("Calor Extremo");

        atraccionCultural2 = new AtraccionCultural(
                "Zona Central", "Show de Aves", 30, 2, "Oro", true, restriccionesClima2,
                NivelesRiesgo.BAJO, "Verano");
    }

    @AfterEach
    void tearDown() throws Exception {
        atraccionCultural1 = null;
        atraccionCultural2 = null;
        restriccionesClima1 = null;
        restriccionesClima2 = null;
    }

    @Test
    void testConstructorConArgumentos() {
        assertEquals("Zona Histórica", atraccionCultural1.getUbicacion());
        assertEquals("Museo de Cera", atraccionCultural1.getNombre());
        assertEquals(50, atraccionCultural1.getCupoMaximo());
        assertEquals(3, atraccionCultural1.getEmpleadosMinimos());
        assertEquals("Familiar", atraccionCultural1.getClasificacionCategoria());
        assertFalse(atraccionCultural1.isDeTemporada());
        assertEquals(restriccionesClima1, atraccionCultural1.getRestriccionesClima());
        assertEquals(NivelesRiesgo.BAJO, atraccionCultural1.getNivelRiesgo());
        assertEquals("Todo el año", atraccionCultural1.getdeTemporadaDetalles());
    }

    @Test
    void testConstructorSinArgumentos() {
        AtraccionCultural atraccionCultural = new AtraccionCultural();
        assertNull(atraccionCultural.getUbicacion());
        assertNull(atraccionCultural.getNombre());
        assertEquals(0, atraccionCultural.getCupoMaximo());
        assertEquals(0, atraccionCultural.getEmpleadosMinimos());
        assertNull(atraccionCultural.getClasificacionCategoria());
        assertFalse(atraccionCultural.isDeTemporada());
        assertNotNull(atraccionCultural.getRestriccionesClima());
        assertNull(atraccionCultural.getNivelRiesgo());
        assertNull(atraccionCultural.getdeTemporadaDetalles());
    }

    @Test
    void testToString() {
        String expected1 = "AtraccionCultural{nombre='Museo de Cera', ubicacion='Zona Histórica', clasificacion='Familiar', cupoMaximo=50, estaCerrada=false, restriccionEdad=0, espacioConstruido=0}";
        String expected2 = "AtraccionCultural{nombre='Show de Aves', ubicacion='Zona Central', clasificacion='Oro', cupoMaximo=30, estaCerrada=false, restriccionEdad=0, espacioConstruido=0}";
        assertEquals(expected1, atraccionCultural1.toString());
        assertEquals(expected2, atraccionCultural2.toString());

        atraccionCultural1.cerrar();
        expected1 = "AtraccionCultural{nombre='Museo de Cera', ubicacion='Zona Histórica', clasificacion='Familiar', cupoMaximo=50, estaCerrada=true, restriccionEdad=0, espacioConstruido=0}";
        assertEquals(expected1, atraccionCultural1.toString());
        atraccionCultural1.abrir();
    }

    @Test
    void testEstaCerrada() {
        atraccionCultural1.abrir();
        assertFalse(Atraccion.estaCerrada());
        atraccionCultural1.cerrar();
        assertTrue(Atraccion.estaCerrada());
        atraccionCultural1.abrir();
    }

    @Test
    void testCerrarDeTemporada() {
        assertFalse(atraccionCultural1.CerrarDeTemporada(atraccionCultural1, "cualquier fecha"));
        assertFalse(atraccionCultural2.CerrarDeTemporada(atraccionCultural2, "Verano"));
        assertFalse(Atraccion.estaCerrada());

        assertTrue(atraccionCultural1.CerrarDeTemporada(atraccionCultural1, "Otra Fecha"));
        assertTrue(Atraccion.estaCerrada());
        atraccionCultural1.abrir();
    }
}