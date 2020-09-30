package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.common.Builder;
import moura.sdp.toolbox.query.TypedQueryBuilder;

import java.util.function.Consumer;

public class RelationConfigurationBuilder<T, E> implements Builder<RelationConfiguration<T, E>> {

    private MutableRelationConfiguration<T, E> config = new MutableRelationConfiguration<>();

    public RelationConfigurationBuilder(Class<T> relationClass, Class<E> entityClass, RelationType type) {
        config.setRelationClass(relationClass);
        config.setType(type);
        config.setEntityClass(entityClass);
    }

    public RelationConfigurationBuilder<T, E> withFkColumn(String fkColumn) {
        config.setFKColumn(fkColumn);
        return this;
    }

    public RelationConfigurationBuilder<T, E> withQueryEnhancer(Consumer<TypedQueryBuilder<T>> makeQuery) {
        config.setMakeQuery(makeQuery);
        return this;
    }

    public RelationConfigurationBuilder<T, E> withName(String name) {
        config.setName(name);
        return this;
    }

    public RelationConfigurationBuilder<T, E> withQueryEnhancer(RelationType type) {
        config.setType(type);
        return this;
    }

    public RelationConfigurationBuilder<T, E> withPkColumn(String pkColumn) {
        config.setPKColumn(pkColumn);
        return this;
    }

    public RelationConfiguration<T, E> build() {
        config.makeDefaults();
        RelationConfiguration<T, E> oldConfig = config;
        config = new MutableRelationConfiguration<>();
        return oldConfig;
    }
}
