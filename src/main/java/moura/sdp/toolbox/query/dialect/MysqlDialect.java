package moura.sdp.toolbox.query.dialect;

import moura.sdp.toolbox.common.Pair;
import moura.sdp.toolbox.query.MutableQuery;
import moura.sdp.toolbox.query.QueryImpl;
import moura.sdp.toolbox.query.StatementImpl;
import moura.sdp.toolbox.query.StatementType;
import moura.sdp.toolbox.query.exception.QueryConstructionException;
import moura.sdp.toolbox.query.exception.UnsupportedSqlFeatureException;
import moura.sdp.toolbox.query.sql.*;

import java.util.ArrayList;
import java.util.List;

public class MysqlDialect implements Dialect {

    public MutableQuery makeQuery(SqlPieces pieces) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        append(parse(pieces.getSelect()), sql, params);
        append(parse(pieces.getFrom()), sql, params);
        append(parse(pieces.getWhere(), "WHERE"), sql, params);
        append(parse(pieces.getGroupBy()), sql, params);
        append(parse(pieces.getHaving(), "HAVING"), sql, params);
        append(parse(pieces.getOrderBy()), sql, params);

        QueryImpl query = new QueryImpl();
        query.setParams(params);
        query.setString(sql.toString());

        return query;
    }

    @Override
    public StatementImpl makeInsert(SqlInsert insert) {
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        List<Object> params = new ArrayList<>();

        append(parse(insert.getTable()), sql, params);
        append(parse(insert.getValues().getFields()), sql, params);

        sql.append(" VALUES");

        append(parse(insert.getValues().getParams()), sql, params);

        StatementImpl statement = new StatementImpl();
        statement.setParams(params);
        statement.setSql(sql.toString());
        statement.setType(StatementType.INSERT);

        return statement;
    }

    @Override
    public StatementImpl makeUpdate(SqlUpdate insert) {
        StringBuilder sql = new StringBuilder("UPDATE");
        List<Object> params = new ArrayList<>();

        append(parse(insert.getTable()), sql, params);
        sql.append(" SET ");
        append(parseUpdateFields(insert.getValues().getFields(), insert.getValues().getParams()), sql, params);
        append(parse(insert.getWhere(), "WHERE"), sql, params);

        StatementImpl statement = new StatementImpl();
        statement.setParams(params);
        statement.setSql(sql.toString());
        statement.setType(StatementType.UPDATE);

        return statement;
    }

    @Override
    public StatementImpl makeDelete(SqlDelete delete) {
        StringBuilder sql = new StringBuilder("DELETE FROM");
        List<Object> params = new ArrayList<>();

        append(parse(delete.getTable()), sql, params);
        append(parse(delete.getWhere(), "WHERE"), sql, params);

        StatementImpl statement = new StatementImpl();
        statement.setParams(params);
        statement.setSql(sql.toString());
        statement.setType(StatementType.DELETE);

        return statement;
    }

    private Pair<String, List<Object>> parseUpdateFields(List<SqlToken> fields, List<SqlToken> tokens) {
        List<String> strings = new ArrayList<>();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            SqlToken field = fields.get(i);
            SqlToken token = tokens.get(i);
            Pair<String, List<Object>> pair1 = parse(field);
            Pair<String, List<Object>> pair2 = parse(token);
            strings.add(pair1.getFirst() + " = " + pair2.getFirst());
            if (pair1.getSecond() != null) {
                params.addAll(pair1.getSecond());
            }
            if (pair2.getSecond() != null) {
                params.addAll(pair2.getSecond());
            }
        }
        return new Pair<>(String.join(", ", strings), params);
    }

    private Pair<String, List<Object>> parse(List<SqlToken> tokens) {
        List<String> strings = new ArrayList<>();
        List<Object> params = null;
        for (SqlToken token : tokens) {
            Pair<String, List<Object>> pair = parse(token);
            if (pair.getSecond() != null && !pair.getSecond().isEmpty()) {
                if (params == null) {
                    params = new ArrayList<>();
                }
                params.addAll(pair.getSecond());
            }
            strings.add(pair.getFirst());
        }
        return new Pair<>("(" + String.join(", ", strings) + ")", params);
    }

    private void append(Pair<String, List<Object>> pair, StringBuilder builder, List<Object> params) {
        if (pair == null) {
            return;
        }
        if (pair.getFirst() != null) {
            if (!builder.toString().isEmpty()) {
                builder.append(" ");
            }
            builder.append(pair.getFirst());
        }
        if (pair.getSecond() != null) {
            params.addAll(pair.getSecond());
        }
    }

    private Pair<String, List<Object>> parse(SqlSelect select) {
        if (select == null || select.getFields() == null || select.getFields().isEmpty()) {
            return new Pair<>("SELECT 1", null);
        }
        StringBuilder sql = new StringBuilder("SELECT ");
        List<Object> params = null;
        boolean isFirst = true;
        for (SqlSelectField field : select.getFields()) {
            if (!isFirst) {
                sql.append(", ");
            }
            Pair<String, List<Object>> pair = parse(field);
            if (pair.getSecond() != null) {
                if (params == null) {
                    params = new ArrayList<>();
                }
                params.addAll(pair.getSecond());
            }
            sql.append(pair.getFirst());
            isFirst = false;
        }
        return new Pair<>(sql.toString(), params);
    }

    private Pair<String, List<Object>> parse(SqlSelectField field) {
        switch (field.getType()) {
            case RAW:
                return new Pair<>(field.getString() + (field.getAlias() != null ? " AS " + field.getAlias() : ""), field.getParams());
            case ALL_FIELDS:
                return new Pair<>("*", null);
            case TABLE:
            case FIELD:
                return new Pair<>("`" + field.getString() + "`", null);
            default:
                throw new UnsupportedSqlFeatureException("The field type " + field.getType() + " is not supported by " + this.getClass().getName());
        }
    }

    private Pair<String, List<Object>> parse(SqlToken token) {
        if (token == null) {
            return new Pair<>("", null);
        }
        switch (token.getType()) {
            case RAW:
                return new Pair<>(token.getString(), token.getParams());
            case FIELD:
            case TABLE:
                return new Pair<>("`" + token.getString() + "`", null);
            case PARAM:
                return new Pair<>("?", token.getParams());
            case PARAM_LIST:
                StringBuilder builder = new StringBuilder("(");
                boolean isFirst = true;
                for (int i = 0; i < token.getParams().size(); i++) {
                    if (!isFirst) {
                        builder.append(", ");
                    }
                    builder.append("?");
                    isFirst = false;
                }
                builder.append(")");
                return new Pair<>(builder.toString(), token.getParams());
            default:
                throw new UnsupportedSqlFeatureException("The token type " + token.getType() + " is not supported by " + this.getClass().getName());
        }
    }

    private Pair<String, List<Object>> parse(SqlFrom from) {
        if (from == null || from.getSource() == null) {
            return null;
        }
        return new Pair<>("FROM " + from.getSource() + (from.getAlias() != null ? " AS " + from.getAlias() : ""), null);
    }

    private Pair<String, List<Object>> parse(SqlWhereCondition condition, String prefix) {
        if (condition == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder(prefix + " ");
        List<Object> params = null;
        for (SqlWhereCondition current = condition; current != null; current = current.getNext()) {
            if (current instanceof SqlBinaryWhereCondition) {
                SqlBinaryWhereCondition binaryWhereCondition = (SqlBinaryWhereCondition) current;
                Pair<String, List<Object>> leftValue = parse(binaryWhereCondition.getLeftValue());
                Pair<String, List<Object>> rightValue = parse(binaryWhereCondition.getRightValue());
                String operator = parse(binaryWhereCondition.getOperator());
                if (params == null && (leftValue.getSecond() != null || rightValue.getSecond() != null)) {
                    params = new ArrayList<>();
                }
                if (leftValue.getSecond() != null) {
                    params.addAll(leftValue.getSecond());
                }
                if (rightValue.getSecond() != null) {
                    params.addAll(rightValue.getSecond());
                }
                builder.append(leftValue.getFirst()).append(" ").append(operator).append(" ").append(rightValue.getFirst());
                if (current.getNext() != null) {
                    builder.append(" ").append(parse(condition.getConnector())).append(" ");
                }
            } else {
                throw new UnsupportedSqlFeatureException(current.getClass().getName() + " is not supported by " + this.getClass().getName());
            }
        }
        return new Pair<>(builder.toString(), params);
    }

    private Pair<String, List<Object>> parse(SqlOrderBy orderBy) {
        if (orderBy == null) {
            return null;
        }
        return parse(orderBy.getFields(), "ORDER BY ");
    }

    private Pair<String, List<Object>> parse(SqlGroupBy groupBy) {
        if (groupBy == null) {
            return null;
        }
        return parse(groupBy.getFields(), "GROUP BY ");
    }

    private Pair<String, List<Object>> parse(List<SqlToken> fields, String prefix) {
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        StringBuilder builder = new StringBuilder(prefix + " ");
        List<Object> params = null;
        boolean isFirst = true;
        for (SqlToken token : fields) {
            Pair<String, List<Object>> pair = parse(token);
            if (params == null && pair.getSecond() != null) {
                params = new ArrayList<>();
            }
            if (pair.getSecond() != null) {
                params.addAll(pair.getSecond());
            }
            if (!isFirst) {
                builder.append(", ");
            }
            builder.append(pair.getFirst());
            isFirst = false;
        }
        return new Pair<>(builder.toString(), params);
    }

    private String parse(SqlWhereConditionConnector connector) {
        if (connector == null) {
            return "AND";
        }
        switch (connector) {
            case OR: return "OR";
            case AND: return "AND";
            default: throw  new UnsupportedSqlFeatureException(connector + " is not supported by " + this.getClass().getName());
        }
    }

    private String parse(SqlBinaryOperator operator) {
        if (operator == null) {
            throw new QueryConstructionException("Operator for where condition wasn't informed");
        }
        switch (operator) {
            case EQUAL: return "=";
            case NOT_EQUAL: return "!=";
            case GREATER_THAN: return ">";
            case LOWER_THAN: return "<";
            case GREATER_THAN_OR_EQUAL: return ">=";
            case LOWER_THAN_OR_EQUAL: return "<=";
            case IS: return "IS";
            case IS_NOT: return "IS NOT";
            case IN: return "IN";
            case NOT_IN: return "NOT IN";
            default: throw new UnsupportedSqlFeatureException(operator + " is not supported by " + this.getClass().getName());
        }
    }
}
