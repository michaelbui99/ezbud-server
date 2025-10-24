package dk.michaelbui.ezbud_server.domain.model.validators;

import dk.michaelbui.ezbud_server.domain.exception.ValidationException;
import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.validation.ValidationError;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {

    @Test
    void validateReturnsValidationErrorWhenNameIsEmpty(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName("");
        account.setId(UUID.randomUUID());
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("name", errors.stream().findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("Length of field is less than minimum allowed value 1", errors.stream().findFirst().map(ValidationError::getMessage).orElse(""));
    }

    @Test
    void validateReturnsValidationErrorWhenNameIsGreaterThan70(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        account.setId(UUID.randomUUID());
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("name", errors.stream().findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("Length of field is greater than maximum allowed value 70", errors.stream().findFirst().map(ValidationError::getMessage).orElse(""));
    }

    @Test
    void validateReturnsValidationErrorWhenNameIsNull(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName(null);
        account.setId(UUID.randomUUID());
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("name", errors.stream().findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("Field is null", errors.stream().findFirst().map(ValidationError::getMessage).orElse(""));
    }

    @Test
    void validateReturnsValidationErrorWhenNameIsBlank(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName("           ");
        account.setId(UUID.randomUUID());
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("name", errors.stream().findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("Field is blank", errors.stream().findFirst().map(ValidationError::getMessage).orElse(""));
    }

    @Test
    void validateReturnsValidationErrorWhenIdIsNull(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName("Test");
        account.setId(null);
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(1, errors.size());
        assertEquals("id", errors.stream().findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("Field is null", errors.stream().findFirst().map(ValidationError::getMessage).orElse(""));
    }


    @Test
    void validateReturnsValidationErrorsWhenIdIsNullAndNameIsInvalid(){
        // Arrange
        AccountValidator validator = new AccountValidator();
        Account account = new Account();
        account.setName("   ");
        account.setId(null);
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(true);

        // Act
        Collection<ValidationError> errors = validator.validate(account);

        // Assert
        assertEquals(2, errors.size());
        assertEquals("id", errors.stream().filter(err -> err.getField().equals("id")).findFirst().map(ValidationError::getField).orElse(null));
        assertEquals("name", errors.stream().filter(err -> err.getField().equals("name")).findFirst().map(ValidationError::getField).orElse(null));
    }
}