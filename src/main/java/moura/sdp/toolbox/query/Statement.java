package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Interpolation;
import moura.sdp.toolbox.orm.StatementResult;

import java.util.List;

public interface Statement extends Interpolation  {

    default String getSql() {
        return getString();
    }

    StatementType getType();

    List<Object> getParams();

    StatementResult execute();

}
