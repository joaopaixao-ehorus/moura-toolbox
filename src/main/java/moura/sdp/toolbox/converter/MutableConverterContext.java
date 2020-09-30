package moura.sdp.toolbox.converter;

import java.util.Set;

public class MutableConverterContext implements ConverterContext {

    private Converter converter;
    private Set<Object> hints;

    public static ConverterContext of(Converter converter) {
        MutableConverterContext context = new MutableConverterContext();
        context.setConverter(converter);
        return context;
    }

    @Override
    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    @Override
    public Set<Object> getParams() {
        return hints;
    }

    public void setParams(Set<Object> params) {
        this.hints = params;
    }
}
