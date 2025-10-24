package dk.michaelbui.ezbud_server.validation.fieldvalidators;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.Optional;

public class MaxValidator implements FieldValidator {
    private final Number maxValue;

    public MaxValidator(Number maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public Optional<ValidationError> validate(String fieldName, Object field) {
        if (field instanceof String stringField && stringField.length() > maxValue.intValue()) {
            return Optional.of(new ValidationError(fieldName, String.format("Length of field is greater than maximum allowed value %s", maxValue.intValue())));
        } else if (field instanceof Number numberField && numberField.doubleValue() > maxValue.doubleValue()) {
            return Optional.of(new ValidationError(fieldName, String.format("Value of field is greater than maximum allowed value %s", maxValue.intValue())));
        }

        return Optional.empty();
    }

    @Override
    public boolean accepts(Class<?> clazz) {
        boolean isNumber =
                Number.class.isAssignableFrom(clazz) || isPrimitiveNumber(clazz);
        return clazz.equals(String.class) || isNumber;
    }

    private boolean isPrimitiveNumber(Class<?> clazz) {
        return clazz.isPrimitive() && (
                clazz == int.class ||
                        clazz == long.class ||
                        clazz == double.class ||
                        clazz == float.class ||
                        clazz == short.class ||
                        clazz == byte.class
        );
    }
}
