package dk.michaelbui.ezbud_server.domain.service;

import dk.michaelbui.ezbud_server.domain.exception.DomainException;
import dk.michaelbui.ezbud_server.domain.model.Account;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
    boolean userHasAccount(String userId, UUID accountId);
    Collection<Account> getAllAccounts(String userId);
    Optional<Account> getAccountById(String userId, UUID id);

    Account createAccount(String userId, String name, boolean isOnBudget) throws DomainException;
    void updateAccount(String userId, Account updatedAccount) throws DomainException;

}
