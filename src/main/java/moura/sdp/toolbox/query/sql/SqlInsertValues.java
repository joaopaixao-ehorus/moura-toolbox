package moura.sdp.toolbox.query.sql;

import java.util.List;

public interface SqlInsertValues {

    List<SqlToken> getFields();

    List<SqlToken> getParams();

}
