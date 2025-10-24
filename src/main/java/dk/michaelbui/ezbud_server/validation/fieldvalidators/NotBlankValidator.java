package dk.michaelbui.ezbud_server.validation.fieldvalidators;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.Optional;

public class NotBlankValidator implements FieldValidator{
    @Override
    public Optional<ValidationError> validate(String fieldName, Object field) {
        if (field instanceof String s && s.isBlank()) {
            return Optional.of(new ValidationError(fieldName, "Field is blank"));
        }
        return Optional.empty();
    }

    @Override
    public boolean accepts(Class<?> clazz) {
        return clazz.equals(String.class);
    }
}
