package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.*;

import java.util.*;
import java.util.function.Consumer;

public class RelationToFetch<T, E>  {

    private final RelationConfiguration<T, E> configuration;
    private final Consumer<TypedQueryBuilder<T>> queryEnhancer;

    public RelationToFetch(RelationConfiguration<T, E> configuration, Consumer<TypedQueryBuilder<T>> queryEnhancer) {
        this.configuration = configuration;
        this.queryEnhancer = queryEnhancer;
    }

    public RelationConfiguration<T, E> getConfiguration() {
        return configuration;
    }

    public void load(Database database, List<E> entities, EntityConfiguration<E> entityConfig) {
        RelationConfiguration<T, E> relationConfig = getConfiguration();
        relationConfig.load(database, entities, entityConfig, this);
    }

    public void enhance(TypedQueryBuilder<T> builder) {
        if (queryEnhancer == null) {
            return;
        }
        queryEnhancer.accept(builder);
    }

}
