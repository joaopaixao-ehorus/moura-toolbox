package moura.sdp.toolbox.query.sql;

public class SqlBinaryWhereConditionImpl extends SqlWhereConditionImpl implements SqlBinaryWhereCondition {

    private SqlToken leftValue;
    private SqlToken rightValue;
    private SqlBinaryOperator operator;

    @Override
    public SqlToken getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(SqlToken leftValue) {
        this.leftValue = leftValue;
    }

    @Override
    public SqlToken getRightValue() {
        return rightValue;
    }

    public void setRightValue(SqlToken rightValue) {
        this.rightValue = rightValue;
    }

    @Override
    public SqlBinaryOperator getOperator() {
        return operator;
    }

    public void setOperator(SqlBinaryOperator operator) {
        this.operator = operator;
    }
}
