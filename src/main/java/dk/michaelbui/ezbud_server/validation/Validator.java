package dk.michaelbui.ezbud_server.validation;

import java.util.Collection;

public interface Validator<T> {
    Collection<ValidationError> validate(T entity);
}
