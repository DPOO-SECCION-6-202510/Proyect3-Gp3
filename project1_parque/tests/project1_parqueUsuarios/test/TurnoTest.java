package project1_parqueUsuarios.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import sistema_parque.usuarios.Turno;
import sistema_parque.atracciones.AtraccionMecanica; // Asumo que existe y tiene getNombre()
import sistema_parque.atracciones.NivelesRiesgo;   // Asumo que existe
import sistema_parque.lugaresServicio.Cafeteria;    // Asumo que existe y tiene getNombre()
import sistema_parque.lugaresServicio.Cajero;       // Asumo que existe
import sistema_parque.lugaresServicio.Cocina;       // Asumo que existe
// No es necesario importar Atraccion y LugarServicio si no se usan directamente en los tests para 'instanceof',
// ya que Turno.java ya los importa y usa.
import sistema_parque.usuarios.Empleado;            // Asumo que existe, tiene getLogin(), equals y hashCode
import sistema_parque.usuarios.TipoTurno;           // Usando tu clase TipoTurno

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects; // Para mocks de Empleado si es necesario


public class TurnoTest {

    private Empleado empleado1;
    private Empleado empleado2;
    private AtraccionMecanica atraccion1;
    private Cafeteria lugarServicio1;
    private LocalDate fecha1;
    private LocalDate fecha2;
    private ArrayList<String> capacitaciones;
    private Cajero cajero;
    private Cocina cocina;

    // Instancias de TipoTurno
    private TipoTurno tipoApertura;
    private TipoTurno tipoCierre;
    private TipoTurno tipoOperacion; // Para otros tests

    @BeforeEach
    void setUp() {
        capacitaciones = new ArrayList<>();
        capacitaciones.add("Capacitacion_empleado");


        empleado1 = new Empleado("Diego","emp001_login", "321*", "empleado", capacitaciones, true, true);
        empleado2 = new Empleado("Hernando","emp002_login", "123*", "empleado", capacitaciones, true, true);
        cocina = new Cocina();
        cajero = new Cajero("1212", null);
        lugarServicio1 = new Cafeteria("Cafetería Central", cajero, cocina);
        if (cajero != null) {
            // cajero.setLugarServicio(lugarServicio1); // Descomenta si es necesario y el método existe.
        }
        atraccion1 = new AtraccionMecanica("Zona de Adrenalina",
                "Montaña Increíble", 40, 4, "Montaña Rusa Extrema", false,
                new ArrayList<>(Arrays.asList("Lluvia Fuerte", "Vientos Fuertes")),
                NivelesRiesgo.ALTO, "verano", 195, 140, 45, 120,
                new ArrayList<>(Arrays.asList("Problemas Cardíacos", "Problemas de Espalda", "Embarazo"))
        );
        
        fecha1 = LocalDate.of(2024, 8, 15);
        fecha2 = LocalDate.of(2024, 8, 16);

        // Inicializar instancias de TipoTurno
        tipoApertura = new TipoTurno("AP", "Apertura del Parque Test"); // Descripciones para distinguir en tests
        tipoCierre = new TipoTurno("CI", "Cierre del Parque Test");
        tipoOperacion = new TipoTurno("OP", "Operacion Regular Test");
    }

    @Nested
    @DisplayName("Pruebas para el Constructor Principal de Turno")
    class ConstructorPrincipalTests {

        @Test
        @DisplayName("Creación exitosa con Atraccion y tipoApertura")
        void testConstructorExitosoConAtraccion() {
            Turno turno = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            assertNotNull(turno, "El turno no debería ser nulo.");
            assertEquals(empleado1, turno.getEmpleadoAsignado());
            assertEquals(atraccion1, turno.getLugarAsignado());
            assertEquals(fecha1, turno.getFecha());
            assertEquals(tipoApertura, turno.getTipoTurno());
        }

        @Test
        @DisplayName("Creación exitosa con LugarServicio y tipoCierre")
        void testConstructorExitosoConLugarServicio() {
            Turno turno = new Turno(empleado1, lugarServicio1, fecha1, tipoCierre);
            assertNotNull(turno, "El turno no debería ser nulo.");
            assertEquals(empleado1, turno.getEmpleadoAsignado());
            assertEquals(lugarServicio1, turno.getLugarAsignado());
            assertEquals(fecha1, turno.getFecha());
            assertEquals(tipoCierre, turno.getTipoTurno());
        }

        @Test
        @DisplayName("Fallo constructor: Empleado nulo")
        void testConstructorEmpleadoNulo() {
            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                new Turno(null, atraccion1, fecha1, tipoApertura);
            });
            assertEquals("El empleado no puede ser nulo.", ex.getMessage());
        }

        @Test
        @DisplayName("Fallo constructor: Lugar nulo")
        void testConstructorLugarNulo() {
            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                new Turno(empleado1, null, fecha1, tipoApertura);
            });
            assertEquals("El lugar asignado no puede ser nulo.", ex.getMessage());
        }

        @Test
        @DisplayName("Fallo constructor: Fecha nula")
        void testConstructorFechaNula() {
            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                new Turno(empleado1, atraccion1, null, tipoApertura);
            });
            assertEquals("La fecha no puede ser nula.", ex.getMessage());
        }

        @Test
        @DisplayName("Fallo constructor: TipoTurno nulo")
        void testConstructorTipoTurnoNulo() {
            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                new Turno(empleado1, atraccion1, fecha1, null);
            });
            assertEquals("El tipo de turno no puede ser nulo.", ex.getMessage());
        }

        @Test
        @DisplayName("Fallo constructor: Tipo de lugar inválido")
        void testConstructorLugarInvalido() {
            Object lugarInvalido = new String("Soy un lugar inválido");
            Exception ex = assertThrows(IllegalArgumentException.class, () -> {
                new Turno(empleado1, lugarInvalido, fecha1, tipoApertura);
            });
            // El mensaje de la excepción en Turno.java es:
            assertEquals("El lugar asignado debe ser una instancia de Atraccion o LugarServicio.", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas para el Constructor por Defecto de Turno")
    class ConstructorPorDefectoTests {
        @Test
        @DisplayName("Creación con constructor por defecto")
        void testConstructorPorDefecto() {
            Turno turno = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura);
            assertNotNull(turno);
            assertNull(turno.getEmpleadoAsignado());
            assertNull(turno.getLugarAsignado());
            assertNull(turno.getFecha());
            assertNull(turno.getTipoTurno());
        }
    }

    @Nested
    @DisplayName("Pruebas para Getters de Turno") // Los setters ya están en Turno.java
    class GetterTests {
        private Turno turnoPrueba;

        @BeforeEach
        void setUpGetter() {
            // Usamos el constructor principal para tener datos que obtener
            turnoPrueba = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
        }

        @Test
        @DisplayName("Test para getEmpleadoAsignado")
        void testGetEmpleadoAsignado() {
            assertEquals(empleado1, turnoPrueba.getEmpleadoAsignado());
        }

        @Test
        @DisplayName("Test para getLugarAsignado")
        void testGetLugarAsignado() {
            assertEquals(atraccion1, turnoPrueba.getLugarAsignado());
        }

        @Test
        @DisplayName("Test para getFecha")
        void testGetFecha() {
            assertEquals(fecha1, turnoPrueba.getFecha());
        }

        @Test
        @DisplayName("Test para getTipoTurno")
        void testGetTipoTurno() {
            assertEquals(tipoApertura, turnoPrueba.getTipoTurno());
        }
    }

    @Nested
    @DisplayName("Pruebas para equals() y hashCode() de Turno")
    class EqualsAndHashCodeTests {
        // Recordatorio: La implementación de equals en Turno NO considera 'lugarAsignado'.

        @Test
        @DisplayName("equals: reflexividad")
        void testEqualsReflexividad() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            assertEquals(turno1, turno1);
        }

        @Test
        @DisplayName("equals: simetría con mismos datos relevantes")
        void testEqualsSimetriaMismosDatos() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno2 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            assertEquals(turno1, turno2);
            assertEquals(turno2, turno1);
        }

        @Test
        @DisplayName("equals: simetría con diferente lugar (no afecta equals)")
        void testEqualsSimetriaDiferenteLugar() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno3 = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura); // Diferente lugar
            assertEquals(turno1, turno3);
            assertEquals(turno3, turno1);
        }

        @Test
        @DisplayName("equals: no igualdad con diferente empleado")
        void testNotEqualsDiferenteEmpleado() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno_diff = new Turno(empleado2, atraccion1, fecha1, tipoApertura);
            assertNotEquals(turno1, turno_diff);
        }

        @Test
        @DisplayName("equals: no igualdad con diferente fecha")
        void testNotEqualsDiferenteFecha() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno_diff = new Turno(empleado1, atraccion1, fecha2, tipoApertura);
            assertNotEquals(turno1, turno_diff);
        }

        @Test
        @DisplayName("equals: no igualdad con diferente TipoTurno")
        void testNotEqualsDiferenteTipoTurno() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno_diff = new Turno(empleado1, atraccion1, fecha1, tipoCierre);
            assertNotEquals(turno1, turno_diff);
        }

        @Test
        @DisplayName("equals: comparación con nulo")
        void testEqualsConNulo() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            // assertNotEquals(null, turno1); // Esta forma es menos explícita
            assertFalse(turno1.equals(null));
        }

        @Test
        @DisplayName("equals: comparación con diferente clase")
        void testEqualsConDiferenteClase() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            // assertNotEquals("Soy un String", turno1); // Esta forma es menos explícita
            assertFalse(turno1.equals("Soy un String"));
        }

        @Test
        @DisplayName("hashCode: consistencia con equals")
        void testHashCodeConsistencia() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno2 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno3 = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura); // lugar no afecta equals

            assertEquals(turno1.hashCode(), turno2.hashCode());
            assertEquals(turno1.hashCode(), turno3.hashCode());
        }

        @Test
        @DisplayName("hashCode: diferentes para turnos distintos")
        void testHashCodeDiferente() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno2 = new Turno(empleado2, atraccion1, fecha1, tipoApertura); // dif empleado
            Turno turno3 = new Turno(empleado1, atraccion1, fecha2, tipoApertura); // dif fecha
            Turno turno4 = new Turno(empleado1, atraccion1, fecha1, tipoCierre);   // dif tipoTurno

            assertNotEquals(turno1.hashCode(), turno2.hashCode());
            assertNotEquals(turno1.hashCode(), turno3.hashCode());
            assertNotEquals(turno1.hashCode(), turno4.hashCode());
        }
    }

    @Nested
    @DisplayName("Pruebas para toString() de Turno")
    class ToStringTests {
        @Test
        @DisplayName("toString con Atraccion y tipoApertura")
        void testToStringConAtraccion() {
            Turno turno = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            // El orden en Turno.toString() es: fecha, tipo, empleado, lugar
            String esperado = "Turno{" +
                              "fecha=" + fecha1.toString() +
                              ", tipo=" + tipoApertura.getDescripcion() +  // O tipoApertura.toString()
                              ", empleado=" + empleado1.getLogin() +
                              ", lugar='" + atraccion1.getNombre() + "'" +
                              '}';
            assertEquals(esperado, turno.toString());
        }

        @Test
        @DisplayName("toString con LugarServicio y tipoCierre")
        void testToStringConLugarServicio() {
            Turno turno = new Turno(empleado1, lugarServicio1, fecha1, tipoCierre);
            String esperado = "Turno{" +
                              "fecha=" + fecha1.toString() +
                              ", tipo=" + tipoCierre.getDescripcion() + // O tipoCierre.toString()
                              ", empleado=" + empleado1.getLogin() +
                              ", lugar='" + lugarServicio1.getNombre() + "'" +
                              '}';
            assertEquals(esperado, turno.toString());
        }

        @Test
        @DisplayName("toString con constructor por defecto (campos nulos)")
        void testToStringConstructorPorDefecto() {
        	Turno turno = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura);
            String esperado = "Turno{" +
                              "fecha=Fecha Desconocida" +
                              ", tipo=Tipo Desconocido" +
                              ", empleado=Empleado Desconocido" +
                              ", lugar='Lugar Desconocido'" + // Ajustado para el caso de todo nulo
                              '}';
            assertEquals(esperado, turno.toString());
        }

        @Test
        @DisplayName("toString con empleado pero lugar nulo (caso Servicio General)")
        void testToStringLugarNuloEmpleadoNoNulo() {
        	Turno turno = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura);
            turno.setEmpleadoAsignado(empleado1); // Empleado no nulo
            turno.setFecha(fecha1);               // Fecha no nula
            turno.setTipoTurno(tipoOperacion);    // TipoTurno no nulo
            turno.setLugarAsignado(null);         // Lugar nulo

            String esperado = "Turno{" +
                              "fecha=" + fecha1.toString() +
                              ", tipo=" + tipoOperacion.getDescripcion() +
                              ", empleado=" + empleado1.getLogin() +
                              ", lugar='Servicio General'" + // Caso especial
                              '}';
            assertEquals(esperado, turno.toString());

            // Adicional: ¿Qué pasa si el empleado también es nulo con lugar nulo? (cubierto por testToStringConstructorPorDefecto)
            Turno turnoTodoNulo = new Turno(empleado1, lugarServicio1, fecha1, tipoApertura);
            
            turnoTodoNulo.setLugarAsignado(null); // empleadoAsignado ya es null
            String esperadoTodoNulo = "Turno{" +
                                      "fecha=Fecha Desconocida" +
                                      ", tipo=Tipo Desconocido" +
                                      ", empleado=Empleado Desconocido" +
                                      ", lugar='Lugar Desconocido'" +
                                      '}';
            assertEquals(esperadoTodoNulo, turnoTodoNulo.toString());
        }
    }

    @Nested
    @DisplayName("Pruebas adicionales de escenarios mixtos")
    class PruebasAdicionales {

        @Test
        @DisplayName("Turno con mismo empleado pero diferente fecha y tipo")
        void testTurnoMismoEmpleadoDiferenteFechaYTipo() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno2 = new Turno(empleado1, lugarServicio1, fecha2, tipoCierre); // lugarServicio1 para variar el lugar

            assertNotEquals(turno1, turno2); // Diferentes por fecha y tipoTurno
            assertNotEquals(turno1.hashCode(), turno2.hashCode());

            assertEquals(empleado1, turno1.getEmpleadoAsignado());
            assertEquals(empleado1, turno2.getEmpleadoAsignado());

            assertNotEquals(turno1.getFecha(), turno2.getFecha());
            assertNotEquals(turno1.getTipoTurno(), turno2.getTipoTurno());
        }

        @Test
        @DisplayName("Turnos para dos empleados diferentes el mismo día")
        void testTurnoDosEmpleadosMismoDia() {
            Turno turno1 = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turno2 = new Turno(empleado2, atraccion1, fecha1, tipoApertura); // Mismo lugar, fecha, tipo

            assertNotEquals(turno1, turno2); // Diferentes por empleado
            assertEquals(turno1.getFecha(), turno2.getFecha());
            assertEquals(turno1.getTipoTurno(), turno2.getTipoTurno());
            assertEquals(turno1.getLugarAsignado(), turno2.getLugarAsignado());
        }

        @Test
        @DisplayName("Verificar información de turnos completos")
        void testInformacionTurnosCompletos() {
            Turno turnoAperturaObj = new Turno(empleado1, atraccion1, fecha1, tipoApertura);
            Turno turnoCierreObj = new Turno(empleado2, lugarServicio1, fecha2, tipoCierre);

            assertEquals(empleado1, turnoAperturaObj.getEmpleadoAsignado());
            assertEquals(atraccion1, turnoAperturaObj.getLugarAsignado());
            assertEquals(fecha1, turnoAperturaObj.getFecha());
            assertEquals(tipoApertura, turnoAperturaObj.getTipoTurno());

            assertEquals(empleado2, turnoCierreObj.getEmpleadoAsignado());
            assertEquals(lugarServicio1, turnoCierreObj.getLugarAsignado());
            assertEquals(fecha2, turnoCierreObj.getFecha());
            assertEquals(tipoCierre, turnoCierreObj.getTipoTurno());

            assertNotEquals(turnoAperturaObj, turnoCierreObj);
        }
    }
}