package project1_parqueAtracciones.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.atracciones.Atraccion; // Import Atraccion
import sistema_parque.atracciones.Espectaculo;
import sistema_parque.atracciones.NivelesRiesgo;

public class EspectaculoTest {

    private Espectaculo espectaculo1;
    private Espectaculo espectaculo2;
    private Date fechaPrueba;
    private ArrayList<String> restriccionesClima1;
    private ArrayList<String> restriccionesClima2;

    @BeforeEach
    void setUp() throws Exception {
        restriccionesClima1 = new ArrayList<>();
        restriccionesClima1.add("Lluvia");
        fechaPrueba = new Date();

        espectaculo1 = new Espectaculo(
                "Teatro Principal", "El Lago de los Cisnes", 100, 5, "Familiar", true, restriccionesClima1,
                NivelesRiesgo.BAJO, "verano");
        espectaculo1.setHorario(20);
        espectaculo1.setFecha(fechaPrueba);
        espectaculo1.setEdadIngreso(0);

        restriccionesClima2 = new ArrayList<>();
        restriccionesClima2.add("Viento Fuerte");

        espectaculo2 = new Espectaculo(
                "Anfiteatro", "Aves Exóticas en Vuelo", 50, 3, "Todo Público", false, restriccionesClima2,
                NivelesRiesgo.BAJO, "verano");
        espectaculo2.setHorario(15);
        espectaculo2.setFecha(new Date(fechaPrueba.getTime() + 86400000)); // Fecha diferente
        espectaculo2.setEdadIngreso(5);
    }

    @AfterEach
    void tearDown() throws Exception {
        espectaculo1 = null;
        espectaculo2 = null;
        fechaPrueba = null;
        restriccionesClima1 = null;
        restriccionesClima2 = null;
    }

    @Test
    void testConstructorConArgumentos() {
        assertEquals("Teatro Principal", espectaculo1.getUbicacion());
        assertEquals("El Lago de los Cisnes", espectaculo1.getNombre());
        assertEquals(100, espectaculo1.getCupoMaximo());
        assertEquals(5, espectaculo1.getEmpleadosMinimos());
        assertEquals("Familiar", espectaculo1.getClasificacionCategoria());
        assertTrue(espectaculo1.isDeTemporada());
        assertEquals(restriccionesClima1, espectaculo1.getRestriccionesClima());
        assertEquals(NivelesRiesgo.BAJO, espectaculo1.getNivelRiesgo());
    }

    @Test
    void testConstructorSinArgumentos() {
        Espectaculo espectaculo = new Espectaculo();
        assertNull(espectaculo.getUbicacion());
        assertNull(espectaculo.getNombre());
        assertEquals(0, espectaculo.getCupoMaximo());
        assertEquals(0, espectaculo.getEmpleadosMinimos());
        assertNull(espectaculo.getClasificacionCategoria());
        assertFalse(espectaculo.isDeTemporada());
        assertNotNull(espectaculo.getRestriccionesClima());
        assertNull(espectaculo.getNivelRiesgo());
    }

    @Test
    void testGetHorario() {
        assertEquals(20, espectaculo1.getHorario());
        assertEquals(15, espectaculo2.getHorario());
    }

    @Test
    void testGetFecha() {
        assertEquals(fechaPrueba, espectaculo1.getFecha());
        assertNotEquals(fechaPrueba, espectaculo2.getFecha());
    }

    @Test
    void testGetEdadIngreso() {
        assertEquals(0, espectaculo1.getEdadIngreso());
        assertEquals(5, espectaculo2.getEdadIngreso());
    }

    @Test
    void testEstaAbierta() {
        Atraccion.abrir(); // Use the static method from Atraccion
        assertFalse(Atraccion.estaCerrada()); // Check the static state
        Atraccion.cerrar(); // Use the static method from Atraccion
        assertTrue(Atraccion.estaCerrada()); // Check the static state
        Atraccion.abrir(); // Reset the state for other tests
    }

    @Test
    void testCerrarDeTemporada() {
        assertFalse(espectaculo1.CerrarDeTemporada(espectaculo1, "cualquier fecha"));
        assertFalse(espectaculo2.CerrarDeTemporada(espectaculo2, "Verano"));
        assertFalse(Atraccion.estaCerrada());

        assertTrue(espectaculo1.CerrarDeTemporada(espectaculo1, "Otra Fecha"));
        assertTrue(Atraccion.estaCerrada());
        Atraccion.abrir(); // Reset the state for other tests
    }
}