package dk.michaelbui.ezbud_server.api;

import dk.michaelbui.ezbud_server.api.dtos.ValidationErrorsDto;
import dk.michaelbui.ezbud_server.domain.exception.DomainException;
import dk.michaelbui.ezbud_server.domain.exception.NotFoundException;
import dk.michaelbui.ezbud_server.domain.exception.UnauthorizedException;
import dk.michaelbui.ezbud_server.domain.exception.ValidationException;
import dk.michaelbui.ezbud_server.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;

@RestControllerAdvice
public class ApiExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorsDto> handleValidationException(ValidationException ex){
        ValidationErrorsDto dto = new ValidationErrorsDto();
        dto.setValidationErrors(ex.getErrors());
        return ResponseEntity.badRequest().body(dto);
    }

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleDomainException() {
        // Intentionally empty
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(NotFoundException ex) {
        ex.logException(LOG);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public void handleUnauthorizedException(UnauthorizedException ex) {
        ex.logException(LOG);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException() {
        // Intentionally empty
    }
}
