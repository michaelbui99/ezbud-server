package dk.michaelbui.ezbud_server.domain.service;

import dk.michaelbui.ezbud_server.domain.exception.DomainException;
import dk.michaelbui.ezbud_server.domain.exception.ValidationException;
import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.domain.model.validators.AccountValidator;
import dk.michaelbui.ezbud_server.domain.repository.AccountRepository;
import dk.michaelbui.ezbud_server.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AccountServiceImpl implements AccountService {
    private static final Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean userHasAccount(String userId, UUID accountId) {
        return accountRepository.userHasAccount(userId, accountId);
    }

    @Override
    public Collection<Account> getAllAccounts(String userId) {
        return accountRepository.getAllAccountsByUserId(userId);
    }

    @Override
    public Optional<Account> getAccountById(UUID id) {
        return Optional.ofNullable(accountRepository.getAccountById(id));
    }

    @Override
    public Account createAccount(String userId, String name, boolean isOnBudget) throws DomainException {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setName(name);
        account.setTransactions(new ArrayList<>());
        account.setOnBudget(isOnBudget);

        AccountValidator validator = new AccountValidator();
        Collection<ValidationError> validationErrors = validator.validate(account);
        if (!validationErrors.isEmpty()) {
            throw new ValidationException(validationErrors);
        }

        accountRepository.createAccount(userId, account);

        return account;
    }

    @Override
    public void updateAccount(String userId, Account updatedAccount) throws DomainException {
        accountRepository.updateAccount(userId, updatedAccount.getId(), updatedAccount);
    }
}
