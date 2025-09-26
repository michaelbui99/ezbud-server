package dk.michaelbui.ezbud;

import dk.michaelbui.ezbud.validation.DomainValidationError;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DomainActionResult {
    private final Collection<String> errors;

   protected  DomainActionResult(Collection<String> errors) {
        this.errors = errors;
    }

    protected  DomainActionResult() {
        this.errors = new ArrayList<>();
    }

    public static DomainActionResult singleError(String error) {
        return new DomainActionResult(List.of(error));
    }

    public static DomainActionResult success(){
        return new DomainActionResult(Collections.emptyList());
    }

    public static <T> DomainActionResult fromValidationErrors(Collection<DomainValidationError<T>> errors) {
        return new DomainActionResultWithEntity<>(
                errors.stream()
                        .map(err -> String.format("field '%s': '%s'", err.getField(), err.getReason()))
                        .toList()
        );
    }

    public boolean isFailed() {
        return !isSuccess();
    }

    public boolean isSuccess() {
        return errors.isEmpty();
    }

    public Collection<String> getErrors() {
        return errors;
    }

}
