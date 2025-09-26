package dk.michaelbui.ezbud;

import dk.michaelbui.ezbud.validation.DomainValidationError;

import java.util.ArrayList;
import java.util.Collection;

public class DomainActionResultWithEntity<T> extends DomainActionResult {
    private T entity;

    protected DomainActionResultWithEntity(T entity) {
        super(new ArrayList<>());
        this.entity = entity;
    }

    protected DomainActionResultWithEntity(Collection<String> errors) {
        super(errors);
    }

    public static <T> DomainActionResultWithEntity<T> error(Collection<String> errors) {
        return new DomainActionResultWithEntity<>(errors);
    }

    public static <T> DomainActionResultWithEntity<T> success(T entity) {
        return new DomainActionResultWithEntity<>(entity);
    }

    public static <T> DomainActionResultWithEntity<T> fromValidationErrors(Collection<DomainValidationError<T>> errors) {
        return new DomainActionResultWithEntity<>(
                errors.stream()
                        .map(err -> String.format("field '%s': '%s'", err.getField(), err.getReason()))
                        .toList()
        );
    }

    @Override
    public boolean isSuccess() {
        return super.isSuccess() && entity != null;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
