package moura.sdp.toolbox.query;

import java.util.function.Consumer;

public interface RelationFetchConfigurer<T> {

    T with(Class<?> relation);

    T with(String relation);

    <R> T with(Class<R> relation, Consumer<TypedQueryBuilder<R>> builder);

    <R> T with(String relation, Consumer<TypedQueryBuilder<R>> builder);

}
