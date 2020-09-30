package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlInsertValues;
import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlUpdate;
import moura.sdp.toolbox.query.sql.SqlWhereCondition;

public class SqlUpdateImpl implements SqlUpdate {

    private SqlInsertValues values;
    private SqlToken table;
    private SqlWhereCondition where;

    @Override
    public SqlInsertValues getValues() {
        return values;
    }

    public void setValues(SqlInsertValues values) {
        this.values = values;
    }

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
