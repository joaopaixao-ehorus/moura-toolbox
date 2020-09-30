package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlInsert;
import moura.sdp.toolbox.query.sql.SqlInsertValues;
import moura.sdp.toolbox.query.sql.SqlToken;

public class SqlInsertImpl implements SqlInsert {

    private SqlToken table;
    private SqlInsertValues values;

    @Override
    public SqlToken getTable() {
        return table;
    }

    public void setTable(SqlToken table) {
        this.table = table;
    }

    @Override
    public SqlInsertValues getValues() {
        return values;
    }

    public void setValues(SqlInsertValues values) {
        this.values = values;
    }
}
