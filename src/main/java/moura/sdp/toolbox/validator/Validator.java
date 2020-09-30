package moura.sdp.toolbox.validator;

public interface Validator {

    ValidationResult validate(Object object);

    default void validateOrFail(Object object) {
        ValidationResult result = validate(object);
        if (!result.isValid()) {
            throw new ValidationException(result);
        }
    }

}
