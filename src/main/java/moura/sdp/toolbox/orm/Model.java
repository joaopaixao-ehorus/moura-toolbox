package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.converter.ConverterException;

import java.util.*;

public class Model {

    private final ConverterContext converterContext;
    private final Map<String, Object> attributes = new HashMap<>();
    private final Map<String, Map<String, Object>> converterCache = new HashMap<>();

    public static ModelBuilder create() {
        return new ModelBuilder();
    }

    public Model(ConverterContext converterContext, Map<String, Object> attributes) {
        if (attributes != null) {
            this.attributes.putAll(attributes);
        }
        this.converterContext = converterContext;
    }

    public Model getModel(String attribute) {
        if (!attributes.containsKey(attribute)) {
            return null;
        }
        Object value = attributes.get(attribute);
        if (value instanceof Model) {
            return (Model) value;
        }
        throw new ConverterException(value + " isn't a Model");
    }

    public Set<String> getAttributeNames() {
        return attributes.keySet();
    }

    public Byte getByte(String attribute) {
        return get(attribute, Byte.class);
    }

    public Short getShort(String attribute) {
        return get(attribute, Short.class);
    }

    public Integer getInt(String attribute) {
        return get(attribute, Integer.class);
    }

    public Long getLong(String attribute) {
        return get(attribute, Long.class);
    }

    public String getString(String attribute) {
        return get(attribute, String.class);
    }

    public Object get(String attribute) {
        return attributes.get(attribute);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String attribute, Class<T> type) {
        Object value = attributes.get(attribute);
        if (value == null) {
            return null;
        }
        if (value.getClass().isAssignableFrom(type)) {
            return (T) value;
        }
        if (converterCache.containsKey(attribute) && converterCache.get(attribute).containsKey(type.getName())) {
            return (T) converterCache.get(attribute).get(type.getName());
        }
        T converted = converterContext.getConverter().convert(value, type, converterContext.getParams());
        if (!converterCache.containsKey(attribute)) {
            converterCache.put(attribute, new HashMap<>());
        }
        converterCache.get(attribute).put(type.getName(), converted);
        return converted;
    }

    public void set(String attribute, Object value) {
        converterCache.remove(attribute);
        attributes.put(attribute, value);
    }

    public void remove(String attribute) {
        converterCache.remove(attribute);
        attributes.remove(attribute);
    }

    public Map<String, Object> getAll() {
        return new HashMap<>(attributes);
    }

    public void setAll(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }
}
