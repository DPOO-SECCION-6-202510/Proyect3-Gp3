package project1_parqueTiquetes.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList; // Importa ArrayList

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.tiquetes.Tiquete;
import sistema_parque.tiquetes.Categoria;
import sistema_parque.atracciones.AtraccionMecanica; // Importa AtraccionMecanica
import sistema_parque.atracciones.NivelesRiesgo; // Importa NivelesRiesgo

public class TiqueteTest {

    private Tiquete tiquete;

    @BeforeEach
    void setUp() throws Exception {
        tiquete = new Tiquete(Categoria.Diamante, false, "1", null);
    }

    @AfterEach
    void tearDown() throws Exception {
        tiquete = null;
    }

    @Test
    void testConstructorConParametros() {
        assertEquals(Categoria.Diamante, tiquete.getNivel());
        assertFalse(tiquete.isFueUsado());
        assertEquals("1", tiquete.getId());
        assertNull(tiquete.getFechaExpiracion());
        assertNull(tiquete.getAtraccion());
    }

    @Test
    void testConstructorSinParametros() {
        Tiquete tiqueteSinParametros = new Tiquete();
        assertNull(tiqueteSinParametros.getNivel());
        assertFalse(tiqueteSinParametros.isFueUsado());
        assertNull(tiqueteSinParametros.getId());
        assertNull(tiqueteSinParametros.getFechaExpiracion());
        assertNull(tiqueteSinParametros.getAtraccion());
    }

    @Test
    void testGetNivel() {
        assertEquals(Categoria.Diamante, tiquete.getNivel());
    }

    @Test
    void testIsFueUsado() {
        assertFalse(tiquete.isFueUsado());
    }

    @Test
    void testGetId() {
        assertEquals("1", tiquete.getId());
    }

    @Test
    void testGetFechaExpiracion() {
        assertNull(tiquete.getFechaExpiracion());
    }

    @Test
    void testSetFechaExpiracion() {
        LocalDate fechaExpiracion = LocalDate.now().plusDays(7);
        tiquete.setFechaExpiracion(fechaExpiracion);
        assertEquals(fechaExpiracion, tiquete.getFechaExpiracion());
    }

    @Test
    void testGetAtraccion() {
        assertNull(tiquete.getAtraccion());
    }

    @Test
    void testSetAtraccion() {
        // Crea una instancia de AtraccionMecanica para la prueba
        ArrayList<String> restriccionesClima = new ArrayList<>();
        restriccionesClima.add("Lluvia");
        ArrayList<String> restriccionesSalud = new ArrayList<>();
        restriccionesSalud.add("Vértigo");

        AtraccionMecanica atraccion = new AtraccionMecanica(
                "Zona 1",
                "Montaña Rusa",
                20,
                2,
                "Familiar",
                true,
                restriccionesClima,
                NivelesRiesgo.ALTO,
                "verano",
                150,
                100,
                40,
                120,
                restriccionesSalud
        );
        tiquete.setAtraccion(atraccion);
        assertEquals(atraccion, tiquete.getAtraccion());
    }

    @Test
    void testEsValidoParaUsar() {
        LocalDate now = LocalDate.now();
        tiquete.setFechaExpiracion(now.plusDays(7));
        assertTrue(tiquete.esValidoParaUsar(now.plusDays(6)));
        assertFalse(tiquete.esValidoParaUsar(now.plusDays(8)));
        assertFalse(tiquete.esValidoParaUsar(now));
    }

    @Test
    void testEsValidoParaUsarSinFechaExpiracion() {
        LocalDate now = LocalDate.now();
        assertFalse(tiquete.esValidoParaUsar(now));
    }

    @Test
    void testMarcarComoUsado() {
        assertFalse(tiquete.isFueUsado());
        tiquete.marcarComoUsado();
        assertTrue(tiquete.isFueUsado());
    }
}