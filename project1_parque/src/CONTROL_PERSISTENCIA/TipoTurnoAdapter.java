package CONTROL_PERSISTENCIA;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import sistema_parque.usuarios.TipoTurno;

import java.io.IOException;

/**
 * Adaptador para convertir entre Strings en JSON y objetos TipoTurno
 */
public class TipoTurnoAdapter extends TypeAdapter<TipoTurno> {

    @Override
    public void write(JsonWriter out, TipoTurno value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        
        // Al serializar, solo usamos el código como un string simple
        out.value(value.getCodigo());
    }

    @Override
    public TipoTurno read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        
        // Al deserializar, convertimos el string a un objeto TipoTurno completo
        String codigo = in.nextString();
        
        // Asignar una descripción basada en el código
        String descripcion;
        switch (codigo) {
            case "APERTURA":
                descripcion = "Turno de Apertura";
                break;
            case "CIERRE":
                descripcion = "Turno de Cierre";
                break;
            case "MANTENIMIENTO":
                descripcion = "Turno de Mantenimiento";
                break;
            default:
                descripcion = "Turno " + codigo;
                break;
        }
        
        return new TipoTurno(codigo, descripcion);
    }
}