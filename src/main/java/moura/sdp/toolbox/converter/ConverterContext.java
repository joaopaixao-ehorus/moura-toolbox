package moura.sdp.toolbox.converter;

import java.util.Set;

public interface ConverterContext {

    static ConverterContext of(Converter converter) {
        MutableConverterImpl context = new MutableConverterImpl();
        context.setConverter(converter);
        return context;
    }

    Converter getConverter();

    Set<Object> getParams();

}
