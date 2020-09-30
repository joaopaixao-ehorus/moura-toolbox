package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.*;

public class SqlPiecesImpl implements SqlPieces {

    private SqlSelect select;
    private SqlFrom from;
    private SqlWhereCondition where;
    private SqlGroupBy groupBy;
    private SqlWhereCondition having;
    private SqlOrderBy orderBy;

    @Override
    public SqlSelect getSelect() {
        return select;
    }

    public void setSelect(SqlSelect select) {
        this.select = select;
    }

    @Override
    public SqlFrom getFrom() {
        return from;
    }

    public void setFrom(SqlFrom from) {
        this.from = from;
    }

    @Override
    public SqlWhereCondition getWhere() {
        return where;
    }

    public void setWhere(SqlWhereCondition where) {
        this.where = where;
    }

    @Override
    public SqlGroupBy getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(SqlGroupBy groupBy) {
        this.groupBy = groupBy;
    }

    @Override
    public SqlWhereCondition getHaving() {
        return having;
    }

    public void setHaving(SqlWhereCondition having) {
        this.having = having;
    }

    @Override
    public SqlOrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(SqlOrderBy orderBy) {
        this.orderBy = orderBy;
    }
}
