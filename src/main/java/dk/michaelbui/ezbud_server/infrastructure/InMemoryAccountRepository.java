package dk.michaelbui.ezbud_server.infrastructure;

import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

public class InMemoryAccountRepository implements AccountRepository {
    private Map<UUID, Account> accounts = new HashMap<>();
    private Map<String, Collection<Account>> accountsByUsername = new HashMap<>();

    @Override
    public Account getAccountById(UUID id) {
        return accounts.getOrDefault(id, null);
    }

    @Override
    public Collection<Account> getAllAccountsByUserId(String userId) {
        return accountsByUsername.getOrDefault(userId, new ArrayList<>());
    }

    @Override
    public Account createAccount(String userId, Account account) {
        accounts.put(account.getId(), account);
        accountsByUsername
                .computeIfAbsent(userId, k -> new ArrayList<>())
                .add(account);
        return account;
    }

    @Override
    public void updateAccount(String userId, UUID id, Account account) {
        accounts.put(id, account);
        accountsByUsername.computeIfAbsent(userId, k -> new ArrayList<>());
        Collection<Account> usersAccounts = accountsByUsername.get(userId);
        for (Account acc : usersAccounts) {
            if (acc.getId().equals(id)) {
                usersAccounts.remove(acc);
                break;
            }
        }
        usersAccounts.add(account);
    }

    @Override
    public boolean userHasAccount(String userId, UUID accountId) {
        return accountsByUsername
                .computeIfAbsent(userId, k -> new ArrayList<>())
                .stream()
                .anyMatch(account -> account.getId().equals(accountId));
    }
}
