package moura.sdp.toolbox.query.sql;

import java.util.List;

public interface SqlToken {

    String getString();

    SqlTokenType getType();

    List<Object> getParams();
    
}
