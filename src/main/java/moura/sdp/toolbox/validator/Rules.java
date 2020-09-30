package moura.sdp.toolbox.validator;

import java.util.Collections;
import java.util.regex.Pattern;

public class Rules {

    public static final String RULE_NOT_NULL = "RULE_NOT_NULL";
    public static final String RULE_MAX = "RULE_MAX";
    public static final String RULE_NOT_EMPTY = "RULE_NOT_EMPTY";
    public static final String RULE_MIN = "RULE_MIN";
    public static final String RULE_PATTERN = "RULE_PATTERN";

    private Rules() {
    }

    public static <T> ComposedRule<T> composed() {
        return new ComposedRule<>();
    }

    public static Rule<Object> notNull(String attribute) {
        return (value, context) -> {
            if (value == null) {
                ValidationResultItem item = new ValidationResultItem(
                    String.format("Attribute %s can't be null", attribute), RULE_NOT_NULL, null, null
                );
                context.getResult().addError(attribute, item);
            }
        };
    }

    public static Rule<String> notEmpty(String attribute) {
        return (value, context) -> {
            if (value == null || value.trim().isEmpty()) {
                ValidationResultItem item = new ValidationResultItem(
                        String.format("Attribute %s can't be null or empty", attribute), RULE_NOT_EMPTY, value, null
                );
                context.getResult().addError(attribute, item);
            }
        };
    }

    public static Rule<String> pattern(String attribute, String pattern, String message) {
        Pattern compiled = Pattern.compile(pattern);
        return (value, context) -> {
            if (value == null) {
                return;
            }
            if (!compiled.matcher(value).matches()) {
                ValidationResultItem item = new ValidationResultItem(
                    String.format(message, attribute), RULE_PATTERN, null, null
                );
                context.getResult().addError(attribute, item);
            }
        };
    }

    public static Rule<Object> between(String attribute, int min, int max) {
        return composed()
                .use(i -> i, min(attribute, min))
                .use(i -> i, max(attribute, max));
    }

    public static Rule<Object> max(String attribute, int max) {
        return (value, context) -> {
            boolean fail;

            if (value == null) {
                return;
            } else if (value instanceof Byte) {
                fail = (Byte) value > max;
            } else if (value instanceof Short) {
                fail = (Short) value > max;
            } else if (value instanceof Integer) {
                fail = (Integer) value > max;
            } else if (value instanceof Long) {
                fail = (Long) value > max;
            } else if (value instanceof Float) {
                fail = (Float) value > max;
            } else if (value instanceof Double) {
                fail = (Double) value > max;
            } else if (value instanceof String) {
                if (((String) value).length() > max) {
                    ValidationResultItem item = new ValidationResultItem(
                        String.format("%s can't have more then %s characters", attribute, max), RULE_MAX, value, Collections.singletonList(max)
                    );
                    context.getResult().addError(attribute, item);
                }
                return;
            } else {
                ValidationResultItem item = new ValidationResultItem(
                    String.format("Could not check size of %s", attribute), RULE_MAX, value, Collections.singletonList(max)
                );
                context.getResult().addError(attribute, item);
                return;
            }

            if (fail) {
                ValidationResultItem item = new ValidationResultItem(
                        String.format("%s can't be greater than %s", attribute, max), RULE_MAX, value, Collections.singletonList(max)
                );
                context.getResult().addError(attribute, item);
            }
        };
    }

    public static Rule<Object> min(String attribute, int min) {
        return (value, context) -> {
            boolean fail;

            if (value == null) {
                return;
            } else if (value instanceof Byte) {
                fail = (Byte) value < min;
            } else if (value instanceof Short) {
                fail = (Short) value < min;
            } else if (value instanceof Integer) {
                fail = (Integer) value < min;
            } else if (value instanceof Long) {
                fail = (Long) value < min;
            } else if (value instanceof Float) {
                fail = (Float) value < min;
            } else if (value instanceof Double) {
                fail = (Double) value < min;
            } else if (value instanceof String) {
                if (((String) value).length() < min) {
                    ValidationResultItem item = new ValidationResultItem(
                        String.format("%s can't have less then %s characters", attribute, min), RULE_MIN, value, Collections.singletonList(min)
                    );
                    context.getResult().addError(attribute, item);
                }
                return;
            } else {
                ValidationResultItem item = new ValidationResultItem(
                    String.format("Could not check size of %s", attribute), RULE_MIN, value, Collections.singletonList(min)
                );
                context.getResult().addError(attribute, item);
                return;
            }

            if (fail) {
                ValidationResultItem item = new ValidationResultItem(
                    String.format("%s can't be less than %s", attribute, min), RULE_MIN, value, Collections.singletonList(min)
                );
                context.getResult().addError(attribute, item);
            }
        };
    }

}
