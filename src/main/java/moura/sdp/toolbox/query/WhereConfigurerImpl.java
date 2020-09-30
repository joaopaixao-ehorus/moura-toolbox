package moura.sdp.toolbox.query;

import moura.sdp.toolbox.query.exception.QueryConstructionException;
import moura.sdp.toolbox.query.sql.SqlBinaryOperator;
import moura.sdp.toolbox.query.sql.SqlTokenType;
import moura.sdp.toolbox.query.sql.SqlWhereCondition;
import moura.sdp.toolbox.query.sql.SqlWhereConditionConnector;
import moura.sdp.toolbox.query.sql.SqlBinaryWhereConditionImpl;
import moura.sdp.toolbox.query.sql.SqlTokenImpl;
import moura.sdp.toolbox.query.sql.SqlWhereConditionImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WhereConfigurerImpl<T extends WhereConfigurer<T>> implements WhereConfigurer<T> {

    private SqlWhereConditionImpl condition;
    protected final T wrapped;
    protected SqlWhereConditionImpl lastWhereCondition;
    protected boolean lastWhereConditionIsIncomplete;

    public WhereConfigurerImpl(T wrapped) {
        this.wrapped = wrapped;
    }

    public T and(String field) {
        if (lastWhereCondition == null) {
            throw new QueryConstructionException("You have not started the where or having");
        } else if (lastWhereConditionIsIncomplete) {
            lastWhereConditionIsIncomplete = false;
        } else {
            SqlWhereConditionImpl newCondition = new SqlBinaryWhereConditionImpl();
            lastWhereCondition.setConnector(SqlWhereConditionConnector.AND);
            lastWhereCondition.setNext(newCondition);
            lastWhereCondition = newCondition;
        }
        SqlTokenImpl token = new SqlTokenImpl();
        token.setString(field);
        token.setType(SqlTokenType.FIELD);
        ((SqlBinaryWhereConditionImpl) lastWhereCondition).setLeftValue(token);
        return wrapped;
    }

    public T or(String field) {
        if (lastWhereCondition == null) {
            throw new QueryConstructionException("You have not started the where or having");
        } else if (!lastWhereConditionIsIncomplete) {
            throw new QueryConstructionException("Where is the left side of the OR");
        }
        SqlTokenImpl token = new SqlTokenImpl();
        token.setString(field);
        token.setType(SqlTokenType.FIELD);
        SqlBinaryWhereConditionImpl newCondition = new SqlBinaryWhereConditionImpl();
        newCondition.setLeftValue(token);
        lastWhereCondition.setNext(newCondition);
        lastWhereCondition.setConnector(SqlWhereConditionConnector.OR);
        lastWhereCondition = newCondition;
        return wrapped;
    }

    public T is(Object value) {
        return addRightValue(value, SqlBinaryOperator.EQUAL);
    }

    public T isNot(Object value) {
        return addRightValue(value, SqlBinaryOperator.NOT_EQUAL);
    }

    public T greaterThan(Object value) {
        return addRightValue(value, SqlBinaryOperator.GREATER_THAN);
    }

    public T lowerThan(Object value) {
        return addRightValue(value, SqlBinaryOperator.LOWER_THAN);
    }

    public T greaterThanOrEqual(Object value) {
        return addRightValue(value, SqlBinaryOperator.GREATER_THAN_OR_EQUAL);
    }

    public T lowerThanOrEqual(Object value) {
        return addRightValue(value, SqlBinaryOperator.LOWER_THAN_OR_EQUAL);
    }

    public T isNull() {
        return addRightValue(null, SqlBinaryOperator.IS);
    }

    public T isNotNull() {
        return addRightValue(null, SqlBinaryOperator.IS_NOT);
    }

    public T in(Object ...params) {
        return addRightValue(Arrays.asList(params), SqlBinaryOperator.IN);
    }

    public T in(List<Object> params) {
        return addRightValue(params, SqlBinaryOperator.IN);
    }

    public T notIn(List<Object> params) {
        return addRightValue(params, SqlBinaryOperator.NOT_IN);
    }

    private T addRightValue(List<Object> params, SqlBinaryOperator operator) {
        if (lastWhereCondition instanceof SqlBinaryWhereConditionImpl) {
            SqlBinaryWhereConditionImpl lastWhereCondition = (SqlBinaryWhereConditionImpl) this.lastWhereCondition;
            lastWhereCondition.setOperator(operator);
            SqlTokenImpl token = new SqlTokenImpl();
            token.setType(SqlTokenType.PARAM_LIST);
            token.setParams(params);
            lastWhereCondition.setRightValue(token);
            return wrapped;
        }
        throw new QueryConstructionException("Where is the left side of this condition");
    }

    private T addRightValue(Object value, SqlBinaryOperator operator) {
        if (lastWhereCondition instanceof SqlBinaryWhereConditionImpl) {
            SqlBinaryWhereConditionImpl lastWhereCondition = (SqlBinaryWhereConditionImpl) this.lastWhereCondition;
            lastWhereCondition.setOperator(operator);
            SqlTokenImpl token = new SqlTokenImpl();
            token.setType(SqlTokenType.PARAM);
            token.setParams(Collections.singletonList(value));
            lastWhereCondition.setRightValue(token);
            return wrapped;
        }
        throw new QueryConstructionException("Where is the left side of this condition");
    }


    @Override
    public T where() {
        condition = new SqlBinaryWhereConditionImpl();
        lastWhereConditionIsIncomplete = true;
        lastWhereCondition = condition;
        return wrapped;
    }

    @Override
    public T where(String field) {
        return where().and(field);
    }

    public SqlWhereCondition build() {
        return condition;
    }
}
