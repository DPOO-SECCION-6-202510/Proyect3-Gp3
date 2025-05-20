package project1_parqueUsuarios.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.usuarios.TipoTurno;

public class TipoTurnoTest {

    private TipoTurno tipoTurno1;
    private TipoTurno tipoTurno2;

    @BeforeEach
    void setUp() throws Exception {
        tipoTurno1 = new TipoTurno("AP", "Apertura");
        tipoTurno2 = new TipoTurno("CI", "Cierre");
    }

    @AfterEach
    void tearDown() throws Exception {
        tipoTurno1 = null;
        tipoTurno2 = null;
    }

    @Test
    void testConstructorConArgumentos() {
        TipoTurno tipoTurno = new TipoTurno("SV", "Servicio General");
        assertEquals("SV", tipoTurno.getCodigo());
        assertEquals("Servicio General", tipoTurno.getDescripcion());
    }

    @Test
    void testConstructorSinArgumentos() {
        TipoTurno tipoTurno = new TipoTurno();
        assertNull(tipoTurno.getCodigo());
        assertNull(tipoTurno.getDescripcion());
    }

    @Test
    void testGetCodigo() {
        assertEquals("AP", tipoTurno1.getCodigo());
        assertEquals("CI", tipoTurno2.getCodigo());
    }

    @Test
    void testGetDescripcion() {
        assertEquals("Apertura", tipoTurno1.getDescripcion());
        assertEquals("Cierre", tipoTurno2.getDescripcion());
    }

    @Test
    void testEquals() {
        TipoTurno turnoApertura1 = new TipoTurno("AP", "Apertura");
        TipoTurno turnoApertura2 = new TipoTurno("AP", "Apertura");
        TipoTurno turnoCierre = new TipoTurno("CI", "Cierre");

        // Reflexividad
        assertEquals(turnoApertura1, turnoApertura1);

        // Simetría
        assertEquals(turnoApertura1, turnoApertura2);
        assertEquals(turnoApertura2, turnoApertura1);

        // Transitivity
        TipoTurno turnoApertura3 = new TipoTurno("AP", "Apertura");
        assertEquals(turnoApertura1, turnoApertura2);
        assertEquals(turnoApertura2, turnoApertura3);
        assertEquals(turnoApertura1, turnoApertura3);

        // Consistency
        for (int i = 0; i < 10; i++) {
            assertEquals(turnoApertura1, turnoApertura2);
        }

        // Comparison with null
        assertNotEquals(turnoApertura1, null);

        // Diferentes objetos
        assertNotEquals(turnoApertura1, turnoCierre);
    }

    @Test
    void testHashCode() {
        TipoTurno turnoApertura1 = new TipoTurno("AP", "Apertura");
        TipoTurno turnoApertura2 = new TipoTurno("AP", "Apertura");
        TipoTurno turnoCierre = new TipoTurno("CI", "Cierre");

        // Consistency
        int hashCode1_1 = turnoApertura1.hashCode();
        int hashCode1_2 = turnoApertura1.hashCode();
        assertEquals(hashCode1_1, hashCode1_2);

        // Equals objects have the same hashCode
        assertEquals(turnoApertura1.hashCode(), turnoApertura2.hashCode());

        // Different objects have different hashCode (usually, not always guaranteed)
        assertNotEquals(turnoApertura1.hashCode(), turnoCierre.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Apertura", tipoTurno1.toString());
        assertEquals("Cierre", tipoTurno2.toString());
    }
}