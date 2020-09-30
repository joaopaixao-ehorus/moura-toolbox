package moura.sdp.toolbox.query;

import java.util.List;

@SuppressWarnings("UnusedReturnValue")
public interface WhereConfigurer<T> {

    T where();

    T where(String field);

    T and(String field);

    T or(String field);

    T is(Object value);

    T isNot(Object value);

    T greaterThan(Object value);

    T lowerThan(Object value);

    T greaterThanOrEqual(Object value);

    T lowerThanOrEqual(Object value);

    T isNull();

    T isNotNull();

    T in(Object ...params);

    T in(List<Object> params);

    T notIn(List<Object> params);

}
