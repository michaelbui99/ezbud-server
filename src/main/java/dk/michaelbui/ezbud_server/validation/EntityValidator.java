package dk.michaelbui.ezbud_server.validation;

import dk.michaelbui.ezbud_server.validation.fieldvalidators.FieldValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

public class EntityValidator<T> implements Validator<T> {
    private final Collection<Rule<T, ?>> rules = new ArrayList<>();

    public <TField> void addRule(String fieldName, Function<T, TField> fieldFunc, FieldValidator validator) {
        rules.add(new Rule<>(fieldName, fieldFunc, validator));
    }

    public Collection<ValidationError> validate(T entity) {
        Collection<ValidationError> errors = new ArrayList<>();
        for (Rule<T, ?> rule : rules) {
            Object field = rule.getField().apply(entity);
            Class<?> fieldClass = field != null ? field.getClass() : Object.class;

            if (!rule.getFieldValidator().accepts(fieldClass)) {
                continue;
            }

            FieldValidator fieldValidator = rule.getFieldValidator();
            fieldValidator
                    .validate(rule.getFieldName(), field)
                    .ifPresent(errors::add);
        }

        return errors;
    }
}
