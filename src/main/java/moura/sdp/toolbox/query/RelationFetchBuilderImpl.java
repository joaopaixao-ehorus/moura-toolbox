package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.EntityRegistry;
import moura.sdp.toolbox.orm.RelationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RelationFetchBuilderImpl<T> implements RelationFetchBuilder<T> {

    private final Class<T> clazz;
    private final EntityRegistry registry;
    private final List<RelationToFetch<?, T>> relationsToFetch = new ArrayList<>();

    public RelationFetchBuilderImpl(Class<T> clazz, EntityRegistry registry) {
        this.clazz = clazz;
        this.registry = registry;
    }

    @Override
    public List<RelationToFetch<?, T>> build() {
        return relationsToFetch;
    }

    @Override
    public RelationFetchBuilderImpl<T> with(String relation) {
        return with(relation, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> RelationFetchBuilderImpl<T> with(String relation, Consumer<TypedQueryBuilder<R>> builder) {
        relationsToFetch.add(new RelationToFetch<>((RelationConfiguration<R, T>) registry.getConfig(clazz).getRelation(relation), builder));
        return this;
    }

    @Override
    public RelationFetchBuilderImpl<T> with(Class<?> relationClass) {
        return with(relationClass, null);
    }

    @Override
    public <R> RelationFetchBuilderImpl<T> with(Class<R> relationClass, Consumer<TypedQueryBuilder<R>> customizer) {
        relationsToFetch.add(new RelationToFetch<>(registry.getConfig(clazz).getRelation(relationClass), customizer));
        return this;
    }
}
