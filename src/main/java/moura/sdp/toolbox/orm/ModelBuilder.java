package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.converter.MutableConverterContext;

import java.util.Map;

public class ModelBuilder {

    private static final ConverterContext defaultConverter;

    static {
        MutableConverterContext context = new MutableConverterContext();
        context.setConverter(new Converter());
        defaultConverter = context;
    }

    private ConverterContext converter;
    private Map<String, Object> attributes;

    public ModelBuilder withConverter(ConverterContext converter) {
        this.converter = converter;
        return this;
    }

    public ModelBuilder withAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public Model done() {
        return new Model(converter == null ? defaultConverter : converter, attributes);
    }

}
