package dk.michaelbui.ezbud.account;

import dk.michaelbui.ezbud.DomainActionResult;
import dk.michaelbui.ezbud.DomainActionResultWithEntity;
import dk.michaelbui.ezbud.account.model.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountsService {
    Optional<Account> getAccountById(String userId, String accountId);

    Collection<Account> getAllAccounts(String userId);

    DomainActionResultWithEntity<Account> createAccount(String userId, String name, boolean onBudget);

    DomainActionResult updateAccount(String userId, Account updatedAccount);
}
