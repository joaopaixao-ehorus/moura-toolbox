package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.common.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RelationsConfigurationBuilder<T> implements Builder<List<RelationConfiguration<?, T>>> {

    private final Class<T> clazz;
    private final List<RelationConfiguration<?, T>> relations = new ArrayList<>();

    public RelationsConfigurationBuilder(Class<T> clazz) {
        this.clazz = clazz;
    }

    public <R> RelationsConfigurationBuilder<T> addRelation(RelationType type, Class<R> relationClass, Consumer<RelationConfigurationBuilder<R, T>> consumer) {
        RelationConfigurationBuilder<R, T> builder = new RelationConfigurationBuilder<>(relationClass, clazz, type);
        if (consumer != null) {
            consumer.accept(builder);
        }
        relations.add(builder.build());
        return this;
    }

    public RelationsConfigurationBuilder<T> hasMany(Class<?> relationClass) {
        return addRelation(RelationType.HAS_MANY, relationClass, null);
    }

    public RelationsConfigurationBuilder<T> hasOne(Class<?> relationClass) {
        return addRelation(RelationType.HAS_ONE, relationClass, null);
    }

    public RelationsConfigurationBuilder<T> belongsTo(Class<?> relationClass) {
        return addRelation(RelationType.BELONGS_TO, relationClass, null);
    }

    public <R> RelationsConfigurationBuilder<T> hasMany(Class<R> relationClass, Consumer<RelationConfigurationBuilder<R, T>> consumer) {
        return addRelation(RelationType.HAS_MANY, relationClass, consumer);
    }

    public <R> RelationsConfigurationBuilder<T> hasOne(Class<R> relationClass, Consumer<RelationConfigurationBuilder<R, T>> consumer) {
        return addRelation(RelationType.HAS_ONE, relationClass, consumer);
    }

    public <R> RelationsConfigurationBuilder<T> belongsTo(Class<R> relationClass, Consumer<RelationConfigurationBuilder<R, T>> consumer) {
        return addRelation(RelationType.BELONGS_TO, relationClass, consumer);
    }

    @Override
    public List<RelationConfiguration<?, T>> build() {
        return relations;
    }
}
