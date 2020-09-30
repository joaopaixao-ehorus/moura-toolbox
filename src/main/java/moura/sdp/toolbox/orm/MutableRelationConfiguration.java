package moura.sdp.toolbox.orm;

import moura.sdp.toolbox.converter.MutableConverterImpl;
import moura.sdp.toolbox.query.RelationToFetch;
import moura.sdp.toolbox.query.TypedQuery;
import moura.sdp.toolbox.query.TypedQueryBuilder;
import moura.sdp.toolbox.utils.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class MutableRelationConfiguration<T, E> implements RelationConfiguration<T, E> {

    private String pkColumn;
    private String fkColumn;
    private Consumer<TypedQueryBuilder<T>> makeQuery;
    private Class<E> entityClass;
    private Class<T> relationClass;
    private String name;
    private RelationType type;

    @Override
    public String getFKColumn() {
        return fkColumn;
    }

    @Override
    public void buildQuery(TypedQueryBuilder<T> builder) {
        if (makeQuery != null) {
            makeQuery.accept(builder);
        }
    }

    public void setFKColumn(String fkColumn) {
        this.fkColumn = fkColumn;
    }

    public void setMakeQuery(Consumer<TypedQueryBuilder<T>> makeQuery) {
        this.makeQuery = makeQuery;
    }

    @Override
    public Class<T> getRelationClass() {
        return relationClass;
    }

    public void setRelationClass(Class<T> relationClass) {
        this.relationClass = relationClass;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public RelationType getType() {
        return type;
    }

    public void setType(RelationType type) {
        this.type = type;
    }

    @Override
    public void load(Database database, List<E> entities, EntityConfiguration<E> entityConfig, RelationToFetch<T, E> relationToFetch) {
        EntityConfiguration<T> relationEntityConfig = database.getConfiguration(getRelationClass());
        String entityKey = getType().equals(RelationType.BELONGS_TO) ? getFKColumn() : entityConfig.getIdColumn();
        String relationKey = getType().equals(RelationType.BELONGS_TO) ? relationEntityConfig.getIdColumn() : getFKColumn();

        MutableConverterImpl context = new MutableConverterImpl();
        context.setConverter(database.getConverter());

        List<Object> ids = entities.stream().map(e -> entityConfig.getAttribute(e, entityKey, context)).collect(Collectors.toList());
        TypedQueryBuilder<T> builder = database.from(getRelationClass()).where(relationKey).in(ids);
        buildQuery(builder);
        relationToFetch.enhance(builder);

        TypedQuery<T> query = builder.done();
        List<T> relationEntities = query.all();

        context.setParams(query.getHints());

        Map<Object, E> indexed = new HashMap<>();

        for (E entity : entities) {
            if (getType().equals(RelationType.HAS_MANY)) {
                entityConfig.setAttribute(entity, getName(), new ArrayList<>(), context);
            }
            indexed.put(entityConfig.getAttribute(entity, entityKey, context), entity);
        }

        for (T relationEntity : relationEntities) {
            E entity = indexed.get(relationEntityConfig.getAttribute(relationEntity, relationKey, context));
            if (getType().equals(RelationType.HAS_MANY)) {
                @SuppressWarnings("unchecked")
                Collection<T> list = (Collection<T>) entityConfig.getAttribute(entity, getName(), context);
                list.add(relationEntity);
            } else {
                entityConfig.setAttribute(entity, getName(), relationEntity, context);
            }
        }

        relationEntityConfig.load(database, relationEntities, query.getNext());
    }

    @Override
    public String getPKColumn() {
        return pkColumn;
    }

    public void setPKColumn(String pkColumn) {
        this.pkColumn = pkColumn;
    }

    public void setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public void makeDefaults() {
        if (name == null) {
            name = relationClass.getSimpleName().substring(0, 1).toLowerCase() + relationClass.getSimpleName().substring(1);
            if (type.equals(RelationType.HAS_MANY)) {
                name += "s";
            }
        }
        if (fkColumn == null) {
            fkColumn = type.equals(RelationType.BELONGS_TO)
                    ? StringUtils.snakeCase(relationClass.getSimpleName()) + "_id"
                    : StringUtils.snakeCase(entityClass.getSimpleName()) + "_id" ;
        }
        if (pkColumn == null) {
            pkColumn = "id";
        }
    }
}
