package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;

import java.util.List;

public interface MutableStatement extends Statement {

    void setSql(String sql);

    void setParams(List<Object> params);

    void setType(StatementType type);

    void setDatabase(Database database);
}
