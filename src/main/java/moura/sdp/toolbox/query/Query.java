package moura.sdp.toolbox.query;

import moura.sdp.toolbox.common.Interpolation;

import java.util.List;
import java.util.Set;

public interface Query extends Interpolation {

    default String getSql() {
        return getString();
    }

    Set<Object> getHints();

    <T> List<T> all(Class<T> clazz);

}
