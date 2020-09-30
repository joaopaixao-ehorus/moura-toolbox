package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlGroupBy;
import moura.sdp.toolbox.query.sql.SqlToken;

import java.util.List;

public class SqlGroupByImpl implements SqlGroupBy {

    private List<SqlToken> fields;

    @Override
    public List<SqlToken> getFields() {
        return fields;
    }

    public void setFields(List<SqlToken> fields) {
        this.fields = fields;
    }

}
