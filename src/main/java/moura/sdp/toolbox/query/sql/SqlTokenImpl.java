package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlToken;
import moura.sdp.toolbox.query.sql.SqlTokenType;

import java.util.Collections;
import java.util.List;

public class SqlTokenImpl implements SqlToken {

    private String string;
    private SqlTokenType type;
    private List<Object> params;

    public SqlTokenImpl() {
    }

    public SqlTokenImpl(String string, SqlTokenType type) {
        this.string = string;
        this.type = type;
    }

    public SqlTokenImpl(String string, SqlTokenType type, List<Object> params) {
        this.string = string;
        this.type = type;
        this.params = params;
    }

    public SqlTokenImpl(String string, SqlTokenType type, Object param) {
        this.string = string;
        this.type = type;
        this.params = Collections.singletonList(param);
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
