package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Builder;
import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.StatementResult;
import moura.sdp.toolbox.query.dialect.Dialect;
import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlTokenType;
import moura.sdp.toolbox.query.sql.SqlDeleteImpl;
import moura.sdp.toolbox.query.sql.SqlTokenImpl;

import java.util.List;

public class DeleteBuilder implements WhereConfigurer<DeleteBuilder>, Builder<Statement> {

    private SqlToken table;
    private Database database;
    private final WhereConfigurerImpl<DeleteBuilder> whereBuilder;
    private final Dialect dialect;

    public DeleteBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.whereBuilder = new WhereConfigurerImpl<>(this);
    }

    public Statement build() {
        SqlDeleteImpl delete = new SqlDeleteImpl();
        delete.setTable(table);
        delete.setWhere(whereBuilder.build());
        MutableStatement statement = dialect.makeDelete(delete);
        statement.setDatabase(database);
        return statement;
    }

    public DeleteBuilder database(Database database) {
        this.database = database;
        return this;
    }

    public DeleteBuilder table(String table) {
        SqlTokenImpl token = new SqlTokenImpl();
        token.setString(table);
        token.setType(SqlTokenType.TABLE);
        this.table = token;
        return this;
    }

    @Override
    public DeleteBuilder where() {
        return whereBuilder.where();
    }

    @Override
    public DeleteBuilder where(String field) {
        return whereBuilder.where(field);
    }

    @Override
    public DeleteBuilder and(String field) {
        return whereBuilder.and(field);
    }

    @Override
    public DeleteBuilder or(String field) {
        return whereBuilder.or(field);
    }

    @Override
    public DeleteBuilder is(Object value) {
        return whereBuilder.is(value);
    }

    @Override
    public DeleteBuilder isNot(Object value) {
        return whereBuilder.isNot(value);
    }

    @Override
    public DeleteBuilder greaterThan(Object value) {
        return whereBuilder.greaterThan(value);
    }

    @Override
    public DeleteBuilder lowerThan(Object value) {
        return whereBuilder.lowerThan(value);
    }

    @Override
    public DeleteBuilder greaterThanOrEqual(Object value) {
        return whereBuilder.greaterThanOrEqual(value);
    }

    @Override
    public DeleteBuilder lowerThanOrEqual(Object value) {
        return whereBuilder.lowerThanOrEqual(value);
    }

    @Override
    public DeleteBuilder isNull() {
        return whereBuilder.isNull();
    }

    @Override
    public DeleteBuilder isNotNull() {
        return whereBuilder.isNotNull();
    }

    @Override
    public DeleteBuilder in(Object... params) {
        return whereBuilder.in(params);
    }

    @Override
    public DeleteBuilder in(List<Object> params) {
        return whereBuilder.in(params);
    }

    @Override
    public DeleteBuilder notIn(List<Object> params) {
        return whereBuilder.notIn(params);
    }

    public StatementResult run() {
        return build().execute();
    }
}
