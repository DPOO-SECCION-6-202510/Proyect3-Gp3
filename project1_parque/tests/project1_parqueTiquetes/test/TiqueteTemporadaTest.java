package project1_parqueTiquetes.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.TiqueteTemporada;

public class TiqueteTemporadaTest {

    private TiqueteTemporada tiqueteTemporada;

    @BeforeEach
    void setUp() throws Exception {
        tiqueteTemporada = new TiqueteTemporada(Categoria.Oro, false, "Mensual", "TT001", null);
    }

    @AfterEach
    void tearDown() throws Exception {
        tiqueteTemporada = null;
    }

    @Test
    void testConstructorConParametros() {
        assertEquals(Categoria.Oro, tiqueteTemporada.getNivel());
        assertFalse(tiqueteTemporada.isFueUsado());
        assertEquals("Mensual", tiqueteTemporada.getTipoTemporadaLapso());
        assertEquals("TT001", tiqueteTemporada.getId());
        assertNotNull(tiqueteTemporada.getFechaExpiracion());
    }

    @Test
    void testConstructorSinParametros() {
        TiqueteTemporada tiqueteTemporadaSinParametros = new TiqueteTemporada();
        assertNull(tiqueteTemporadaSinParametros.getNivel());
        assertFalse(tiqueteTemporadaSinParametros.isFueUsado());
        assertNull(tiqueteTemporadaSinParametros.getTipoTemporadaLapso());
        assertNotNull(tiqueteTemporadaSinParametros.getId());
        assertNotNull(tiqueteTemporadaSinParametros.getFechaExpiracion());
    }

    @Test
    void testGetTipoTemporadaLapso() {
        assertEquals("Mensual", tiqueteTemporada.getTipoTemporadaLapso());
    }
}