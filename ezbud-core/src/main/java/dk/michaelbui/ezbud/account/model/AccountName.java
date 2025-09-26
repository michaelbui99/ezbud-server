package dk.michaelbui.ezbud.account.model;

import dk.michaelbui.ezbud.validation.DomainValidationErrorReason;
import dk.michaelbui.ezbud.validation.ValidatableDomainObject;
import dk.michaelbui.ezbud.validation.DomainValidationError;

import java.util.ArrayList;
import java.util.Collection;

public class AccountName extends ValidatableDomainObject<AccountName> {
    private static final int MAX_LENGTH = 100;

    private String value;

    public AccountName(String name) {
        super(AccountName.class);
        this.value = name;
    }

    public Collection<DomainValidationError<AccountName>> validate() {
        ArrayList<DomainValidationError<AccountName>> errors = new ArrayList<>();
        if (value == null) {
            errors.add(createValidationError("name", DomainValidationErrorReason.FIELD_IS_NULL));
        }
        if (value.isEmpty()) {
            errors.add(createValidationError("name", DomainValidationErrorReason.FIELD_IS_EMPTY));
        }
        if (value.length() > MAX_LENGTH) {
            errors.add(createValidationError("name", DomainValidationErrorReason.FIELD_EXCEEDS_MAXIMUM));
        }
        return errors;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
