package moura.sdp.toolbox.query.dialect;

import moura.sdp.toolbox.query.MutableQuery;
import moura.sdp.toolbox.query.MutableStatement;
import moura.sdp.toolbox.query.sql.SqlDelete;
import moura.sdp.toolbox.query.sql.SqlInsert;
import moura.sdp.toolbox.query.sql.SqlPieces;
import moura.sdp.toolbox.query.sql.SqlUpdate;

public interface Dialect {

    MutableQuery makeQuery(SqlPieces pieces);

    MutableStatement makeInsert(SqlInsert insert);

    MutableStatement makeUpdate(SqlUpdate insert);

    MutableStatement makeDelete(SqlDelete insert);

}
