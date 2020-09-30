package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Builder;
import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.Model;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.dialect.MysqlDialect;
import moura.sdp.toolbox.query.sql.*;

import java.util.*;
import java.util.function.Function;

public class QueryBuilder implements Builder<Query>, WhereConfigurer<QueryBuilder> {

    private final Dialect dialect;
    private final WhereConfigurerImpl<QueryBuilder> whereBuilder;

    private Database database;
    private SqlSelectImpl select;
    private SqlFromImpl from;
    private SqlWhereConditionImpl having;
    private SqlOrderByImpl orderBy;
    private SqlGroupByImpl groupBy;
    private Object lastToPutAlias;
    private SqlToken limit;

    public static void main(String[] args) {
        Query query = new QueryBuilder(new MysqlDialect())
                .select("nome", "idade", "cpf")
                .from("pessoa")
                .where("idade").greaterThanOrEqual(10)
                .and("id").in(123, 34, 45)
                .build();
        System.out.println(query.getString());
        System.out.println(query.getParams());
    }

    private static <T> void replaceLast(List<T> list, Function<T, T> function) {
        if (list.isEmpty()) {
            list.add(function.apply(null));
        }
        list.set(list.size() - 1, function.apply(list.get(list.size() - 1)));
    }

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.whereBuilder = new WhereConfigurerImpl<>(this);
    }

    public <T> Optional<T> get(Class<T> resultClass) {
        List<T> all = limit(1).build().all(resultClass);
        if (all.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(all.get(0));
    }

    public Optional<Model> get() {
        return get(Model.class);
    }

    public <T> List<T> all(Class<T> resultClass) {
        return build().all(resultClass);
    }

    public List<Model> all() {
        return all(Model.class);
    }

    public Query build() {
        SqlPiecesImpl pieces = new SqlPiecesImpl();
        pieces.setSelect(select);
        pieces.setFrom(from);
        pieces.setWhere(whereBuilder.build());
        pieces.setHaving(having);
        pieces.setGroupBy(groupBy);
        pieces.setOrderBy(orderBy);
        MutableQuery query = dialect.makeQuery(pieces);
        query.setDatabase(database);
        return query;
    }

    public QueryBuilder database(Database database) {
        this.database = database;
        return this;
    }

    public QueryBuilder selectAll() {
        return selectAll(new SqlSelectImpl());
    }

    public QueryBuilder appendSelectAll() {
        return selectAll(select != null ? select : new SqlSelectImpl());
    }

    private QueryBuilder selectAll(SqlSelectImpl select) {
        SqlSelectFieldImpl field = new SqlSelectFieldImpl();
        field.setType(SqlTokenType.ALL_FIELDS);
        select.addField(field);
        this.select = select;
        return this;
    }

    public QueryBuilder select(String fieldName) {
        return select(new SqlSelectImpl(), fieldName);
    }

    public QueryBuilder appendSelect(String fieldName) {
        return select(select != null ? select : new SqlSelectImpl(), fieldName);
    }

    private QueryBuilder select(SqlSelectImpl select, String fieldName) {
        SqlSelectFieldImpl field = new SqlSelectFieldImpl();
        field.setString(fieldName);
        field.setType(SqlTokenType.FIELD);
        select.addField(field);
        this.select = select;
        lastToPutAlias = field;
        return this;
    }

    public QueryBuilder select(String ...fieldNames) {
        return select(new SqlSelectImpl(), fieldNames);
    }

    public QueryBuilder appendSelect(String ...fieldNames) {
        return select(select != null ? select : new SqlSelectImpl(), fieldNames);
    }

    private QueryBuilder select(SqlSelectImpl select, String ...fieldNames) {
        List<SqlSelectField> fields = new ArrayList<>();
        for (String name : fieldNames) {
            SqlSelectFieldImpl field = new SqlSelectFieldImpl();
            field.setString(name);
            field.setType(SqlTokenType.FIELD);
            fields.add(field);
        }
        select.addFields(fields);
        this.select = select;
        return this;
    }

    public QueryBuilder selectRaw(String fieldName, Object ...params) {
        return selectRaw(new SqlSelectImpl(), fieldName, params);
    }

    public QueryBuilder appendSelectRaw(String fieldName, Object ...params) {
        return selectRaw(select != null ? select : new SqlSelectImpl(), fieldName, params);
    }

    private QueryBuilder selectRaw(SqlSelectImpl select, String fieldName, Object ...params) {
        SqlSelectFieldImpl field = new SqlSelectFieldImpl();
        field.setString(fieldName);
        field.setParams(Arrays.asList(params));
        field.setType(SqlTokenType.RAW);
        select.addField(field);
        this.select = select;
        lastToPutAlias = field;
        return this;
    }

    public QueryBuilder as(String alias) {
        if (lastToPutAlias instanceof SqlSelectFieldImpl) {
            SqlSelectFieldImpl field = (SqlSelectFieldImpl) lastToPutAlias;
            field.setAlias(alias);
        } else if (lastToPutAlias instanceof SqlFromImpl) {
            SqlFromImpl from = (SqlFromImpl) lastToPutAlias;
            from.setAlias(alias);
        }
        return this;
    }

    public QueryBuilder from(Object source) {
        SqlFromImpl from = initFrom();
        from.setSource(source);
        lastToPutAlias = from;
        return this;
    }

    public QueryBuilder fromRaw(String string, List<Object> params) {
        SqlFromImpl from = initFrom();
        from.setSource(string);
        from.setParams(params);
        lastToPutAlias = from;
        return this;
    }

    @Override
    public QueryBuilder where() {
        return whereBuilder.where();
    }

    @Override
    public QueryBuilder where(String field) {
        return whereBuilder.where(field);
    }

    @Override
    public QueryBuilder and(String field) {
        return whereBuilder.and(field);
    }

    @Override
    public QueryBuilder or(String field) {
        return whereBuilder.or(field);
    }

    @Override
    public QueryBuilder is(Object value) {
        return whereBuilder.is(value);
    }

    @Override
    public QueryBuilder isNot(Object value) {
        return whereBuilder.isNot(value);
    }

    @Override
    public QueryBuilder greaterThan(Object value) {
        return whereBuilder.greaterThan(value);
    }

    @Override
    public QueryBuilder lowerThan(Object value) {
        return whereBuilder.lowerThan(value);
    }

    @Override
    public QueryBuilder greaterThanOrEqual(Object value) {
        return whereBuilder.greaterThanOrEqual(value);
    }

    @Override
    public QueryBuilder lowerThanOrEqual(Object value) {
        return whereBuilder.lowerThanOrEqual(value);
    }

    @Override
    public QueryBuilder isNull() {
        return whereBuilder.isNull();
    }

    @Override
    public QueryBuilder isNotNull() {
        return whereBuilder.isNotNull();
    }

    @Override
    public QueryBuilder in(Object... params) {
        return whereBuilder.in(params);
    }

    @Override
    public QueryBuilder in(List<Object> params) {
        return whereBuilder.in(params);
    }

    @Override
    public QueryBuilder notIn(List<Object> params) {
        return whereBuilder.notIn(params);
    }

    public QueryBuilder limit(int limit) {
        SqlTokenImpl token = new SqlTokenImpl();
        token.setType(SqlTokenType.RAW);
        token.setString(String.valueOf(limit));
        this.limit = token;
        return this;
    }

    private SqlFromImpl initFrom() {
        if (from == null) {
            from = new SqlFromImpl();
        }
        return from;
    }

    Database getDatabase() {
        return database;
    }

}
