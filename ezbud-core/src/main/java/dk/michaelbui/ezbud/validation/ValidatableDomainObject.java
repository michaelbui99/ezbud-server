package dk.michaelbui.ezbud.validation;

import java.util.Collection;

public abstract class ValidatableDomainObject<T> {
    private final Class<T> validatedClass;

    public ValidatableDomainObject(Class<T> validatedClass) {
        this.validatedClass = validatedClass;
    }

    public abstract Collection<DomainValidationError<T>> validate();

    protected DomainValidationError<T> createValidationError(String field, String reason) {
        DomainValidationError<T> error = new DomainValidationError<>();
        error.setValidatedClass(validatedClass);
        error.setField(field);
        error.setReason(reason);
        return error;
    }
}
