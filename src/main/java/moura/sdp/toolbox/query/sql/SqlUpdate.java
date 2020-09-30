package moura.sdp.toolbox.query.sql;

public interface SqlUpdate {

    SqlToken getTable();

    SqlInsertValues getValues();

    SqlWhereCondition getWhere();
}
