package moura.sdp.toolbox.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationResult {

    private final HashMap<String, List<ValidationResultItem>> errors = new HashMap<>();
    private int errorCount;

    public void merge(ValidationResult validationResult) {
        for (Map.Entry<String, List<ValidationResultItem>> entry : validationResult.getErrors().entrySet()) {
            if (errors.get(entry.getKey()) != null) {
                errors.put(entry.getKey(), new ArrayList<>());
            }
            errors.get(entry.getKey()).addAll(entry.getValue());
        }
        errorCount += validationResult.getErrorCount();
    }

    public ValidationResult addError(String key, ValidationResultItem error) {
        errors.computeIfAbsent(key, k -> new ArrayList<>());
        errors.get(key).add(error);
        errorCount += 1;
        return this;
    }

    public Map<String, List<ValidationResultItem>> getErrors() {
        return errors;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public boolean isValid() {
        return errorCount == 0;
    }
}
