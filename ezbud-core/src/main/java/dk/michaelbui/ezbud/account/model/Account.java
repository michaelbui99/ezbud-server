package dk.michaelbui.ezbud.account.model;

import dk.michaelbui.ezbud.validation.ValidatableDomainObject;
import dk.michaelbui.ezbud.validation.DomainValidationError;
import dk.michaelbui.ezbud.validation.DomainValidationErrorReason;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class Account extends ValidatableDomainObject<Account> {
    private UUID id;
    private AccountName name;
    private boolean onBudget;
    private Collection<Transaction> transactions = new ArrayList<>();

    public Account() {
        super(Account.class);
    }

    @Override
    public Collection<DomainValidationError<Account>> validate() {
        List<DomainValidationError<Account>> errors = new ArrayList<>(name.validate().stream()
                .map(error -> createValidationError("name", error.getReason()))
                .toList());

        if (id == null) {
            errors.add(createValidationError("id", DomainValidationErrorReason.FIELD_IS_NULL));
        }

        return errors;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    public AccountName getName() {
        return name;
    }

    public void setName(AccountName name) {
        this.name = name;
    }

    public boolean isOnBudget() {
        return onBudget;
    }

    public void setOnBudget(boolean onBudget) {
        this.onBudget = onBudget;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }

}
