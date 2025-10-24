package dk.michaelbui.ezbud_server.api.dtos;

import dk.michaelbui.ezbud_server.validation.ValidationError;

import java.util.Collection;

public class ValidationErrorsDto {
    private Collection<ValidationError> validationErrors;

    public Collection<ValidationError> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Collection<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
