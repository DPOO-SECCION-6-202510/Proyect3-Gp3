package CONTROL_PERSISTENCIA;

public class CentralPersistencia {

    public static final String JSON = "JSON";
    
    public PersistenciaParque getPersistenciaParque(String tipoArchivo) {
        if (tipoArchivo.equalsIgnoreCase(JSON)) {
            return new PersistenciaParqueJson();
        }

        throw new IllegalArgumentException("Tipo de archivo no soportado: " + tipoArchivo);
    }

}