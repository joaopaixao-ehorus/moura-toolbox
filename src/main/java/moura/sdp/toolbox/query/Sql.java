package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.EntityRegistry;
import moura.sdp.toolbox.query.dialect.Dialect;

public class Sql {

    private Dialect dialect;

    public Sql withDialect(Dialect dialect) {
        this.dialect = dialect;
        return this;
    }

    public QueryBuilder newQuery() {
        return new QueryBuilder(dialect);
    }

    public <T> TypedQueryBuilder<T> newQuery(EntityRegistry registry, Class<T> resultClass) {
        return new TypedQueryBuilder<T>(resultClass, registry, newQuery());
    }

    public InsertBuilder newInsert() {
        return new InsertBuilder(dialect);
    }

    public UpdateBuilder newUpdate() {
        return new UpdateBuilder(dialect);
    }

    public DeleteBuilder newDelete() {
        return new DeleteBuilder(dialect);
    }
}
