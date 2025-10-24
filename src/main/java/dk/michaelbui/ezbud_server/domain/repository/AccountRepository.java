package dk.michaelbui.ezbud_server.domain.repository;

import dk.michaelbui.ezbud_server.domain.model.Account;

import java.util.Collection;
import java.util.UUID;

public interface AccountRepository {
    Account getAccountById(UUID id);

    Collection<Account> getAllAccountsByUserId(String userId);

    Account createAccount(String userId, Account account);

    void updateAccount(String userId, UUID id, Account account);

    boolean userHasAccount(String userId, UUID accountId);
}
