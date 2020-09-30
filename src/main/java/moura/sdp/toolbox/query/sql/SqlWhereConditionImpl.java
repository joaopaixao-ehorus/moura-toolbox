package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlWhereCondition;
import moura.sdp.toolbox.query.sql.SqlWhereConditionConnector;

public abstract class SqlWhereConditionImpl implements SqlWhereCondition {

    private SqlWhereConditionConnector connector;
    private SqlWhereCondition next;

    @Override
    public SqlWhereConditionConnector getConnector() {
        return connector;
    }

    public void setConnector(SqlWhereConditionConnector connector) {
        this.connector = connector;
    }

    @Override
    public SqlWhereCondition getNext() {
        return next;
    }

    public void setNext(SqlWhereCondition next) {
        this.next = next;
    }

}
