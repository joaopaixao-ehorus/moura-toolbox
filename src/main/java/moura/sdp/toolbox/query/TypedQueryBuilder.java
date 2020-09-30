package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.EntityRegistry;
import moura.sdp.toolbox.orm.RelationConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class TypedQueryBuilder<T> implements ConditionBuilder<TypedQueryBuilder<T>>, RelationFetchConfigurer<TypedQueryBuilder<T>> {

    private final Class<T> resultClass;
    private final EntityRegistry registry;
    private final QueryBuilder queryBuilder;
    private final RelationFetchBuilder<T> relationFetchBuilder;

    TypedQueryBuilder(Class<T> resultClass, EntityRegistry registry, QueryBuilder queryBuilder) {
        this.resultClass = resultClass;
        this.registry = registry;
        this.queryBuilder = queryBuilder.selectAll().from(registry.getConfig(resultClass).getTableName());
        this.relationFetchBuilder = new RelationFetchBuilderImpl<>(resultClass, registry);
    }

    public Optional<T> get() {
        return get(resultClass);
    }

    public List<T> all() {
        return done().all();
    }

    public <R> Optional<R> get(Class<R> resultClass) {
        queryBuilder.limit(1);
        List<R> list = done().all(resultClass);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(list.get(0));
    }

    public <R> List<R> all(Class<R> resultClass) {
        return done().all(resultClass);
    }

    public TypedQuery<T> done() {
        return new TypedQuery<>(resultClass, relationFetchBuilder.build(), queryBuilder.build(), queryBuilder.getDatabase());
    }

    public TypedQueryBuilder<T> database(Database database) {
        queryBuilder.database(database);
        return this;
    }

    public TypedQueryBuilder<T> selectAll() {
        queryBuilder.selectAll();
        return this;
    }

    public TypedQueryBuilder<T> select(String fieldName) {
        queryBuilder.select(fieldName);
        return this;
    }

    public TypedQueryBuilder<T> select(String... fieldNames) {
        queryBuilder.select(fieldNames);
        return this;
    }

    public TypedQueryBuilder<T> selectRaw(String fieldName, Object... params) {
        queryBuilder.selectRaw(fieldName, params);
        return this;
    }

    public TypedQueryBuilder<T> as(String alias) {
        queryBuilder.as(alias);
        return this;
    }

    public TypedQueryBuilder<T> where() {
        queryBuilder.where();
        return this;
    }

    public TypedQueryBuilder<T> where(String field) {
        queryBuilder.where(field);
        return this;
    }

    public TypedQueryBuilder<T> and(String field) {
        queryBuilder.and(field);
        return this;
    }

    public TypedQueryBuilder<T> or(String field) {
        queryBuilder.or(field);
        return this;
    }

    public TypedQueryBuilder<T> is(Object value) {
        queryBuilder.is(value);
        return this;
    }

    public TypedQueryBuilder<T> isNot(Object value) {
        queryBuilder.isNot(value);
        return this;
    }

    public TypedQueryBuilder<T> greaterThan(Object value) {
        queryBuilder.greaterThan(value);
        return this;
    }

    public TypedQueryBuilder<T> lowerThan(Object value) {
        queryBuilder.lowerThan(value);
        return this;
    }

    public TypedQueryBuilder<T> greaterThanOrEqual(Object value) {
        queryBuilder.greaterThanOrEqual(value);
        return this;
    }

    public TypedQueryBuilder<T> lowerThanOrEqual(Object value) {
        queryBuilder.lowerThanOrEqual(value);
        return this;
    }

    public TypedQueryBuilder<T> isNull() {
        queryBuilder.isNull();
        return this;
    }

    public TypedQueryBuilder<T> isNotNull() {
        queryBuilder.isNotNull();
        return this;
    }

    public TypedQueryBuilder<T> in(Object... params) {
        queryBuilder.in(params);
        return this;
    }

    public TypedQueryBuilder<T> in(List<Object> params) {
        queryBuilder.in(params);
        return this;
    }

    public TypedQueryBuilder<T> notIn(List<Object> params) {
        queryBuilder.notIn(params);
        return this;
    }

    public TypedQueryBuilder<T> from(String tableName) {
        queryBuilder.from(tableName);
        return this;
    }

    @Override
    public TypedQueryBuilder<T> with(String relation) {
        return with(relation, null);
    }

    @Override
    public <R> TypedQueryBuilder<T> with(String relation, Consumer<TypedQueryBuilder<R>> builder) {
        relationFetchBuilder.with(relation, builder);
        return this;
    }

    @Override
    public TypedQueryBuilder<T> with(Class<?> relationClass) {
        return with(relationClass, null);
    }

    @Override
    public <R> TypedQueryBuilder<T> with(Class<R> relationClass, Consumer<TypedQueryBuilder<R>> customizer) {
        relationFetchBuilder.with(relationClass, customizer);
        return this;
    }
}
