package project1_parqueAtracciones.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.atracciones.NivelesRiesgo;

public class AtraccionMecanicaTest {

    private AtraccionMecanica atraccionMecanica1;
    private AtraccionMecanica atraccionMecanica2;
    private ArrayList<String> restriccionesClima1;
    private ArrayList<String> restriccionesSalud1;
    private ArrayList<String> restriccionesClima2;
    private ArrayList<String> restriccionesSalud2;

    @BeforeEach
    void setUp() throws Exception {
        restriccionesClima1 = new ArrayList<>();
        restriccionesClima1.add("Lluvia");
        restriccionesSalud1 = new ArrayList<>();
        restriccionesSalud1.add("Vértigo");

        atraccionMecanica1 = new AtraccionMecanica(
                "Zona 1", "Montaña Rusa", 20, 2, "Familiar", true, restriccionesClima1,
                NivelesRiesgo.ALTO,"verano", 150, 100, 40, 120, restriccionesSalud1);

        restriccionesClima2 = new ArrayList<>();
        restriccionesClima2.add("Viento");
        restriccionesSalud2 = new ArrayList<>();
        restriccionesSalud2.add("Problemas Cardíacos");

        atraccionMecanica2 = new AtraccionMecanica(
                "Zona 2", "Barco Pirata", 15, 1, "Oro", false, restriccionesClima2,
                NivelesRiesgo.MEDIO,"verano", 140, 90, 30, 110, restriccionesSalud2);
    }

    @AfterEach
    void tearDown() throws Exception {
        atraccionMecanica1 = null;
        atraccionMecanica2 = null;
        restriccionesClima1 = null;
        restriccionesSalud1 = null;
        restriccionesClima2 = null;
        restriccionesSalud2 = null;
    }

    @Test
    void testConstructorConArgumentos() {
        assertEquals("Zona 1", atraccionMecanica1.getUbicacion());
        assertEquals("Montaña Rusa", atraccionMecanica1.getNombre());
        assertEquals(20, atraccionMecanica1.getCupoMaximo());
        assertEquals(2, atraccionMecanica1.getEmpleadosMinimos());
        assertEquals("Familiar", atraccionMecanica1.getClasificacionCategoria());
        assertTrue(atraccionMecanica1.isDeTemporada());
        assertEquals(restriccionesClima1, atraccionMecanica1.getRestriccionesClima());
        assertEquals(NivelesRiesgo.ALTO, atraccionMecanica1.getNivelRiesgo());
        assertEquals(150, atraccionMecanica1.getAlturaMaxima());
        assertEquals(100, atraccionMecanica1.getAlturaMinima());
        assertEquals(40, atraccionMecanica1.getPesoMinimo());
        assertEquals(120, atraccionMecanica1.getPesoMaximo());
        assertEquals(restriccionesSalud1, atraccionMecanica1.getRestriccionesSalud());
    }

    @Test
    void testConstructorSinArgumentos() {
        AtraccionMecanica atraccionMecanica = new AtraccionMecanica();
        assertNull(atraccionMecanica.getUbicacion());
        assertNull(atraccionMecanica.getNombre());
        assertEquals(0, atraccionMecanica.getCupoMaximo());
        assertEquals(0, atraccionMecanica.getEmpleadosMinimos());
        assertNull(atraccionMecanica.getClasificacionCategoria());
        assertFalse(atraccionMecanica.isDeTemporada());
        assertNotNull(atraccionMecanica.getRestriccionesClima());
        assertNull(atraccionMecanica.getNivelRiesgo());
        assertEquals(0, atraccionMecanica.getAlturaMaxima());
        assertEquals(0, atraccionMecanica.getAlturaMinima());
        assertEquals(0, atraccionMecanica.getPesoMinimo());
        assertEquals(0, atraccionMecanica.getPesoMaximo());
        assertNotNull(atraccionMecanica.getRestriccionesSalud());
    }

    @Test
    void testGetAlturaMinima() {
        assertEquals(100, atraccionMecanica1.getAlturaMinima());
        assertEquals(90, atraccionMecanica2.getAlturaMinima());
    }

    @Test
    void testGetAlturaMaxima() {
        assertEquals(150, atraccionMecanica1.getAlturaMaxima());
        assertEquals(140, atraccionMecanica2.getAlturaMaxima());
    }

    @Test
    void testGetPesoMinimo() {
        assertEquals(40, atraccionMecanica1.getPesoMinimo());
        assertEquals(30, atraccionMecanica2.getPesoMinimo());
    }

    @Test
    void testGetPesoMaximo() {
        assertEquals(120, atraccionMecanica1.getPesoMaximo());
        assertEquals(110, atraccionMecanica2.getPesoMaximo());
    }

    @Test
    void testGetRestriccionesSalud() {
        assertEquals(restriccionesSalud1, atraccionMecanica1.getRestriccionesSalud());
        assertEquals(restriccionesSalud2, atraccionMecanica2.getRestriccionesSalud());
    }

    @Test
    void testEstaAbierta() {
        // Llama a los métodos abrir() y cerrar() en la instancia de AtraccionMecanica
        atraccionMecanica1.abrir();
        assertTrue(atraccionMecanica1.estaAbierta("cualquier fecha"));
        atraccionMecanica1.cerrar();
        assertFalse(atraccionMecanica1.estaAbierta("cualquier fecha"));
    }
}