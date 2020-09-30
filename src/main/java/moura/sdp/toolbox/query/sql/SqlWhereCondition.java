package moura.sdp.toolbox.query.sql;

public interface SqlWhereCondition {

    SqlWhereConditionConnector getConnector();

    SqlWhereCondition getNext();

}
