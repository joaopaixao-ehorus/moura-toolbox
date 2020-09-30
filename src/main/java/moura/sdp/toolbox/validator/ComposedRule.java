package moura.sdp.toolbox.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class ComposedRule<T> implements Rule<T> {

    public static class ComposedRuleEntry<T, R> {

        private final Predicate<T> predicate;
        private final Function<T, ? extends R> function;
        private final Rule<R> rule;

        public ComposedRuleEntry(Predicate<T> predicate, Function<T, ? extends R> function, Rule<R> rule) {
            this.predicate = predicate;
            this.function = function;
            this.rule = rule;
        }

        public ComposedRuleEntry(Function<T, ? extends R> function, Rule<R> rule) {
            this.predicate = o -> true;
            this.function = function;
            this.rule = rule;
        }

        public boolean mustApply(T object) {
            return predicate.test(object);
        }

        public R getValue(T object) {
            return function.apply(object);
        }

        public Rule<R> getRule() {
            return rule;
        }
    }

    private final List<ComposedRuleEntry<T, Object>> entries = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public <R> ComposedRule<T> use(Function<T, ? extends R> function, Rule<R> rule) {
        entries.add((ComposedRuleEntry<T, Object>) new ComposedRuleEntry<>(function, rule));
        return this;
    }

    @SuppressWarnings("unchecked")
    public <R> ComposedRule<T> useIf(Predicate<T> predicate, Function<T, ? extends R> function, Rule<R> rule) {
        entries.add((ComposedRuleEntry<T, Object>) new ComposedRuleEntry<>(predicate, function, rule));
        return this;
    }

    @Override
    public void validate(T object, ValidationContext context) {
        for (ComposedRuleEntry<T, Object> entry : entries) {
            if (entry.mustApply(object)) {
                entry.getRule().validate(entry.getValue(object), context);
            }
        }
    }

}
