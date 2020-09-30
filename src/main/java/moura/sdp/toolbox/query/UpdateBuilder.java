package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.StatementResult;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlTokenType;
import moura.sdp.toolbox.query.sql.SqlInsertValuesImpl;
import moura.sdp.toolbox.query.sql.SqlTokenImpl;
import moura.sdp.toolbox.query.sql.SqlUpdateImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UpdateBuilder implements WhereConfigurer<UpdateBuilder> {

    private SqlToken table;
    private final List<SqlToken> fields = new ArrayList<>();
    private final List<SqlToken> params = new ArrayList<>();
    private final Dialect dialect;
    private final WhereConfigurerImpl<UpdateBuilder> whereBuilder;
    private Database database;

    UpdateBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.whereBuilder = new WhereConfigurerImpl<>(this);
    }

    public Statement create() {
        SqlInsertValuesImpl values = new SqlInsertValuesImpl();
        values.setFields(fields);
        values.setParams(params);
        SqlUpdateImpl update = new SqlUpdateImpl();
        update.setValues(values);
        update.setTable(table);
        update.setWhere(whereBuilder.build());
        MutableStatement statement = dialect.makeUpdate(update);
        statement.setDatabase(database);
        return statement;
    }

    public UpdateBuilder database(Database database) {
        this.database = database;
        return this;
    }

    public UpdateBuilder withTable(String table) {
        SqlTokenImpl token = new SqlTokenImpl();
        token.setString(table);
        token.setType(SqlTokenType.TABLE);
        this.table = token;
        return this;
    }

    public UpdateBuilder withValue(String column, Object value) {
        fields.add(new SqlTokenImpl(column, SqlTokenType.FIELD));
        params.add(new SqlTokenImpl(column, SqlTokenType.PARAM, Collections.singletonList(value)));
        return this;
    }

    public UpdateBuilder withValues(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            withValue(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public UpdateBuilder where() {
        return whereBuilder.where();
    }

    @Override
    public UpdateBuilder where(String field) {
        return whereBuilder.where(field);
    }

    @Override
    public UpdateBuilder and(String field) {
        return whereBuilder.and(field);
    }

    @Override
    public UpdateBuilder or(String field) {
        return whereBuilder.or(field);
    }

    @Override
    public UpdateBuilder is(Object value) {
        return whereBuilder.is(value);
    }

    @Override
    public UpdateBuilder isNot(Object value) {
        return whereBuilder.isNot(value);
    }

    @Override
    public UpdateBuilder greaterThan(Object value) {
        return whereBuilder.greaterThan(value);
    }

    @Override
    public UpdateBuilder lowerThan(Object value) {
        return whereBuilder.lowerThan(value);
    }

    @Override
    public UpdateBuilder greaterThanOrEqual(Object value) {
        return whereBuilder.greaterThanOrEqual(value);
    }

    @Override
    public UpdateBuilder lowerThanOrEqual(Object value) {
        return whereBuilder.lowerThanOrEqual(value);
    }

    @Override
    public UpdateBuilder isNull() {
        return whereBuilder.isNull();
    }

    @Override
    public UpdateBuilder isNotNull() {
        return whereBuilder.isNotNull();
    }

    @Override
    public UpdateBuilder in(Object... params) {
        return whereBuilder.in(params);
    }

    @Override
    public UpdateBuilder in(List<Object> params) {
        return whereBuilder.in(params);
    }

    @Override
    public UpdateBuilder notIn(List<Object> params) {
        return whereBuilder.notIn(params);
    }

    public StatementResult run() {
        return create().execute();
    }
}
