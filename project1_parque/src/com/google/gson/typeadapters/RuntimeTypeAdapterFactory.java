// Copyright 2011 Google Inc. All Rights Reserved.
// Copied from Gson source code repository (extras) to handle polymorphism.

package com.google.gson.typeadapters;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Adapts values whose runtime type may differ from their declaration type. This
 * is necessary when a field's type is an interface or abstract class and the
 * actual values are of concrete types. To use this adapter, you must register
 * it with a {@link com.google.gson.GsonBuilder} instance:
 * <pre>   {@code
 * GsonBuilder builder = new GsonBuilder();
 * builder.registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(Widget.class));
 * Gson gson = builder.create();
 * }</pre>
 * This factory supports robust type information via the key listed in the JSON
 * object, or guessing based on the value's nested fields.
 *
 * @param <T> The type adapted by this factory.
 */
public final class RuntimeTypeAdapterFactory<T> implements TypeAdapterFactory {
    private final Class<?> baseType;
    private final String typeFieldName;
    private final Map<String, Class<?>> labelToSubtype = new LinkedHashMap<>();
    private final Map<Class<?>, String> subtypeToLabel = new LinkedHashMap<>();
    private final boolean maintainType;

    private RuntimeTypeAdapterFactory(Class<?> baseType, String typeFieldName, boolean maintainType) {
        if (typeFieldName == null || baseType == null) {
            throw new NullPointerException();
        }
        this.baseType = baseType;
        this.typeFieldName = typeFieldName;
        this.maintainType = maintainType;
    }

    /**
     * Creates a new runtime type adapter using the given {@code typeFieldName} name
     * and the base type {@code T} for the internal type mapping. Type field names
     * are case sensitive.
     *
     * @param <T> The type for which the factory should be created.
     * @param baseType The base type for the factory.
     * @param typeFieldName The name of the type field.
     * @return A new instance of RuntimeTypeAdapterFactory.
     */
    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName, false);
    }

    /**
     * Creates a new runtime type adapter using the given {@code typeFieldName} name
     * and the base type {@code T} for the internal type mapping. Type field names
     * are case sensitive. {@code maintainType} flag define if the type will be
     * stored in pojo or not.
     *
     * @param <T> The type for which the factory should be created.
     * @param baseType The base type for the factory.
     * @param typeFieldName The name of the type field.
     * @param maintainType Define if the type will be stored in the pojo or not.
     * @return A new instance of RuntimeTypeAdapterFactory.
     */
    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType, String typeFieldName, boolean maintainType) {
        return new RuntimeTypeAdapterFactory<>(baseType, typeFieldName, maintainType);
    }

    /**
     * Creates a new runtime type adapter using {@code "type"} as the type field name
     * and the base type {@code T} for the internal type mapping. Type field names
     * are case sensitive.
     *
     * @param <T> The type for which the factory should be created.
     * @param baseType The base type for the factory.
     * @return A new instance of RuntimeTypeAdapterFactory.
     */
    public static <T> RuntimeTypeAdapterFactory<T> of(Class<T> baseType) {
        return new RuntimeTypeAdapterFactory<>(baseType, "type", false);
    }

    /**
     * Registers {@code type} identified by {@code label}. Labels are case
     * sensitive.
     *
     * @throws IllegalArgumentException if either {@code type} or {@code label}
     * have already been registered on this type adapter.
     * @param type The class type to register.
     * @param label The label for the registered type.
     * @return This instance for chaining calls.
     */
    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type, String label) {
        if (type == null || label == null) {
            throw new NullPointerException();
        }
        if (subtypeToLabel.containsKey(type) || labelToSubtype.containsKey(label)) {
            throw new IllegalArgumentException("types and labels must be unique");
        }
        labelToSubtype.put(label, type);
        subtypeToLabel.put(type, label);
        return this;
    }

    /**
     * Registers {@code type} identified by its {@link Class#getSimpleName simple
     * name}. Labels are case sensitive.
     *
     * @throws IllegalArgumentException if either {@code type} or its simple name
     * have already been registered on this type adapter.
     * @param type The class type to register.
     * @return This instance for chaining calls.
     */
    public RuntimeTypeAdapterFactory<T> registerSubtype(Class<? extends T> type) {
        return registerSubtype(type, type.getSimpleName());
    }

    @Override
    public <R> TypeAdapter<R> create(Gson gson, TypeToken<R> type) {
        if (type.getRawType() != baseType) {
            return null;
        }

        final TypeAdapter<JsonElement> jsonElementAdapter = gson.getAdapter(JsonElement.class);
        final Map<String, TypeAdapter<?>> labelToDelegate = new LinkedHashMap<>();
        final Map<Class<?>, TypeAdapter<?>> subtypeToDelegate = new LinkedHashMap<>();

        for (Map.Entry<String, Class<?>> entry : labelToSubtype.entrySet()) {
            TypeAdapter<?> delegate = gson.getDelegateAdapter(this, TypeToken.get(entry.getValue()));
            labelToDelegate.put(entry.getKey(), delegate);
            subtypeToDelegate.put(entry.getValue(), delegate);
        }


        return new TypeAdapter<R>() {
            @Override public R read(JsonReader in) throws IOException {
                JsonElement jsonElement = jsonElementAdapter.read(in);
                JsonElement labelJsonElement;
                if (maintainType) {
                    labelJsonElement = jsonElement.getAsJsonObject().get(typeFieldName);
                } else {
                    labelJsonElement = jsonElement.getAsJsonObject().remove(typeFieldName);
                }

                if (labelJsonElement == null) {
                    throw new JsonParseException("cannot deserialize " + baseType
                            + " because it does not define a field named " + typeFieldName);
                }
                String label = labelJsonElement.getAsString();
                @SuppressWarnings("unchecked") // registration requires that subtype extends T
                TypeAdapter<R> delegate = (TypeAdapter<R>) labelToDelegate.get(label);
                if (delegate == null) {
                    throw new JsonParseException("cannot deserialize " + baseType + " subtype named "
                            + label + "; did you forget to register a subtype?");
                }
                return delegate.fromJsonTree(jsonElement);
            }

            @Override public void write(JsonWriter out, R value) throws IOException {
                Class<?> srcType = value.getClass();
                String label = subtypeToLabel.get(srcType);
                @SuppressWarnings("unchecked") // registration requires that subtype extends T
                TypeAdapter<R> delegate = (TypeAdapter<R>) subtypeToDelegate.get(srcType);
                if (delegate == null) {
                    throw new JsonParseException("cannot serialize " + srcType.getName()
                            + "; did you forget to register a subtype?");
                }
                JsonObject jsonObject = delegate.toJsonTree(value).getAsJsonObject();

                if (maintainType) {
                    gson.toJson(jsonObject, out); 
                    return;
                }

                JsonObject clone = new JsonObject();

                if (jsonObject.has(typeFieldName)) {
                    throw new JsonParseException("cannot serialize " + srcType.getName()
                            + " because it already defines a field named " + typeFieldName);
                }
                clone.add(typeFieldName, new JsonPrimitive(label));

                for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
                    clone.add(e.getKey(), e.getValue());
                }
                gson.toJson(clone, out);
            }
        }.nullSafe();
    }
}