package moura.sdp.toolbox.utils;

import moura.sdp.toolbox.common.UnsafeSupplier;

import java.util.function.Function;

public class ExceptionUtils {

    private ExceptionUtils() {
    }

    public static  <T> T sneaky(UnsafeSupplier<T> supplier, Function<Exception, ? extends RuntimeException> exceptionSupplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw exceptionSupplier.apply(e);
        }
    }

    public static <T> T sneaky(UnsafeSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
