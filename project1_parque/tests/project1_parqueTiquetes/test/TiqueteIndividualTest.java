package project1_parqueTiquetes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.atracciones.NivelesRiesgo;
import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.TiqueteIndividual;

public class TiqueteIndividualTest {

    private TiqueteIndividual tiqueteIndividual;
    private AtraccionMecanica atraccionMecanicaPrueba;

    @BeforeEach
    void setUp() throws Exception {
        ArrayList<String> restriccionesClima = new ArrayList<>();
        restriccionesClima.add("Lluvia fuerte");
        ArrayList<String> restriccionesSalud = new ArrayList<>();
        restriccionesSalud.add("Problemas cardíacos");

        atraccionMecanicaPrueba = new AtraccionMecanica(
                "Zona A",
                "Montaña Rusa",
                20,
                2,
                "Familiar",
                true,
                restriccionesClima,
                NivelesRiesgo.ALTO,
                "verano",
                180,
                120,
                50,
                120,
                restriccionesSalud
        );
        tiqueteIndividual = new TiqueteIndividual(Categoria.Familiar, false, atraccionMecanicaPrueba, "TI001", null);
    }

    @AfterEach
    void tearDown() throws Exception {
        tiqueteIndividual = null;
        atraccionMecanicaPrueba = null;
    }

    @Test
    void testConstructorConParametros() {
        assertEquals(Categoria.Familiar, tiqueteIndividual.getNivel());
        assertFalse(tiqueteIndividual.isFueUsado());
        assertEquals(atraccionMecanicaPrueba, tiqueteIndividual.getAtraccion());
        assertEquals("TI001", tiqueteIndividual.getId());
        assertNotNull(tiqueteIndividual.getFechaExpiracion());
    }

    @Test
    void testConstructorSinParametros() {
        TiqueteIndividual tiqueteSinParametros = new TiqueteIndividual();
        assertNull(tiqueteSinParametros.getNivel());
        assertFalse(tiqueteSinParametros.isFueUsado());
        assertNull(tiqueteSinParametros.getAtraccion());
        assertNotNull(tiqueteSinParametros.getId());
        assertNotNull(tiqueteSinParametros.getFechaExpiracion());
    }

    @Test
    void testToStringConAtraccionAsignada() {
        String expected = "TiqueteIndividual{id='TI001', nivel=Familiar, fueUsado=false, atraccion='Montaña Rusa', fechaExpiracion=" + tiqueteIndividual.getFechaExpiracion() + '}';
        assertEquals(expected, tiqueteIndividual.toString());
    }

    @Test
    void testToStringSinAtraccionAsignada() {
        TiqueteIndividual tiqueteSinAtraccion = new TiqueteIndividual(Categoria.Oro, true, null, "TI002", null);
        String expected = "TiqueteIndividual{id='TI002', nivel=Oro, fueUsado=true, atraccion='Ninguna', fechaExpiracion=" + tiqueteSinAtraccion.getFechaExpiracion() + '}';
        assertEquals(expected, tiqueteSinAtraccion.toString());
    }

    @Test
    void testSetAtraccion() {
        ArrayList<String> nuevasRestriccionesClima = new ArrayList<>();
        nuevasRestriccionesClima.add("Viento fuerte");
        ArrayList<String> nuevasRestriccionesSalud = new ArrayList<>();
        nuevasRestriccionesSalud.add("Mareos");

        AtraccionMecanica nuevaAtraccion = new AtraccionMecanica(
                "Zona B",
                "Carrusel",
                15,
                1,
                "Infantil",
                false,
                nuevasRestriccionesClima,
                NivelesRiesgo.BAJO,
                "verano",
                150,
                100,
                30,
                100,
                nuevasRestriccionesSalud
        );
        tiqueteIndividual.setAtraccion(nuevaAtraccion);
        assertEquals(nuevaAtraccion, tiqueteIndividual.getAtraccion());
    }

    @Test
    void testAtraccionEstaAbierta() {
        // Asumiendo que Atraccion.estaCerrada() devuelve false por defecto para esta prueba
        assertTrue(atraccionMecanicaPrueba.estaAbierta("alguna fecha"));
        // Si Atraccion.estaCerrada() tiene una lógica específica, podrías necesitar simularla
        // o probarla en una clase de prueba separada para AtraccionMecanica.
    }
}