package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlDelete;
import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlWhereCondition;

public class SqlDeleteImpl implements SqlDelete {

    private SqlToken table;
    private SqlWhereCondition where;

    @Override
    public SqlToken getTable() {
        return table;
    }

    public void setTable(SqlToken table) {
        this.table = table;
    }

    @Override
    public SqlWhereCondition getWhere() {
        return where;
    }

    public void setWhere(SqlWhereCondition where) {
        this.where = where;
    }
}
