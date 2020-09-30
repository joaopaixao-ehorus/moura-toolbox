package moura.sdp.toolbox.query.exception;

public class UnsupportedSqlFeatureException extends RuntimeException {

    public UnsupportedSqlFeatureException() {
    }

    public UnsupportedSqlFeatureException(String message) {
        super(message);
    }
}
