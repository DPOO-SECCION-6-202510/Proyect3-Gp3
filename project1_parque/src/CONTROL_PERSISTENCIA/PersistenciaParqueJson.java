package CONTROL_PERSISTENCIA;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory; // Para manejar herencia

import sistema_parque.sisParque.PrincipalParque;
import sistema_parque.atracciones.*;
import sistema_parque.lugaresServicio.*;
import sistema_parque.tiquetes.*;
import sistema_parque.usuarios.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate; // Para el adaptador de fecha
import java.util.ArrayList;
import java.util.List;
import java.util.Map; // Para la deserialización compleja de turnos
import java.util.HashMap; // Para la deserialización compleja de turnos

public class PersistenciaParqueJson implements PersistenciaParque {

    // --- Clase interna para envolver todos los datos del parque ---
    // Facilita la serialización/deserialización a un único objeto JSON
    private static class ParqueData {
        List<Atraccion> listaAtracciones;
        List<Usuario> listaUsuarios;
        List<LugarServicio> listaLugaresServicio;
        List<Tiquete> listaTiquetes;
        List<Turno> listaTurnos;
        String adminLogin;
    }

    private Gson createGson() {
        // Adaptador para manejar la herencia en Atraccion
        RuntimeTypeAdapterFactory<Atraccion> atraccionAdapterFactory = RuntimeTypeAdapterFactory
                .of(Atraccion.class, "tipoAtraccion") // "tipoAtraccion" será el campo en JSON
                .registerSubtype(AtraccionMecanica.class, "MECANICA")
                .registerSubtype(AtraccionCultural.class, "CULTURAL")
                .registerSubtype(Espectaculo.class, "ESPECTACULO");

        // Adaptador para manejar la herencia en Usuario
        RuntimeTypeAdapterFactory<Usuario> usuarioAdapterFactory = RuntimeTypeAdapterFactory
                .of(Usuario.class, "tipoUsuario")
                .registerSubtype(Cliente.class, "CLIENTE")
                .registerSubtype(Empleado.class, "EMPLEADO")
                .registerSubtype(Administrador.class, "ADMINISTRADOR")
                .registerSubtype(Cocinero.class, "COCINERO");

        // Adaptador para manejar la herencia en Tiquete
        RuntimeTypeAdapterFactory<Tiquete> tiqueteAdapterFactory = RuntimeTypeAdapterFactory
                .of(Tiquete.class, "tipoTiquete")
                .registerSubtype(TiqueteIndividual.class, "INDIVIDUAL")
                .registerSubtype(TiqueteTemporada.class, "TEMPORADA")
                .registerSubtype(FastPass.class, "FASTPASS");

         // Adaptador para manejar la herencia en LugarServicio
         RuntimeTypeAdapterFactory<LugarServicio> lugarAdapterFactory = RuntimeTypeAdapterFactory
             .of(LugarServicio.class, "tipoLugarServicio")
             .registerSubtype(Cafeteria.class, "CAFETERIA")
             .registerSubtype(Tienda.class, "TIENDA")
             .registerSubtype(Taquilla.class, "TAQUILLA");

        // Adaptador para LocalDate y TipoTurno (Gson no los maneja por defecto)
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Registrado para LocalDate
                .registerTypeAdapter(TipoTurno.class, new TipoTurnoAdapter()) // Añadir para manejar TipoTurno
                .registerTypeAdapterFactory(atraccionAdapterFactory)
                .registerTypeAdapterFactory(usuarioAdapterFactory)
                .registerTypeAdapterFactory(tiqueteAdapterFactory)
                .registerTypeAdapterFactory(lugarAdapterFactory)
                // .setPrettyPrinting() // Descomentar para que el JSON sea legible al guardar
                .serializeNulls(); // Opcional: guarda los campos null

        return builder.create();
    }


    @Override
    public void cargarParque(String archivo, PrincipalParque parque) {
        Gson gson = createGson();
        ParqueData data = null;

        try {
            // Leer el archivo JSON completo
            String jsonContent = new String(Files.readAllBytes(Paths.get(archivo)), StandardCharsets.UTF_8);

            // Deserializar el contenido JSON a nuestro objeto ParqueData
            data = gson.fromJson(jsonContent, ParqueData.class);

        } catch (FileNotFoundException e) {
            System.err.println("Archivo de persistencia no encontrado: " + archivo + ". Se iniciará con datos vacíos.");
            return;
        } catch (IOException e) {
            System.err.println("Error de E/S al leer el archivo: " + archivo);
            e.printStackTrace();
            return;
        } catch (Exception e) { // Captura errores de sintaxis JSON, de instanciación, etc.
            System.err.println("Error al parsear el archivo JSON: " + archivo);
            e.printStackTrace(); // Imprime el stack trace completo del error de parseo
            return;
        }

        // --- Poblar el objeto PrincipalParque con los datos cargados ---
        if (data != null) {
            // Limpiar listas actuales antes de llenarlas
            parque.getListaAtracciones().clear();
            parque.getListaUsuarios().clear();
            parque.getListaEmpleados().clear();
            parque.getListaLugaresServicio().clear();
            parque.getListaTiquetes().clear();
            parque.getListaTurnos().clear();

            // Añadir datos cargados de las listas principales
            // Es importante cargar estas listas ANTES de procesar los turnos
            if (data.listaAtracciones != null) parque.getListaAtracciones().addAll(data.listaAtracciones);
            if (data.listaUsuarios != null) parque.getListaUsuarios().addAll(data.listaUsuarios);
            if (data.listaLugaresServicio != null) parque.getListaLugaresServicio().addAll(data.listaLugaresServicio);
            if (data.listaTiquetes != null) parque.getListaTiquetes().addAll(data.listaTiquetes);

            // Poblar listaEmpleados filtrando listaUsuarios (después de cargar usuarios)
            if (parque.getListaUsuarios() != null) { // Usar la lista ya poblada del parque
                 for(Usuario u : parque.getListaUsuarios()) {
                     if (u instanceof Empleado) {
                         parque.getListaEmpleados().add((Empleado) u);
                     }
                 }
            }

            // --- Reconstrucción de Turnos y referencias ---
            // Primero, verifica si hay turnos para procesar
            if (data.listaTurnos != null && !data.listaTurnos.isEmpty()) {

                // CREA LOS MAPAS UNA SOLA VEZ, FUERA DEL BUCLE
                // Usando las listas YA CARGADAS en el objeto 'parque'
                Map<String, Empleado> empleadoMap = new HashMap<>();
                for (Usuario u : parque.getListaUsuarios()) { // Itera sobre parque.getListaUsuarios()
                    if (u instanceof Empleado) {
                        empleadoMap.put(u.getLogin(), (Empleado) u);
                    }
                }
                Map<String, Atraccion> atraccionMap = new HashMap<>();
                for (Atraccion a : parque.getListaAtracciones()) { // Itera sobre parque.getListaAtracciones()
                    atraccionMap.put(a.getNombre(), a);
                }
                Map<String, LugarServicio> lugarServicioMap = new HashMap<>();
                for (LugarServicio l : parque.getListaLugaresServicio()) { // Itera sobre parque.getListaLugaresServicio()
                    lugarServicioMap.put(l.getNombre(), l);
                }

                // RECORRE LA LISTA DE TURNOS UNA SOLA VEZ
                System.out.println("INFO: Iniciando reconstrucción de " + data.listaTurnos.size() + " turnos...");
                for (Turno turnoCargado : data.listaTurnos) {
                    // 1. Obtener la referencia correcta al Empleado usando el Map
                    String empleadoLogin = null;
                    // Verifica si el objeto Empleado dentro del Turno cargado existe
                    if (turnoCargado.getEmpleadoAsignado() != null) {
                        empleadoLogin = turnoCargado.getEmpleadoAsignado().getLogin(); // Obtiene el login
                    }

                    Empleado empleadoRef = null; // La referencia REAL al empleado en parque.getListaUsuarios()
                    if (empleadoLogin != null) {
                        empleadoRef = empleadoMap.get(empleadoLogin); // Busca en el mapa por login
                    } else {
                        System.err.println("Advertencia al cargar turno: El empleado asignado en el turno cargado es null o no tiene login.");
                        continue; // Saltar al siguiente turno si no hay info del empleado
                    }

                    // 2. Obtener la referencia correcta al Lugar (Atraccion o LugarServicio)
                    Object lugarDeserializado = turnoCargado.getLugarAsignado(); // El objeto (posiblemente Map) que Gson creó
                    Object lugarRef = null; // La referencia REAL al lugar en las listas del parque
                    String nombreLugar = null; // Variable para guardar el nombre extraído

                    if (lugarDeserializado != null) {
                        // Intenta extraer el nombre del lugar
                        if (lugarDeserializado instanceof Map) { // Caso más probable: Gson lo hizo un Map
                            Object nombreObj = ((Map<?, ?>) lugarDeserializado).get("nombre");
                            if (nombreObj instanceof String) {
                                nombreLugar = (String) nombreObj;
                            }
                        } else if (lugarDeserializado instanceof Atraccion) { // Si Gson logró deserializar algo (menos probable)
                           nombreLugar = ((Atraccion)lugarDeserializado).getNombre();
                        } else if (lugarDeserializado instanceof LugarServicio) { // Si Gson logró deserializar algo (menos probable)
                           nombreLugar = ((LugarServicio)lugarDeserializado).getNombre();
                        }
                        // Podrías añadir más `else if` si hay otras formas de obtener el nombre

                        // Si pudimos obtener un nombre, buscar la referencia real en los mapas
                        if (nombreLugar != null) {
                            lugarRef = atraccionMap.get(nombreLugar); // Busca en atracciones
                            if (lugarRef == null) {
                                lugarRef = lugarServicioMap.get(nombreLugar); // Si no, busca en lugares de servicio
                            }
                            // Debug: System.out.println("INFO: Buscando lugar '" + nombreLugar + "'. Encontrado: " + (lugarRef != null));
                        } else {
                             // Si no se pudo extraer el nombre del objeto deserializado
                             System.err.println("Advertencia al cargar turno para empleado '" + empleadoLogin + "': No se pudo extraer el nombre del lugar asignado: " + lugarDeserializado);
                             continue; // Saltar al siguiente turno
                        }
                    } else {
                         // Si el lugar deserializado era null
                         System.err.println("Advertencia al cargar turno para empleado '" + empleadoLogin + "': El lugar asignado en el turno cargado es null.");
                         continue; // Saltar al siguiente turno
                    }

                    // 3. Crear y añadir el Turno RECONSTRUIDO si tenemos ambas referencias REALES
                    if (empleadoRef != null && lugarRef != null) {
                         // Doble chequeo por si acaso (aunque si vino de los maps debería ser uno de estos)
                         if (lugarRef instanceof Atraccion || lugarRef instanceof LugarServicio) {
                             Turno turnoReconstruido = new Turno(
                                 empleadoRef,       // La instancia REAL del Empleado de la lista principal
                                 lugarRef,          // La instancia REAL de Atraccion/LugarServicio de las listas principales
                                 turnoCargado.getFecha(), // Fecha del turno cargado
                                 turnoCargado.getTipoTurno() // Tipo del turno cargado
                             );
                             parque.getListaTurnos().add(turnoReconstruido); // Añadir el turno CORRECTO
                             // Debug: System.out.println("INFO: Turno reconstruido añadido: " + turnoReconstruido);
                         } else {
                              // Si lugarRef no es ni Atraccion ni LugarServicio (inesperado)
                              System.err.println("Error INESPERADO al cargar turno para empleado '" + empleadoLogin + "': lugarRef encontrado no es Atraccion ni LugarServicio: " + lugarRef.getClass().getName());
                         }
                    } else {
                        // Si falló la búsqueda del empleado o del lugar en los mapas
                        System.err.println("Error al cargar turno: No se encontró la referencia para empleado con login '"
                                           + empleadoLogin + "' (encontrado: " + (empleadoRef != null) + ") o lugar con nombre '"
                                           + (nombreLugar != null ? nombreLugar : "???") + "' (encontrado: " + (lugarRef != null) + "). Verificar consistencia de datos en JSON.");
                    }
                } // Fin del ÚNICO bucle for para procesar turnos
                System.out.println("INFO: Reconstrucción de turnos finalizada. Turnos reconstruidos en parque: " + parque.getListaTurnos().size());

            } else {
                 System.out.println("INFO: No hay turnos en los datos cargados para procesar.");
            } // Fin if (data.listaTurnos != null && !data.listaTurnos.isEmpty())


            // Encontrar y asignar el administrador (después de cargar usuarios)
            if (data.adminLogin != null) {
                boolean adminEncontrado = false;
                for (Usuario u : parque.getListaUsuarios()) { // Usar la lista del parque
                    if (u instanceof Administrador && u.getLogin().equals(data.adminLogin)) {
                        parque.setAdministrador((Administrador) u);
                        adminEncontrado = true;
                        break;
                    }
                }
                 if (!adminEncontrado) { // Si el bucle terminó sin encontrarlo
                     System.err.println("Advertencia: No se encontró el administrador con login '" + data.adminLogin + "' en la lista de usuarios cargada.");
                 }
            } else {
                 System.err.println("Advertencia: No se especificó un adminLogin en el archivo JSON.");
            }

            System.out.println("Datos del parque cargados exitosamente desde: " + archivo);

        } else {
             System.err.println("Error: No se pudieron deserializar los datos (objeto ParqueData fue null) desde: " + archivo);
        }
    } // Fin cargarParque

    @Override
    public void salvarParque(String archivo, PrincipalParque parque) {
        Gson gson = createGson();
        ParqueData data = new ParqueData();

        // Copiar datos desde PrincipalParque al objeto ParqueData para guardar
        // Es mejor crear nuevas listas para evitar modificar las originales si Gson hiciera algo inesperado
        data.listaAtracciones = new ArrayList<>(parque.getListaAtracciones());
        data.listaUsuarios = new ArrayList<>(parque.getListaUsuarios());
        data.listaLugaresServicio = new ArrayList<>(parque.getListaLugaresServicio());
        data.listaTiquetes = new ArrayList<>(parque.getListaTiquetes());
        data.listaTurnos = new ArrayList<>(parque.getListaTurnos()); // Guarda los turnos reconstruidos/correctos

        if (parque.getAdministrador() != null) {
            data.adminLogin = parque.getAdministrador().getLogin();
        } else {
            data.adminLogin = null;
        }

        // Convertir el objeto ParqueData a JSON
        String json = gson.toJson(data);

        // Escribir el JSON al archivo
        try (Writer writer = Files.newBufferedWriter(Paths.get(archivo), StandardCharsets.UTF_8)) {
            writer.write(json);
            System.out.println("Datos del parque guardados exitosamente en: " + archivo);
        } catch (IOException e) {
            System.err.println("Error de E/S al guardar el archivo: " + archivo);
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error al generar el JSON o guardar el archivo: " + archivo);
            e.printStackTrace();
        }
    } // Fin salvarParque


     private static class LocalDateAdapter extends com.google.gson.TypeAdapter<LocalDate> {
         @Override
         public void write(com.google.gson.stream.JsonWriter out, LocalDate value) throws IOException {
             if (value == null) {
                 out.nullValue();
             } else {
                 out.value(value.toString()); // Guardar como "YYYY-MM-DD"
             }
         }

         @Override
         public LocalDate read(com.google.gson.stream.JsonReader in) throws IOException {
             if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
                 in.nextNull();
                 return null;
             } else {
                 String dateString = in.nextString();
                 try {
                     return LocalDate.parse(dateString); // Leer como "YYYY-MM-DD"
                 } catch (java.time.format.DateTimeParseException e) {
                     System.err.println("Error parseando fecha: '" + dateString + "'. Asegúrate que esté en formato YYYY-MM-DD.");
                    
                     return null; 
                 }
             }
         }
     } // Fin LocalDateAdapter
     
     
     
} // Fin clase PersistenciaParqueJson