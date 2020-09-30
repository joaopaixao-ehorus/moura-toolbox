package moura.sdp.toolbox.converter;

import java.util.function.BiFunction;

class ConverterEntryImpl<T, R> implements ConverterEntry<T, R> {

    private final Class<T> targetClass;
    private final Class<R> resultClass;
    private final BiFunction<T, ConverterContext, R> function;

    public ConverterEntryImpl(Class<T> targetClass, Class<R> resultClass, BiFunction<T, ConverterContext, R> function) {
        this.targetClass = targetClass;
        this.resultClass = resultClass;
        this.function = function;
    }

    @Override
    public R convert(T object, ConverterContext params) {
        return function.apply(object, params);
    }

    @Override
    public Class<T> getTargetClass() {
        return targetClass;
    }

    @Override
    public Class<R> getResultClass() {
        return resultClass;
    }
}
