package project1_parqueTiquetes.test;
            
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.FastPass;
            
public class FastPassTest {

    private FastPass fastPass;
    private Date fechaPrueba;

    @BeforeEach
    void setUp() throws Exception {
        fechaPrueba = new Date();
        fastPass = new FastPass(Categoria.Familiar, false, fechaPrueba, "FP001");
    }

    @AfterEach
    void tearDown() throws Exception {
        fastPass = null;
        fechaPrueba = null;
    }

    @Test
    void testConstructorConParametros() {
        assertEquals(Categoria.Familiar, fastPass.getNivel());
        assertFalse(fastPass.isFueUsado());
        assertEquals(fechaPrueba, fastPass.getFecha());
        assertEquals("FP001", fastPass.getId());
        assertNotNull(fastPass.getFechaExpiracion());
    }

    @Test
    void testConstructorSinParametros() {
        FastPass fastPassSinParametros = new FastPass();
        assertNull(fastPassSinParametros.getNivel());
        assertFalse(fastPassSinParametros.isFueUsado());
        assertNull(fastPassSinParametros.getFecha());
        assertNotNull(fastPassSinParametros.getId());
        assertNotNull(fastPassSinParametros.getFechaExpiracion());
    }

    @Test
    void testGetFecha() {
        assertEquals(fechaPrueba, fastPass.getFecha());
    }
}