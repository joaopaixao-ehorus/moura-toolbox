package moura.sdp.toolbox.query.sql;

public interface SqlPieces {

    SqlSelect getSelect();

    SqlFrom getFrom();

    SqlWhereCondition getWhere();

    SqlGroupBy getGroupBy();

    SqlWhereCondition getHaving();

    SqlOrderBy getOrderBy();
}
