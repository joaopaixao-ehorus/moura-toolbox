package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.query.RelationToFetch;
import moura.sdp.toolbox.query.TypedQueryBuilder;

import java.util.List;

public interface RelationConfiguration<T, E> {

    static <R> RelationsConfigurationBuilder<R> buildList(Class<R> clazz) {
        return new RelationsConfigurationBuilder<>(clazz);
    }

    void load(Database database, List<E> entities, EntityConfiguration<E> entityConfig, RelationToFetch<T, E> relationToFetch);

    String getPKColumn();

    String getFKColumn();

    void buildQuery(TypedQueryBuilder<T> builder);

    Class<T> getRelationClass();

    RelationType getType();

    String getName();

}
