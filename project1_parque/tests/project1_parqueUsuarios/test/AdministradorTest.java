package project1_parqueUsuarios.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sistema_parque.usuarios.Administrador;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Usuario;
import sistema_parque.usuarios.Empleado;
import sistema_parque.usuarios.Turno;
import sistema_parque.usuarios.TipoTurno;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.lugaresServicio.LugarServicio;
import sistema_parque.lugaresServicio.Cafeteria;
import java.util.Date;

/**
 * Clase de prueba para Administrador
 */
public class AdministradorTest {

    private Administrador administrador;
    private ByteArrayOutputStream salida;
    private PrintStream salidaOriginal;

    // Clases Mock para las pruebas
    private static class MockAtraccion extends Atraccion {
        public MockAtraccion(String nombre) {
            super();
            setNombre(nombre);
        }

		private void setNombre(String nombre) {
			// TODO Auto-generated method stub
			
		}
    }
    
    private static class MockLugarServicio extends LugarServicio {
        public MockLugarServicio(String nombre) {
            super();
            setNombre(nombre);
        }

		private void setNombre(String nombre) {
			// TODO Auto-generated method stub
			
		}
    }

    @BeforeEach
    void setUp() throws Exception {
        // Inicializar el administrador para cada prueba
        administrador = new Administrador("Admin Test", "admin", "password123");
        
        // Configurar la captura de la salida estándar para verificar mensajes de consola
        salidaOriginal = System.out;
        salida = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salida));
    }

    @AfterEach
    void tearDown() throws Exception {
        // Restaurar la salida estándar original
        System.setOut(salidaOriginal);
    }

    @Test
    @DisplayName("Test constructor con parámetros")
    void testConstructorConParametros() {
        // Prueba el constructor con parámetros
        Administrador admin = new Administrador("Nombre Test", "login_test", "pass_test");
        
        // Verificar que los valores se hayan asignado correctamente
        assertEquals("Nombre Test", admin.getNombre());
        assertEquals("login_test", admin.getLogin());
        assertEquals("Administrador", admin.getRol());
        assertNotNull(admin.getCapacitaciones());
        assertTrue(admin.getCapacitaciones().isEmpty());
        // No verificamos los turnos porque esos métodos no existen en la clase Administrador
    }

    @Test
    @DisplayName("Test constructor sin parámetros")
    void testConstructorSinParametros() {
        // Prueba el constructor sin parámetros
        Administrador admin = new Administrador();
        
        // Verificar que el rol se haya asignado correctamente
        assertEquals("Administrador", admin.getRol());
    }

    @Test
    @DisplayName("Test cambiar información de empleado")
    void testCambiarInfoEmpleado() {
        // Crear un empleado para modificar
        Empleado empleado = new Empleado("Empleado Test", "emp_test", "pass_emp", 
                                         "Operador", new ArrayList<>(), false, false);
        
        // Nuevos valores
        List<String> nuevasCapacitaciones = Arrays.asList("Capacitación 1", "Capacitación 2");
        boolean nuevoTurnoDiurno = true;
        boolean nuevoTurnoNocturno = false;
        
        // Ejecutar el método
        boolean resultado = administrador.cambiarInfoEmpleado(empleado, "Supervisor", 
                                                           nuevasCapacitaciones, 
                                                           nuevoTurnoDiurno, 
                                                           nuevoTurnoNocturno);
        
        // Verificar que el método retorne true indicando éxito
        assertTrue(resultado);
        
        // Verificar que se imprimió el mensaje de éxito
        String mensajeSalida = salida.toString();
        assertTrue(mensajeSalida.contains("Información de empleado " + empleado.getLogin() + " actualizada"));
    }

    @Test
    @DisplayName("Test cambiar información de empleado con argumento nulo")
    void testCambiarInfoEmpleadoArgumentoNulo() {
        // Verificar que se lance NullPointerException al pasar un empleado nulo
        assertThrows(NullPointerException.class, () -> {
            administrador.cambiarInfoEmpleado(null, "Supervisor", new ArrayList<>(), true, false);
        });
    }

    @Test
    @DisplayName("Test asignar turno a empleado")
    void testAsignarTurno() {
        // Crear datos de prueba
        Empleado empleado = new Empleado("Empleado Test", "emp_test", "pass_emp", 
                                        "Operador", new ArrayList<>(), false, false);
        Atraccion atraccion = new MockAtraccion("Montaña Rusa");
        LocalDate fecha = LocalDate.now();
        
        // Crear un TipoTurno manualmente ya que es un enum en tu código
        TipoTurno tipoTurno = new TipoTurno("DIURNO", "Turno Diurno");
        
        Turno turno = new Turno(empleado, atraccion, fecha, tipoTurno);
        List<Turno> listaTurnos = new ArrayList<>();
        
        // Ejecutar el método
        boolean resultado = administrador.asignarTurno(empleado, atraccion, turno, listaTurnos);
        
        // Verificar resultado
        assertTrue(resultado);
        assertEquals(1, listaTurnos.size());
        assertEquals(turno, listaTurnos.get(0));
        
        // Verificar mensaje en consola
        String mensajeSalida = salida.toString();
        assertTrue(mensajeSalida.contains("Asignando a " + empleado.getLogin()));
        assertTrue(mensajeSalida.contains("Asignación registrada"));
    }

    @Test
    @DisplayName("Test asignar turno con lugar de servicio")
    void testAsignarTurnoLugarServicio() {
        // Crear datos de prueba
        Empleado empleado = new Empleado("Empleado Test", "emp_test", "pass_emp", 
                                        "Operador", new ArrayList<>(), false, false);
        LugarServicio lugar = new MockLugarServicio("Restaurante");
        LocalDate fecha = LocalDate.now();
        
        // Crear un TipoTurno manualmente ya que es un enum en tu código
        TipoTurno tipoTurno = new TipoTurno("NOCTURNO", "Turno Nocturno");
        
        Turno turno = new Turno(empleado, lugar, fecha, tipoTurno);
        List<Turno> listaTurnos = new ArrayList<>();
        
        // Ejecutar el método
        boolean resultado = administrador.asignarTurno(empleado, lugar, turno, listaTurnos);
        
        // Verificar resultado
        assertTrue(resultado);
        assertEquals(1, listaTurnos.size());
    }

    @Test
    @DisplayName("Test asignar turno con lugar inválido")
    void testAsignarTurnoLugarInvalido() {
        // Crear datos de prueba
        Empleado empleado = new Empleado("Empleado Test", "emp_test", "pass_emp", 
                                        "Operador", new ArrayList<>(), false, false);
        Object lugarInvalido = new Object(); // Ni Atraccion ni LugarServicio
        LocalDate fecha = LocalDate.now();
        
        // Crear un TipoTurno
        TipoTurno tipoTurno = new TipoTurno("DIURNO", "Turno Diurno");
        
        // No creamos un Turno con objeto inválido porque lanzaría excepción en el constructor
        List<Turno> listaTurnos = new ArrayList<>();
        
        // Ejecutar el método
        boolean resultado = administrador.asignarTurno(empleado, lugarInvalido, null, listaTurnos);
        
        // Verificar resultado
        assertFalse(resultado);
        assertTrue(listaTurnos.isEmpty());
    }

    @Test
    @DisplayName("Test asignar turno con argumento nulo")
    void testAsignarTurnoArgumentoNulo() {
        // Crear algunos datos válidos
        Empleado empleado = new Empleado("Empleado Test", "emp_test", "pass_emp", 
                                        "Operador", new ArrayList<>(), false, false);
        Atraccion atraccion = new MockAtraccion("Atracción Test");
        LocalDate fecha = LocalDate.now();
        
        // Crear un TipoTurno
        TipoTurno tipoTurno = new TipoTurno("DIURNO", "Turno Diurno");
        
        Turno turno = new Turno(empleado, atraccion, fecha, tipoTurno);
        List<Turno> listaTurnos = new ArrayList<>();
        
        // Probar con empleado nulo
        assertThrows(NullPointerException.class, () -> {
            administrador.asignarTurno(null, atraccion, turno, listaTurnos);
        });
        
        // Probar con lugar nulo
        assertThrows(NullPointerException.class, () -> {
            administrador.asignarTurno(empleado, null, turno, listaTurnos);
        });
        
        // Probar con turno nulo
        assertThrows(NullPointerException.class, () -> {
            administrador.asignarTurno(empleado, atraccion, null, listaTurnos);
        });
        
        // Probar con lista de turnos nula
        assertThrows(NullPointerException.class, () -> {
            administrador.asignarTurno(empleado, atraccion, turno, null);
        });
    }

    @Test
    @DisplayName("Test agregar atracción")
    void testAgregarAtraccion() {
        // Crear datos de prueba
        Atraccion atraccion = new MockAtraccion("Nueva Atracción");
        List<Atraccion> listaAtracciones = new ArrayList<>();
        
        // Ejecutar el método
        boolean resultado = administrador.agregarAtraccion(atraccion, listaAtracciones);
        
        // Verificar resultado
        assertTrue(resultado);
        assertEquals(1, listaAtracciones.size());
        assertEquals(atraccion, listaAtracciones.get(0));
        
        // Verificar mensaje en consola
        String mensajeSalida = salida.toString();
        assertTrue(mensajeSalida.contains("Atracción agregada: " + atraccion.getNombre()));
    }

    @Test
    @DisplayName("Test agregar atracción duplicada")
    void testAgregarAtraccionDuplicada() {
        // Crear datos de prueba
        Atraccion atraccion1 = new MockAtraccion("Atracción Test");
        Atraccion atraccion2 = new MockAtraccion("atracción test"); // Mismo nombre pero diferente capitalización
        List<Atraccion> listaAtracciones = new ArrayList<>();
        
        // Agregar la primera atracción
        administrador.agregarAtraccion(atraccion1, listaAtracciones);
        
        // Intentar agregar la segunda atracción con el mismo nombre
        boolean resultado = administrador.agregarAtraccion(atraccion2, listaAtracciones);
        
        // Verificar resultado
        assertFalse(resultado);
        assertEquals(1, listaAtracciones.size()); // Solo debe haber una atracción
    }

    @Test
    @DisplayName("Test agregar atracción con argumento nulo")
    void testAgregarAtraccionArgumentoNulo() {
        List<Atraccion> listaAtracciones = new ArrayList<>();
        
        // Verificar que se lance NullPointerException al pasar una atracción nula
        assertThrows(NullPointerException.class, () -> {
            administrador.agregarAtraccion(null, listaAtracciones);
        });
        
        // Verificar que se lance NullPointerException al pasar una lista nula
        Atraccion atraccion = new MockAtraccion("Test");
        assertThrows(NullPointerException.class, () -> {
            administrador.agregarAtraccion(atraccion, null);
        });
    }

    @Test
    @DisplayName("Test modificar atracción")
    void testModificarAtraccion() {
        // Crear datos de prueba
        Atraccion atraccion = new MockAtraccion("Atracción a Modificar");
        
        // Ejecutar el método
        boolean resultado = administrador.modificarAtraccion(atraccion);
        
        // Verificar resultado
        assertTrue(resultado);
        
        // Verificar mensaje en consola
        String mensajeSalida = salida.toString();
        assertTrue(mensajeSalida.contains("Intentando modificar atracción: " + atraccion.getNombre()));
        assertTrue(mensajeSalida.contains("Información de atracción " + atraccion.getNombre() + " actualizada"));
    }

    @Test
    @DisplayName("Test modificar atracción con argumento nulo")
    void testModificarAtraccionArgumentoNulo() {
        // Verificar que se lance NullPointerException al pasar una atracción nula
        assertThrows(NullPointerException.class, () -> {
            administrador.modificarAtraccion(null);
        });
    }

    @Test
    @DisplayName("Test toString")
    void testToString() {
        // Crear un administrador con valores conocidos
        Administrador admin = new Administrador("Admin Prueba", "admin_login", "pass123");
        
        // Verificar que toString contiene los elementos esperados
        String resultado = admin.toString();
        assertTrue(resultado.contains("Admin Prueba"));
        assertTrue(resultado.contains("admin_login"));
        assertTrue(resultado.contains("Administrador"));
    }
}