package moura.sdp.toolbox.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Converter {

    private final Map<String, ConverterEntry<Object, Object>> converterMap = new ConcurrentHashMap<>();

    public Converter() {
        register(Converters::byteToShort, Byte.class, Short.class);
        register(Converters::byteToInt, Byte.class, Integer.class);
        register(Converters::byteToLong, Byte.class, Long.class);
        register(Converters::byteToString, Byte.class, String.class);
        register(Converters::shortToByte, Short.class, Byte.class);
        register(Converters::shortToInt, Short.class, Integer.class);
        register(Converters::shortToLong, Short.class, Long.class);
        register(Converters::shortToString, Short.class, String.class);
        register(Converters::intToByte, Integer.class, Byte.class);
        register(Converters::intToShort, Integer.class, Short.class);
        register(Converters::intToLong, Integer.class, Long.class);
        register(Converters::intToString, Integer.class, String.class);
        register(Converters::longToByte, Long.class, Byte.class);
        register(Converters::longToShort, Long.class, Short.class);
        register(Converters::longToInt, Long.class, Integer.class);
        register(Converters::longToString, Long.class, String.class);
        register(Converters::strToByte, String.class, Byte.class);
        register(Converters::strToShort, String.class, Short.class);
        register(Converters::strToInt, String.class, Integer.class);
        register(Converters::strToLong, String.class, Long.class);
    }

    @SuppressWarnings("unchecked")
    public void register(ConverterEntry<?, ?> entry) {
        String key = makeKey(entry.getTargetClass(), entry.getResultClass());
        converterMap.put(key, (ConverterEntry<Object, Object>) entry);
    }

    synchronized public <T, R> void register(Function<T, R> function, Class<T> targetClass, Class<R> resultClass) {
        register((a, b) -> function.apply(a), targetClass, resultClass);
    }

    public <T, R> void register(BiFunction<T, ConverterContext, R> biFunction, Class<T> targetClass, Class<R> resultClass) {
        register(new ConverterEntryImpl<>(targetClass, resultClass, biFunction));
    }

    @SuppressWarnings("unchecked")
    public <T, R> R convert(T target, Class<R> resultClass, Set<Object> hints) {
        if (target == null) {
            return null;
        }
        if (resultClass.equals(target.getClass())) {
            return (R) target;
        }
        if (resultClass.isAssignableFrom(target.getClass())) {
            return (R) target;
        }
        ConverterEntry<Object, Object> entry = converterMap.get(makeKey(target.getClass(), resultClass));
        if (entry == null) {
            throw new ConverterException("There is no converter registered for converting " + target.getClass() + " to " + resultClass);
        }
        MutableConverterContext context = new MutableConverterContext();
        context.setParams(hints);
        context.setConverter(this);
        return (R) entry.convert(target, context);
    }

    public <T, R> R convert(T target, Class<R> resultClass) {
        return convert(target, resultClass, null);
    }

    private String makeKey(Class<?> classA, Class<?> classB) {
        return classA.getName() + "@" + classB.getName();
    }

}
