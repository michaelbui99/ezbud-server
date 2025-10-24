package dk.michaelbui.ezbud_server.domain.exception;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ValidationException extends DomainException {
    private Collection<ValidationError> errors = new ArrayList<>();

    public ValidationException(Collection<ValidationError> errors) {
        this.errors = errors;
    }

    public ValidationException(String message, Collection<ValidationError> errors) {
        super(message);
        this.errors = errors;
    }

    public ValidationException(String message, Throwable cause, Collection<ValidationError> errors) {
        super(message, cause);
        this.errors = errors;
    }

    public Collection<ValidationError> getErrors() {
        return errors;
    }
}
