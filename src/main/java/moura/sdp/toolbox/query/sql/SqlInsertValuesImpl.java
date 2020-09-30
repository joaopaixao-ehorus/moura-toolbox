package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlInsertValues;
import moura.sdp.toolbox.query.sql.SqlToken;

import java.util.List;

public class SqlInsertValuesImpl implements SqlInsertValues {

    private List<SqlToken> fields;
    private List<SqlToken> params;

    @Override
    public List<SqlToken> getFields() {
        return fields;
    }

    public void setFields(List<SqlToken> fields) {
        this.fields = fields;
    }

    @Override
    public List<SqlToken> getParams() {
        return params;
    }

    public void setParams(List<SqlToken> params) {
        this.params = params;
    }
}
