package dk.michaelbui.ezbud.account.repository;

import dk.michaelbui.ezbud.account.model.Account;

import java.util.Collection;

public interface AccountsRepository {
    Account getAccountById(String userId, String accountId);

    Collection<Account> getAllAccounts(String userId);

    Account createAccount(String userId, Account account);

    Account updateAccount(String userId, Account updatedAccount);
}
