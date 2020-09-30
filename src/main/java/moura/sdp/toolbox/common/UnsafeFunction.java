package moura.sdp.toolbox.common;

public interface UnsafeFunction<T, R> {

    R apply(T object) throws Exception;

}
