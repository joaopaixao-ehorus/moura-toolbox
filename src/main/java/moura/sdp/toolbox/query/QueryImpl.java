package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;

import java.util.List;
import java.util.Set;

public class QueryImpl implements MutableQuery {

    private Database database;
    private String string;
    private List<Object> params;
    private Set<Object> hints;

    @Override
    public Set<Object> getHints() {
        return hints;
    }

    @Override
    public void setHint(Set<Object> hints) {
        this.hints = hints;
    }

    @Override
    public <T> List<T> all(Class<T> clazz) {
        return database.getQueryResults(this, clazz);
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void setSql(String sql) {
        this.string = sql;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public List<Object> getParams() {
        return params;
    }

    @Override
    public void setParams(List<Object> params) {
        this.params = params;
    }

}
