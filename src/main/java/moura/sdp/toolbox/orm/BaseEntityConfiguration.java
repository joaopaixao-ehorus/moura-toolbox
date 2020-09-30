package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.converter.MutableConverterImpl;
import moura.sdp.toolbox.exception.AttributeNotFoundException;
import moura.sdp.toolbox.query.RelationToFetch;
import moura.sdp.toolbox.query.TypedQueryBuilder;
import moura.sdp.toolbox.query.exception.QueryConstructionException;
import moura.sdp.toolbox.utils.ReflectionUtils;
import moura.sdp.toolbox.utils.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.util.*;

public abstract class BaseEntityConfiguration<T> implements EntityConfiguration<T> {

    private final Class<T> clazz;
    private final String idColumn;
    private final String tableName;

    @SuppressWarnings("unchecked")
    public BaseEntityConfiguration() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        idColumn = "id";
        tableName = StringUtils.snakeCase(getEntityClass().getSimpleName());
    }

    @Override
    public boolean isNew(T entity, ConverterContext context) {
        return getAttribute(entity, getIdColumn(), context) == null;
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Override
    public Map<String, Object> getAttributes(T entity, ConverterContext context) {
        return ReflectionUtils.getAttributes(entity);
    }

    @Override
    public Map<String, Object> getOwnAttributes(T entity, ConverterContext context) {
        Map<String, Object> attributes = getAttributes(entity, context);
        for (RelationConfiguration<?, T> relation : getRelations()) {
            attributes.remove(relation.getName());
        }
        return attributes;
    }

    @Override
    public Map<String, Object> getDBColumnValues(T entity, ConverterContext context) {
        Map<String, Object> attributes = getOwnAttributes(entity, context);
        for (String key : attributes.keySet()) {
            if (attributes.get(key) != null) {
                attributes.put(key, attributes.get(key).toString());
            }
        }
        return attributes;
    }

    @Override
    public Object getAttribute(T entity, String attribute, ConverterContext context) {
        return ReflectionUtils.get(entity, attribute);
    }

    @Override
    public void setAttribute(T entity, String attribute, Object value, ConverterContext context) {
        ReflectionUtils.set(entity, attribute, value, context);
    }

    @Override
    public void setAttributes(T entity, Map<String, Object> attributes, ConverterContext context) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            try {
                setAttribute(entity, entry.getKey(), entry.getValue(), context);
            } catch (AttributeNotFoundException ignored) {
            }
        }
    }

    @Override
    public T modelToEntity(Model model, ConverterContext context) {
        try {
            T entity = getEntityClass().newInstance();
            setAttributes(entity, model.getAll(), context);
            return entity;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void buildQuery(TypedQueryBuilder<T> builder) {
        builder.selectAll().from(getTableName());
    }

    @Override
    public String getTableName() {
        return tableName;
    }

    @Override
    public String getIdColumn() {
        return idColumn;
    }

    @Override
    public List<RelationConfiguration<?, T>> getRelations() {
        return Collections.emptyList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <R> RelationConfiguration<R, T> getRelation(Class<R> relationClass) {
        for (RelationConfiguration<?, T> relation : getRelations()) {
            if (relation.getRelationClass().equals(relationClass)) {
                return (RelationConfiguration<R, T>) relation;
            }
        }
        throw new QueryConstructionException(getEntityClass() + " has no relation with " + relationClass);
    }

    @Override
    public RelationConfiguration<?, T> getRelation(String name) {
        for (RelationConfiguration<?, T> relation : getRelations()) {
            if (relation.getName().equals(name)) {
                return relation;
            }
        }
        throw new QueryConstructionException(getEntityClass() + " has no relation named " + name);
    }

    @Override
    public Optional<T> find(Database database, Object id) {
        TypedQueryBuilder<T> queryBuilder = database.from(getEntityClass());
        buildQuery(queryBuilder);
        queryBuilder.where(getIdColumn()).is(id);
        return queryBuilder.get();
    }

    @Override
    public T save(Database database, T object) {
        MutableConverterImpl converterContext = new MutableConverterImpl();
        converterContext.setConverter(database.getConverter());
        if (isNew(object, converterContext)) {
            Map<String, Object> attributes = getDBColumnValues(object, converterContext);
            attributes.remove(getIdColumn());
            StatementResult result = database
                    .insert()
                    .table(getTableName())
                    .withValues(attributes)
                    .run();
            setAttribute(object, getIdColumn(), result.getGeneratedKeys().get(0), converterContext);
        } else {
            Map<String, Object> attributes = getDBColumnValues(object, converterContext);
            database.update()
                    .withValues(attributes)
                    .withTable(getTableName())
                    .where(getIdColumn()).is(getAttribute(object, getIdColumn(), converterContext))
                    .run();
        }
        return object;
    }

    @Override
    public void delete(Database database, T object) {
        Object id = getAttribute(object, getIdColumn(), ConverterContext.of(database.getConverter()));
        database.delete().table(getTableName()).where(getIdColumn()).is(id).run();
    }

    @Override
    public void deleteById(Database database, Object id) {
        database.delete().table(getTableName()).where(getIdColumn()).is(id).run();
    }

    @Override
    public void load(Database database, List<T> entities, List<RelationToFetch<?, T>> relationsToFetch) {
        if (relationsToFetch == null || relationsToFetch.isEmpty()) {
            return;
        }
        for (RelationToFetch<?, T> relationToFetch : relationsToFetch) {
            relationToFetch.load(database, entities, this);
        }
    }

}
