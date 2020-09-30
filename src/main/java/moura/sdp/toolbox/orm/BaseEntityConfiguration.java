package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.Converter;
import moura.sdp.toolbox.converter.ConverterContext;
import moura.sdp.toolbox.converter.MutableConverterContext;
import moura.sdp.toolbox.exception.AttributeNotFoundException;
import moura.sdp.toolbox.query.*;
import moura.sdp.toolbox.query.exception.QueryConstructionException;
import moura.sdp.toolbox.utils.ReflectionUtils;
import moura.sdp.toolbox.utils.StringUtils;

import javax.management.relation.Relation;
import javax.xml.crypto.Data;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseEntityConfiguration<T> implements EntityConfiguration<T> {

    private Class<T> clazz;
    private String idColumn;
    private String tableName;
    private List<RelationConfiguration<?, T>> relations;

    @SuppressWarnings("unchecked")
    public BaseEntityConfiguration() {
        clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        idColumn = "id";
        tableName = StringUtils.snakeCase(getEntityClass().getSimpleName());
        configure();
    }

    protected void setEntityClass(Class<T> clazz) {
        this.clazz = clazz;
    }

    protected void setIdColumn(String idColumn) {
        this.idColumn = idColumn;
    }

    protected void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void addRelation(RelationConfiguration<?, T> relation) {
        if (relations == null) {
            relations = new ArrayList<>();
        }
        relations.add(relation);
    }


    protected void configure() {
    }

    public void addRelation(RelationConfigurationBuilder<?, T> builder) {
        addRelation(builder.done());
    };

    protected RelationConfigurationBuilder<?, T> hasMany(Class<?> relationClass) {
        return new RelationConfigurationBuilder<>(relationClass, getEntityClass(), RelationType.HAS_MANY);
    }

    protected RelationConfigurationBuilder<?, T> hasOne(Class<?> relationClass) {
        return new RelationConfigurationBuilder<>(relationClass, getEntityClass(), RelationType.HAS_ONE);
    }

    protected RelationConfigurationBuilder<?, T> belongsTo(Class<?> relationClass) {
        return new RelationConfigurationBuilder<>(relationClass, getEntityClass(), RelationType.BELONGS_TO);
    }

    @Override
    public boolean isNew(T entity) {
        return getAttribute(entity, getIdColumn()) == null;
    }

    @Override
    public Class<T> getEntityClass() {
        return clazz;
    }

    @Override
    public Map<String, Object> getAttributes(T entity) {
        Map<String, Object> attributes = ReflectionUtils.getAttributes(entity);
        attributes.remove("table_name");
        attributes.remove("id_column");
        attributes.remove("entity_class");
        attributes.remove("relations");
        return attributes;
    }

    @Override
    public Map<String, Object> getOwnAttributes(T entity) {
        Map<String, Object> attributes = getAttributes(entity);
        for (RelationConfiguration<?, T> relation : getRelations()) {
            attributes.remove(relation.getName());
        }
        return attributes;
    }

    @Override
    public Object getAttribute(T entity, String attribute) {
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
        return relations;
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
        MutableConverterContext converterContext = new MutableConverterContext();
        converterContext.setConverter(database.getConverter());
        if (isNew(object)) {
            Map<String, Object> attributes = getOwnAttributes(object);
            attributes.remove(getIdColumn());
            StatementResult result = database
                    .insert()
                    .table(getTableName())
                    .withValues(attributes)
                    .run();
            setAttribute(object, getIdColumn(), result.getGeneratedKeys().get(0), converterContext);
        } else {
            Map<String, Object> attributes = getOwnAttributes(object);
            database.update()
                    .withValues(attributes)
                    .withTable(getTableName())
                    .where(getIdColumn()).is(getAttribute(object, getIdColumn()))
                    .run();
        }
        return object;
    }

    @Override
    public void delete(Database database, T object) {
        Object id = getAttribute(object, getIdColumn());
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
