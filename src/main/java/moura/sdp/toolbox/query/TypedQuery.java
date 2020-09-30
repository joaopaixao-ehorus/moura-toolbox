package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;

import java.util.List;
import java.util.Set;

public class TypedQuery<T> implements Query {

    private final Class<T> resultClass;
    private final List<RelationToFetch<?, T>> relations;
    private final Query query;
    private final Database database;

    TypedQuery(Class<T> resultClass, List<RelationToFetch<?, T>> relations, Query query, Database database) {
        this.resultClass = resultClass;
        this.relations = relations;
        this.query = query;
        this.database = database;
    }

    public List<RelationToFetch<?, T>> getNext() {
        return relations;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> List<R> all(Class<R> clazz) {
        if (resultClass.equals(clazz)) {
            return (List<R>) all();
        }
        return database.getQueryResults(this, clazz);
    }

    public List<T> all() {
        return database.getTypedQueryResults(this, resultClass);
    }

    @Override
    public String getString() {
        return query.getString();
    }

    @Override
    public List<Object> getParams() {
        return query.getParams();
    }

    @Override
    public Set<Object> getHints() {
        return query.getHints();
    }
}
