package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlWhereCondition;
import moura.sdp.toolbox.query.sql.SqlWhereConditionGroup;

public class SqlWhereConditionGroupImpl implements SqlWhereConditionGroup {

    private SqlWhereCondition root;

    @Override
    public SqlWhereCondition getRoot() {
        return root;
    }

    public void setRoot(SqlWhereCondition root) {
        this.root = root;
    }
}
