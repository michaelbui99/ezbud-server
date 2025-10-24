package dk.michaelbui.ezbud_server.domain.model.validators;

import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.validation.EntityValidator;
import dk.michaelbui.ezbud_server.validation.fieldvalidators.MaxValidator;
import dk.michaelbui.ezbud_server.validation.fieldvalidators.MinValidator;
import dk.michaelbui.ezbud_server.validation.fieldvalidators.NotBlankValidator;
import dk.michaelbui.ezbud_server.validation.fieldvalidators.NotNullValidator;

public class AccountValidator extends EntityValidator<Account> {
    public AccountValidator() {
        addRule("name", Account::getName, new MinValidator(1));
        addRule("name", Account::getName, new MaxValidator(70));
        addRule("name", Account::getName, new NotNullValidator());
        addRule("name", Account::getName, new NotBlankValidator());

        addRule("id", Account::getId, new NotNullValidator());
    }
}
