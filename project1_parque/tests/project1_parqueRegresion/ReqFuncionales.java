package project1_parqueRegresion;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionCultural;
import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.atracciones.Espectaculo;
import sistema_parque.atracciones.NivelesRiesgo;
import sistema_parque.tiquetes.Categoria;
import sistema_parque.tiquetes.FastPass;
import sistema_parque.tiquetes.Tiquete;
import sistema_parque.tiquetes.TiqueteIndividual;
import sistema_parque.tiquetes.TiqueteTemporada;
import sistema_parque.usuarios.Cliente;
import sistema_parque.usuarios.Empleado;

public class ReqFuncionales {

    private AtraccionMecanica atraccionMecanica;
    private AtraccionCultural atraccionCultural;
    private Espectaculo espectaculo;
    private ArrayList<String> restriccionesClima;
    private ArrayList<String> restriccionesSalud;
    private Empleado empleado;
    private Cliente cliente;

    @BeforeEach
    void setUp() throws Exception {
        // Inicializa las listas y objetos necesarios para las pruebas
        restriccionesClima = new ArrayList<>();
        restriccionesClima.add("Lluvia");
        restriccionesClima.add("Tormenta");
        
        restriccionesSalud = new ArrayList<>();
        restriccionesSalud.add("Problemas cardíacos");
        restriccionesSalud.add("Vértigo");
        
        // Abrir atracciones por defecto para las pruebas
        Atraccion.abrir();
        
        // Crear objetos de prueba para atracciones
        atraccionMecanica = new AtraccionMecanica(
            "Zona Norte", "Montaña Rusa", 50, 3,
            "Oro", true, restriccionesClima, NivelesRiesgo.ALTO, "verano",
            190, 120, 30, 100, restriccionesSalud
        );
        
        atraccionCultural = new AtraccionCultural(
            "Zona Este", "Museo Virtual", 100, 2,
            "Familiar", false, restriccionesClima, NivelesRiesgo.MEDIO,
            "No aplica"
        );
        
        espectaculo = new Espectaculo(
            "Zona Central", "Show de Luces", 200, 0,
            "Familiar", true, restriccionesClima, NivelesRiesgo.MEDIO, "verano"
        );
        
        espectaculo.setHorario(18); // 6 PM
        espectaculo.setFecha(new Date());
        espectaculo.setEdadIngreso(5);
        
        // Crear empleado para pruebas
        ArrayList<String> capacitaciones = new ArrayList<>();
        capacitaciones.add("Manejo de atracciones");
        empleado = new Empleado("Carlos", "carlos123", "pass123", "Operador", 
                              capacitaciones, true, false);
        
        // Crear cliente para pruebas de tiquetes
        cliente = new Cliente("Ana", "ana123", "anaPass");
    }

    @AfterEach
    void tearDown() throws Exception {
        // Limpiar después de cada prueba
        atraccionMecanica = null;
        atraccionCultural = null;
        espectaculo = null;
        restriccionesClima = null;
        restriccionesSalud = null;
        empleado = null;
        cliente = null;
    }
    
    // ============================================
    // PRUEBAS PARA EL REQUERIMIENTO 1: Controlar las diferentes atracciones y espectáculos
    // ============================================
    
    @Test
    void testCrearAtraccionMecanica() {
        assertEquals("Montaña Rusa", atraccionMecanica.getNombre());
        assertEquals("Zona Norte", atraccionMecanica.getUbicacion());
        assertEquals(50, atraccionMecanica.getCupoMaximo());
        assertEquals(3, atraccionMecanica.getEmpleadosMinimos());
        assertEquals("Oro", atraccionMecanica.getClasificacionCategoria());
        assertTrue(atraccionMecanica.isDeTemporada());
        assertEquals(NivelesRiesgo.ALTO, atraccionMecanica.getNivelRiesgo());
        assertEquals(120, atraccionMecanica.getAlturaMinima());
        assertEquals(190, atraccionMecanica.getAlturaMaxima());
        assertEquals(30, atraccionMecanica.getPesoMinimo());
        assertEquals(100, atraccionMecanica.getPesoMaximo());
        assertEquals(2, atraccionMecanica.getRestriccionesSalud().size());
    }
    
    @Test
    void testCrearAtraccionCultural() {
        assertEquals("Museo Virtual", atraccionCultural.getNombre());
        assertEquals("Zona Este", atraccionCultural.getUbicacion());
        assertEquals(100, atraccionCultural.getCupoMaximo());
        assertEquals(2, atraccionCultural.getEmpleadosMinimos());
        assertEquals("Familiar", atraccionCultural.getClasificacionCategoria());
        assertFalse(atraccionCultural.isDeTemporada());
        assertEquals(NivelesRiesgo.MEDIO, atraccionCultural.getNivelRiesgo());
    }
    
    @Test
    void testCrearEspectaculo() {
        assertEquals("Show de Luces", espectaculo.getNombre());
        assertEquals("Zona Central", espectaculo.getUbicacion());
        assertEquals(200, espectaculo.getCupoMaximo());
        assertEquals(0, espectaculo.getEmpleadosMinimos());
        assertEquals("Familiar", espectaculo.getClasificacionCategoria());
        assertTrue(espectaculo.isDeTemporada());
        assertEquals(18, espectaculo.getHorario());
        assertEquals(5, espectaculo.getEdadIngreso());
    }
    
    @Test
    void testAbrirCerrarAtracciones() {
        assertFalse(Atraccion.estaCerrada());
        
        Atraccion.cerrar();
        assertTrue(Atraccion.estaCerrada());
        
        assertFalse(atraccionMecanica.estaAbierta("2025-01-01"));
        
        Atraccion.abrir();
        assertFalse(Atraccion.estaCerrada());
        
        assertTrue(atraccionMecanica.estaAbierta("2025-01-01"));
    }
    
    @Test
    void testRestriccionesAtracciones() {
        assertEquals(2, atraccionMecanica.getRestriccionesClima().size());
        assertTrue(atraccionMecanica.getRestriccionesClima().contains("Lluvia"));
        assertTrue(atraccionMecanica.getRestriccionesClima().contains("Tormenta"));
        
        assertEquals(2, atraccionMecanica.getRestriccionesSalud().size());
        assertTrue(atraccionMecanica.getRestriccionesSalud().contains("Problemas cardíacos"));
        assertTrue(atraccionMecanica.getRestriccionesSalud().contains("Vértigo"));
    }
    
    // ============================================
    // PRUEBAS PARA EL REQUERIMIENTO 2: Manejar los empleados
    // ============================================
    
    @Test
    void testCreacionEmpleado() {
        assertEquals("Carlos", empleado.getNombre());
        assertEquals("Operador", empleado.getRol());
        assertTrue(empleado.isTurnoDiurno());
        assertFalse(empleado.isTurnoNocturno());
        assertEquals(1, empleado.getCapacitaciones().size());
        assertTrue(empleado.getCapacitaciones().contains("Manejo de atracciones"));
    }
    
    @Test
    void testDescuentoEmpleado() {
        assertEquals(1, Empleado.getDESCUENTOCOMPRA());
    }
    
    // ============================================
    // PRUEBAS PARA EL REQUERIMIENTO 3: Catálogo de atracciones
    // ============================================
    
    @Test
    void testObtenerCatalogoAtracciones() {
        ArrayList<Atraccion> catalogo = new ArrayList<>();
        catalogo.add(atraccionMecanica);
        catalogo.add(atraccionCultural);
        catalogo.add(espectaculo);
        
        assertEquals(3, catalogo.size());
        assertTrue(catalogo.contains(atraccionMecanica));
        assertTrue(catalogo.contains(atraccionCultural));
        assertTrue(catalogo.contains(espectaculo));
    }
    
    @Test
    void testFiltrarAtraccionesPorTipo() {
        ArrayList<Atraccion> catalogo = new ArrayList<>();
        catalogo.add(atraccionMecanica);
        catalogo.add(atraccionCultural);
        catalogo.add(espectaculo);
        
        ArrayList<Atraccion> mecanicas = new ArrayList<>();
        ArrayList<Atraccion> culturales = new ArrayList<>();
        ArrayList<Atraccion> espectaculos = new ArrayList<>();
        
        for (Atraccion a : catalogo) {
            if (a instanceof AtraccionMecanica) {
                mecanicas.add(a);
            } else if (a instanceof AtraccionCultural) {
                culturales.add(a);
            } else if (a instanceof Espectaculo) {
                espectaculos.add(a);
            }
        }
        
        assertEquals(1, mecanicas.size());
        assertEquals(1, culturales.size());
        assertEquals(1, espectaculos.size());
    }
    
    // ============================================
    // PRUEBAS PARA EL REQUERIMIENTO 4: Venta de tiquetes
    // ============================================
    
    @Test
    void testCreacionTiquetes() {
        TiqueteIndividual individual = new TiqueteIndividual(
            Categoria.Familiar, false, atraccionCultural, "T001", null
        );
        
        TiqueteTemporada temporada = new TiqueteTemporada(
            Categoria.Oro, false, "Verano", "T002", null
        );
        
        FastPass fastPass = new FastPass(
            Categoria.Diamante, false, new Date(), "FP001", null
        );
        
        assertNotNull(individual);
        assertNotNull(temporada);
        assertNotNull(fastPass);
        
        assertEquals(atraccionCultural, individual.getAtraccion());
        assertEquals("Verano", temporada.getTipoTemporadaLapso());
    }
    
    @Test
    void testUsoTiquete() {
        TiqueteIndividual tiquete = new TiqueteIndividual(
            Categoria.Familiar, false, atraccionCultural, "T001", null
        );
        
        assertFalse(tiquete.isFueUsado());
        tiquete.marcarComoUsado();
        assertTrue(tiquete.isFueUsado());
    }
    
    @Test
    void testValidezTiquete() {
        TiqueteTemporada tiquete = new TiqueteTemporada(
            Categoria.Oro, false, "Verano", "T002", null
        );
        
        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        LocalDate ayer = hoy.minusDays(1);
        
        tiquete.setFechaExpiracion(hoy);
        
        assertTrue(tiquete.esValidoParaUsar(ayer));
        assertTrue(tiquete.esValidoParaUsar(hoy));
        assertFalse(tiquete.esValidoParaUsar(manana));
    }
    
    @Test
    void testCompraTiqueteCliente() {
        TiqueteIndividual tiquete = new TiqueteIndividual(
            Categoria.Familiar, false, atraccionCultural, "T001", null
        );
        
        cliente.comprarTiquete(tiquete);
        
        assertEquals(1, cliente.getTiquetesNoUsados().size());
        assertTrue(cliente.getTiquetesNoUsados().contains(tiquete));
    }
}