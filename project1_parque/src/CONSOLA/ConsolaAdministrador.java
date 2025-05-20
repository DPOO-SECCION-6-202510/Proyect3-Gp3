package CONSOLA;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.usuarios.Administrador;
import sistema_parque.usuarios.Empleado;
import sistema_parque.atracciones.Atraccion;
import sistema_parque.atracciones.AtraccionCultural;
import sistema_parque.atracciones.AtraccionMecanica;
import sistema_parque.atracciones.Espectaculo;
import sistema_parque.atracciones.NivelesRiesgo;

public class ConsolaAdministrador {
    private final PrincipalParque parque;
    private final Administrador admin;
    private final Scanner scanner;

    public ConsolaAdministrador(PrincipalParque parque, Administrador admin) {
        this.parque = parque;
        this.admin = admin;
        this.scanner = new Scanner(System.in);
    }

    public void mostrarMenu() {
        System.out.println("\n╔══════════════════════════════════════════╗");
        System.out.println("║          PANEL DE ADMINISTRACIÓN         ║");
        System.out.println("╚══════════════════════════════════════════╝");

        int opcion = -1;
        while (opcion != 0) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Gestionar empleados");
            System.out.println("2. Gestionar atracciones");
            System.out.println("3. Asignar turnos");
            System.out.println("0. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        gestionarEmpleados();
                        break;
                    case 2:
                        gestionarAtracciones();
                        break;
                    case 3:
                        gestionarTurnos();
                        break;
                    case 0:
                        System.out.println("Cerrando sesión...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (InputMismatchException e) {
                System.err.println("Error: Debe ingresar un número válido");
                scanner.nextLine();
            }
        }
    }

    private void gestionarEmpleados() {
        System.out.println("\n--- GESTIÓN DE EMPLEADOS ---");
        System.out.println("1. Agregar empleado");
        System.out.println("2. Editar empleado");
        System.out.println("3. Eliminar empleado");
        System.out.print("Seleccione opción: ");
        
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    agregarEmpleado();
                    break;
                case 2:
                    editarEmpleado();
                    break;
                case 3:
                    eliminarEmpleado();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido");
            scanner.nextLine();
        }
    }

    private void agregarEmpleado() {
        System.out.println("\n--- AGREGAR EMPLEADOS ---");
        List<Empleado> empleados = parque.getListaEmpleados();
        System.out.print("Nombre del empleado: ");       
        String nombre = scanner.nextLine();
        System.out.print("login del empleado: ");
        String login = scanner.nextLine();
        System.out.print("contraseña del empleado: ");
        String contraseña = scanner.nextLine();
        System.out.print("rol del empleado: ");
        String rol = scanner.nextLine();
        System.out.print("capacitaciones del empleado (separados por comas): ");
        String capacitaciones = scanner.nextLine();
        String[] array = capacitaciones.split(",");
        ArrayList<String> lista = new ArrayList<>();
        for (String capacitacion : array) {
        	lista.add(capacitacion);
        }
        System.out.print("¿Tiene turno Diurno? si/no: ");
        String diurno = scanner.nextLine();
        boolean es1 = false;
        if (diurno == "si") {
        	es1 = true;
        }
        System.out.print("¿Tiene turno Nocturno? si/no: ");
        String nocturno = scanner.nextLine();
        boolean es2 = false;
        if (nocturno == "si") {
        	es2 = true;
        }
        
        Empleado empleado = new Empleado(nombre, login, contraseña, rol, lista, es1, es2);
        empleados.add(empleado);
        }
    
    private void editarEmpleado() {
    	List<Empleado> empleados = parque.getListaEmpleados();
        System.out.print("Nombre del empleado a editar: ");
        String nombre = scanner.nextLine();
        Empleado empleado = null;
        for(int i = 0; i < empleados.size(); i++) {
        	if(empleados.get(i).getNombre() == nombre) {
        		empleado = empleados.get(i);
        	}
        }
        if(empleado == null) {
        	System.out.println("No se encontro el empleado con el nombre "+ nombre);
        }
        else {
        	System.out.print("rol nuevo del empleado: ");
            String rol = scanner.nextLine();
        	System.out.print("capacitaciones nuevas del empleado (separados por comas): ");
            String capacitaciones = scanner.nextLine();
            String[] array = capacitaciones.split(",");
            ArrayList<String> lista = new ArrayList<>();
            for (String capacitacion : array) {
            	lista.add(capacitacion);
            }
            System.out.print("¿Tiene turno Diurno? si/no: ");
            String diurno = scanner.nextLine();
            boolean es1 = false;
            if (diurno == "si") {
            	es1 = true;
            }
            System.out.print("¿Tiene turno Nocturno? si/no: ");
            String nocturno = scanner.nextLine();
            boolean es2 = false;
            if (nocturno == "si") {
            	es2 = true;
            }
            boolean cambio = admin.cambiarInfoEmpleado(empleado, rol, lista, es1, es2);
        	if(cambio == true) {
        		System.out.println("Se cambio la informacion exitosamente! ");
        	}
        	else {
        		System.out.print("No se pudo cambiar la informacion del empleado ");
        	}
        	}
        }
    
    private void eliminarEmpleado() {
    	List<Empleado> empleados = parque.getListaEmpleados();
    	System.out.print("Nombre del empleado a eliminar: ");
        String nombre = scanner.nextLine();
        Empleado empleado = null;
        for(int i = 0; i < empleados.size(); i++) {
        	if(empleados.get(i).getNombre() == nombre) {
        		empleado = empleados.get(i);
        	}
        }
        if(empleado == null) {
        	System.out.println("No se encontro el empleado con el nombre "+ nombre);
        }
        else {
        	empleados.remove(empleado);
        }	
    }

    private void gestionarAtracciones() {
        System.out.println("\n--- GESTIÓN DE ATRACCIONES ---");
        System.out.println("1. Agregar atracción");
        System.out.println("2. Modificar atracción");
        System.out.print("Seleccione opción: ");
        
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    agregarAtraccion();
                    break;
                case 2:
                    modificarAtraccion();
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } catch (InputMismatchException e) {
            System.err.println("Error: Debe ingresar un número válido");
            scanner.nextLine();
        }
    }
    
    private void agregarAtraccion() {
    	 System.out.println("\n--- AGREGAR ATRACCION ---");
         List<Atraccion> atracciones = parque.getListaAtracciones();
         System.out.print("ubicacion de la atraccion: ");       
         String ubicacion = scanner.nextLine();
         System.out.print("nombre de la atraccion: ");
         String nombre = scanner.nextLine();
         System.out.print("cupo maximo de la atraccion: ");
         String cupo = scanner.nextLine();
         int numero1 = Integer.parseInt(cupo);
         System.out.print("empleados minimos para la atraccion: ");
         String empleadosmin = scanner.nextLine();
         int numero2 = Integer.parseInt(empleadosmin);
         System.out.print("categoria de la atraccion: ");
         String categoria = scanner.nextLine();
         System.out.print("¿es de temporada? si/no ");
         String detemporada = scanner.nextLine();
         boolean es = false;
         if (detemporada == "si") {
         	es = true;
         }
         System.out.print("restricciones del clima (separados por comas): ");
         String restricciones = scanner.nextLine();
         String[] array = restricciones.split(",");
         ArrayList<String> lista = new ArrayList<>();
         for (String restriccion : array) {
         	lista.add(restriccion);
         }
         System.out.print("nivel de riesgo de la atraccion: ");
         String riesgo = scanner.nextLine();
         NivelesRiesgo nivel = null;
         if(riesgo == "ALTO") {
        	 nivel = NivelesRiesgo.ALTO;
         }
         else if(riesgo == "MEDIO") {
        	 nivel = NivelesRiesgo.ALTO;
         }
         else if(riesgo == "BAJO") {
        	 nivel = NivelesRiesgo.BAJO;
         }
         System.out.print("detalles de la atraccion de temporada: ");
         String detalles = scanner.nextLine();
         System.out.print("tipo de atraccion: ");
    	 String tipo = scanner.nextLine();
    	 if(tipo == "Atraccion cultural") {
    		 AtraccionCultural atraccion = new AtraccionCultural(ubicacion, nombre, numero1, numero2, categoria, es, lista, nivel, detalles);
    		 atracciones.add(atraccion);
    	 }
    	 else if(tipo == "Atraccion mecanica") {
    		 System.out.print("altura maxima: ");
    		 String altmax = scanner.nextLine();
    		 int numero3 = Integer.parseInt(altmax);
    		 System.out.print("altura minima: ");
             String altmin = scanner.nextLine();
             int numero4 = Integer.parseInt(altmin);
             System.out.print("peso maximo: ");
             String pesmax = scanner.nextLine();
             int numero5 = Integer.parseInt(pesmax);
             System.out.print("peso minimo: ");
             String pesmin = scanner.nextLine();
             int numero6 = Integer.parseInt(pesmin);
             System.out.print("restricciones de salud(separados por comas): ");
             restricciones = scanner.nextLine();
             array = restricciones.split(",");
             ArrayList<String> lista2 = new ArrayList<>();
             for (String restriccion : array) {
             	lista2.add(restriccion);
             }
             
    		 AtraccionMecanica atraccion = new AtraccionMecanica(ubicacion, nombre, numero1, numero2, categoria, es, lista, nivel, detalles, numero3, numero4, numero5, numero6, lista2);
    		 atracciones.add(atraccion);
    	 }
    	 else if(tipo == "Espectaculo") {
    		 Espectaculo atraccion = new Espectaculo(ubicacion, nombre, numero1, numero2, categoria, es, lista, nivel, detalles);
    		 atracciones.add(atraccion);
    	 }	
    }
    
    private void modificarAtraccion() {
    	
    }
    
    private void gestionarTurnos() {
    	
    }
    
    public static void main(PrincipalParque parque, Administrador admin) {
        new ConsolaAdministrador(parque, admin).mostrarMenu();
    }
}