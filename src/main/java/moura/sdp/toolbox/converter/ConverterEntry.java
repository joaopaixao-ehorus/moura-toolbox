package moura.sdp.toolbox.converter;

public interface ConverterEntry<T, R> {

    Class<T> getTargetClass();

    Class<R> getResultClass();

    R convert(T object, ConverterContext params);

}
