package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.common.DynamicAccessor;
import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.query.QueryBuilder;
import moura.sdp.toolbox.query.RelationToFetch;
import moura.sdp.toolbox.query.TypedQueryBuilder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface EntityConfiguration<T> extends DynamicAccessor<T> {

    String getTableName();

    String getIdColumn();

    Class<T> getEntityClass();

    Map<String, Object> getOwnAttributes(T entity, ConverterContext context);

    Map<String, Object> getDBColumnValues(T entity, ConverterContext context);

    T modelToEntity(Model model, ConverterContext context);

    boolean isNew(T entity, ConverterContext context);

    void buildQuery(TypedQueryBuilder<T> builder);

    List<RelationConfiguration<?, T>> getRelations();

    <R> RelationConfiguration<R, T> getRelation(Class<R> relationClass);

    RelationConfiguration<?, T> getRelation(String name);

    Optional<T> find(Database database, Object id);

    T save(Database database, T object);

    void delete(Database database, T object);

    void deleteById(Database database, Object id);

    void load(Database database, List<T> entities, List<RelationToFetch<?, T>> relationsToFetch);
}
