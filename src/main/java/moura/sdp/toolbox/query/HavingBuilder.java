package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Builder;
import moura.sdp.toolbox.query.sql.SqlWhereCondition;

public interface HavingBuilder<T> extends ConditionBuilder<T>, Builder<SqlWhereCondition> {

    T having();

    T having(String field);

}
