package dk.michaelbui.ezbud_server.validation.fieldvalidators;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.Optional;

public interface FieldValidator {
    public Optional<ValidationError> validate(String fieldName, Object field);
    public boolean accepts(Class<?> clazz);
}
