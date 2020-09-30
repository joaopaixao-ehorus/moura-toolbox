package moura.sdp.toolbox.orm;

import java.util.List;

public interface StatementResult {

    List<Object> getGeneratedKeys();

    int getUpdatedRows();

}
