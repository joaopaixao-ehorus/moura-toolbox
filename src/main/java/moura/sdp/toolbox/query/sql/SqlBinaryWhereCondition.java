package moura.sdp.toolbox.query.sql;

public interface SqlBinaryWhereCondition extends SqlWhereCondition {

    SqlToken getLeftValue();

    SqlToken getRightValue();

    SqlBinaryOperator getOperator();
}
