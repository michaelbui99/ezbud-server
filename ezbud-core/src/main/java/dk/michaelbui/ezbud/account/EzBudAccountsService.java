package dk.michaelbui.ezbud.account;

import dk.michaelbui.ezbud.DomainActionResult;
import dk.michaelbui.ezbud.DomainActionResultWithEntity;
import dk.michaelbui.ezbud.account.model.Account;
import dk.michaelbui.ezbud.account.model.AccountName;
import dk.michaelbui.ezbud.account.repository.AccountsRepository;
import dk.michaelbui.ezbud.exception.DomainException;
import dk.michaelbui.ezbud.validation.DomainValidationError;

import java.util.*;

public class EzBudAccountsService implements AccountsService {
    private final AccountsRepository accountsRepository;

    public EzBudAccountsService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public Optional<Account> getAccountById(String userId, String accountId) {
        return Optional.ofNullable(accountsRepository.getAccountById(userId, accountId));
    }

    @Override
    public Collection<Account> getAllAccounts(String userId) {
        return accountsRepository.getAllAccounts(userId);
    }

    @Override
    public DomainActionResultWithEntity<Account> createAccount(String userId, String name, boolean onBudget) {
        Account newAccount = new Account();
        newAccount.setId(UUID.randomUUID());
        newAccount.setName(new AccountName(name));
        newAccount.setOnBudget(onBudget);
        newAccount.setTransactions(new ArrayList<>());

        Collection<DomainValidationError<Account>> errors = newAccount.validate();
        if (!errors.isEmpty()) {
            return DomainActionResultWithEntity.fromValidationErrors(errors);
        }

        try {
            Account createdAccount = accountsRepository.createAccount(userId, newAccount);
            return DomainActionResultWithEntity.success(createdAccount);
        } catch (Exception e) {
            throw new DomainException("Failed to create new account", e);
        }
    }

    @Override
    public DomainActionResult updateAccount(String userId, Account updatedAccount) {
        if (updatedAccount == null) {
            return DomainActionResult.singleError("updatedAccount is null");
        }

        Optional<Account> existingAccount = getAccountById(userId, updatedAccount.getId().toString());
        if (existingAccount.isEmpty()) {
            DomainActionResultWithEntity<Account> createAccountResult = createAccount(userId, updatedAccount.getName().getValue(), updatedAccount.isOnBudget());
            return createAccountResult.isSuccess() ? updateAccount(userId, updatedAccount) : createAccountResult;
        }

        try {
            Collection<DomainValidationError<Account>> validationErrors = updatedAccount.validate();
            if (!validationErrors.isEmpty()) {
                return DomainActionResult.fromValidationErrors(validationErrors);
            }

            accountsRepository.updateAccount(userId, updatedAccount);
            return DomainActionResult.success();
        } catch (Exception e) {
            throw new DomainException("Failed to update account", e);
        }
    }
}
