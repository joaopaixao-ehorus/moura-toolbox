package moura.sdp.toolbox.converter;

import java.util.Set;

public class MutableConverterImpl implements ConverterContext {

    private Converter converter;
    private Set<Object> hints;

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
