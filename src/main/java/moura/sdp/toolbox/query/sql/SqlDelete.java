package moura.sdp.toolbox.query.sql;

public interface SqlDelete {

    SqlToken getTable();

    SqlWhereCondition getWhere();
}
