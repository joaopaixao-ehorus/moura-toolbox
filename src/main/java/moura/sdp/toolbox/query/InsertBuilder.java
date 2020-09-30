package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Builder;
import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.StatementResult;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlTokenType;
import moura.sdp.toolbox.query.sql.SqlInsertImpl;
import moura.sdp.toolbox.query.sql.SqlInsertValuesImpl;
import moura.sdp.toolbox.query.sql.SqlTokenImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("FieldMayBeFinal")
public class InsertBuilder implements WhereConfigurer<InsertBuilder>, Builder<Statement> {

    private SqlToken table;
    private List<SqlToken> fields = new ArrayList<>();
    private List<SqlToken> params = new ArrayList<>();
    private Dialect dialect;
    private Database database;

    public InsertBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public InsertBuilder table(String table) {
        SqlTokenImpl token = new SqlTokenImpl();
        token.setString(table);
        token.setType(SqlTokenType.TABLE);
        this.table = token;
        return this;
    }

    public InsertBuilder database(Database database) {
        this.database = database;
        return this;
    }

    public InsertBuilder withValue(String column, Object value) {
        fields.add(new SqlTokenImpl(column, SqlTokenType.FIELD));
        params.add(new SqlTokenImpl(column, SqlTokenType.PARAM, Collections.singletonList(value)));
        return this;
    }

    public InsertBuilder withValues(Map<String, Object> attributes) {
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            withValue(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public Statement build() {
        SqlInsertValuesImpl values = new SqlInsertValuesImpl();
        values.setFields(fields);
        values.setParams(params);
        SqlInsertImpl insert = new SqlInsertImpl();
        insert.setTable(table);
        insert.setValues(values);
        MutableStatement statement = dialect.makeInsert(insert);
        statement.setDatabase(database);
        return statement;
    }

    @Override
    public InsertBuilder where() {
        return this;
    }

    @Override
    public InsertBuilder where(String field) {
        return this;
    }

    @Override
    public InsertBuilder and(String field) {
        return this;
    }

    @Override
    public InsertBuilder or(String field) {
        return this;
    }

    @Override
    public InsertBuilder is(Object value) {
        return this;
    }

    @Override
    public InsertBuilder isNot(Object value) {
        return this;
    }

    @Override
    public InsertBuilder greaterThan(Object value) {
        return this;
    }

    @Override
    public InsertBuilder lowerThan(Object value) {
        return this;
    }

    @Override
    public InsertBuilder greaterThanOrEqual(Object value) {
        return this;
    }

    @Override
    public InsertBuilder lowerThanOrEqual(Object value) {
        return this;
    }

    @Override
    public InsertBuilder isNull() {
        return this;
    }

    @Override
    public InsertBuilder isNotNull() {
        return null;
    }

    @Override
    public InsertBuilder in(Object... params) {
        return null;
    }

    @Override
    public InsertBuilder in(List<Object> params) {
        return null;
    }

    @Override
    public InsertBuilder notIn(List<Object> params) {
        return null;
    }

    public StatementResult run() {
        return build().execute();
    }
}
