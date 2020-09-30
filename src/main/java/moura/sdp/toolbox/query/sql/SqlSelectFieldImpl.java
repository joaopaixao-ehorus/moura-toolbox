package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlSelectField;
import moura.sdp.toolbox.query.sql.SqlTokenType;

import java.util.List;

public class SqlSelectFieldImpl implements SqlSelectField {

    private String alias;
    private String string;
    private SqlTokenType type;
    private List<Object> params;

    @Override
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public SqlTokenType getType() {
        return type;
    }

    public void setType(SqlTokenType type) {
        this.type = type;
    }

    @Override
    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

}
