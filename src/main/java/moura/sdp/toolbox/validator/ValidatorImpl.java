package moura.sdp.toolbox.validator;

import java.util.HashMap;
import java.util.Map;

public class ValidatorImpl implements Validator {

    private final Map<Class<?>, Rule<?>> rules = new HashMap<>();

    public <T> void register(Class<T> clazz, Rule<T> rule) {
        rules.put(clazz, rule);
    }

    @Override
    @SuppressWarnings("unchecked")
    public ValidationResult validate(Object object) {
        ValidationResult result = new ValidationResult();
        ValidationContext context = new ValidationContext(this, result);
        Rule<Object> rule = (Rule<Object>) rules.get(object.getClass());
        rule.validate(object, context);
        return result;
    }

}
