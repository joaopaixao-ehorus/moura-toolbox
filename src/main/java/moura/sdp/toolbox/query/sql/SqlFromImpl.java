package moura.sdp.toolbox.query.sql;

import moura.sdp.toolbox.query.sql.SqlFrom;

import java.util.List;

public class SqlFromImpl implements SqlFrom {

    private String alias;
    private Object source;
    private List<Object> params;

    @Override
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Override
    public Object getSource() {
        return source;
    }

    public void setSource(Object source) {
        this.source = source;
    }

    @Override
    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
