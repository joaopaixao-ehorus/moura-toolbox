package moura.sdp.toolbox.common;

import moura.sdp.toolbox.converter.ConverterContext;

import java.util.Map;

public interface DynamicAccessor<T> {

    void setAttribute(T entity, String attribute, Object value, ConverterContext context);

    Object getAttribute(T entity, String attribute);

    void setAttributes(T entity, Map<String, Object> attributes, ConverterContext context);

    Map<String, Object> getAttributes(T entity);

}
