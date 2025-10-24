package dk.michaelbui.ezbud_server.validation.fieldvalidators;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.Optional;

public class NotNullValidator implements FieldValidator{
    @Override
    public Optional<ValidationError> validate(String fieldName, Object field) {
        if (field == null) {
            return Optional.of(new ValidationError(fieldName, "Field is null"));
        }
        return Optional.empty();
    }

    @Override
    public boolean accepts(Class<?> clazz) {
        return true;
    }
}
