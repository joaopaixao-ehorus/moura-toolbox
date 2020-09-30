package moura.sdp.toolbox.query;

import moura.sdp.toolbox.orm.Database;

import java.util.List;
import java.util.Set;

public interface MutableQuery extends Query {

    void setHint(Set<Object> hint);

    void setDatabase(Database database);

    void setSql(String sql);

    void setParams(List<Object> params);

}
