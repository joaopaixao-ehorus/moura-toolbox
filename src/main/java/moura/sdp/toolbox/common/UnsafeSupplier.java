package moura.sdp.toolbox.common;

public interface UnsafeSupplier<T> {

    T get() throws Exception;

}
