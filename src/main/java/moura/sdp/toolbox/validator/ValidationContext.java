package moura.sdp.toolbox.validator;

public class ValidationContext {

    private final Validator validator;
    private final ValidationResult result;

    public ValidationContext(
        Validator validator,
        ValidationResult result
    ) {
        this.validator = validator;
        this.result = result;
    }

    public Validator getValidator() {
        return validator;
    }

    public ValidationResult getResult() {
        return result;
    }
}
