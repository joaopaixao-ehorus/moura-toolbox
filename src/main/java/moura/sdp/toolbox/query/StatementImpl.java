package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;
import moura.sdp.toolbox.orm.StatementResult;

import java.util.List;

public class StatementImpl implements MutableStatement {

    private String sql;
    private List<Object> params;
    private Database database;
    private StatementType type;

    @Override
    public String getString() {
        return sql;
    }

    @Override
    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public List<Object> getParams() {
        return params;
    }

    @Override
    public void setParams(List<Object> params) {
        this.params = params;
    }

    @Override
    public StatementType getType() {
        return type;
    }

    @Override
    public void setType(StatementType type) {
        this.type = type;
    }

    public Database getDatabase() {
        return database;
    }

    @Override
    public void setDatabase(Database database) {
        this.database = database;
    }

    @Override
    public StatementResult execute() {
        return database.execute(this);
    }
}
