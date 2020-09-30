package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlOrderBy;
import moura.sdp.toolbox.query.sql.SqlToken;

import java.util.List;

public class SqlOrderByImpl implements SqlOrderBy {

    private List<SqlToken> fields;

    @Override
    public List<SqlToken> getFields() {
        return fields;
    }

    public void setFields(List<SqlToken> fields) {
        this.fields = fields;
    }

}
