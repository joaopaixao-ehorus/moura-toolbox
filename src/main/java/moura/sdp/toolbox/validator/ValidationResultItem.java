package moura.sdp.toolbox.validator;

import java.util.List;

public class ValidationResultItem {

    private final String message;
    private final String type;
    private final Object rejected;
    private final List<Object> params;

    public ValidationResultItem(String message, String type, Object rejected, List<Object> params) {
        this.message = message;
        this.type = type;
        this.rejected = rejected;
        this.params = params;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public List<Object> getParams() {
        return params;
    }

    public Object getRejected() {
        return rejected;
    }
}
