package moura.sdp.toolbox.query.sql;

import java.util.List;

public interface SqlFrom {

    Object getSource();

    String getAlias();

    List<Object> getParams();

}
