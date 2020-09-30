package moura.sdp.toolbox.converter;

import java.util.Set;

public interface ConverterContext {

    Converter getConverter();

    Set<Object> getParams();

}
