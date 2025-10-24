package dk.michaelbui.ezbud_server.infrastructure;

import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.domain.repository.AccountRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static dk.michaelbui.ezbud_server.generated.Tables.ACCOUNTS;
import static dk.michaelbui.ezbud_server.generated.Tables.USER_ACCOUNT;
import static org.jooq.impl.DSL.select;

public class SqlAccountsRepository implements AccountRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SqlAccountsRepository.class);
    private final ConnectionProvider connectionProvider;

    public SqlAccountsRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public Account getAccountById(UUID id) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(ACCOUNTS).where(ACCOUNTS.ID.eq(id)).fetch();
            return result.stream()
                    .findAny()
                    .map(this::accountFromRecord)
                    .orElse(null);
        } catch (SQLException e) {
            LOG.error("Failed to retrieve account {}", id, e);
            throw new InfrastructureException("Failed to retrieve account " + id, e);
        }
    }

    @Override
    public Collection<Account> getAllAccountsByUserId(String userId) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            Result<Record> result = create.select()
                    .from(ACCOUNTS)
                    .where(ACCOUNTS.ID.in(
                            select(USER_ACCOUNT.ACCOUNT_ID)
                                    .from(USER_ACCOUNT)
                                    .where(USER_ACCOUNT.USER_ID.eq(userId)))
                    )
                    .fetch();
            return result.stream()
                    .map(this::accountFromRecord)
                    .toList();
        } catch (SQLException e) {
            LOG.error("Failed to retrieve accounts for user {}", userId, e);
            throw new InfrastructureException("Failed to retrieve accounts for user  " + userId, e);
        }
    }

    @Override
    public Account createAccount(String userId, Account account) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            create.transaction(trx -> {
                trx.dsl()
                        .insertInto(ACCOUNTS, ACCOUNTS.ID, ACCOUNTS.NAME, ACCOUNTS.ON_BUDGET)
                        .values(account.getId(), account.getName(), account.isOnBudget())
                        .execute();
                trx.dsl()
                        .insertInto(USER_ACCOUNT, USER_ACCOUNT.ACCOUNT_ID, USER_ACCOUNT.USER_ID)
                        .values(account.getId(), userId)
                        .execute();
            });
            return account;
        } catch (SQLException e) {
            LOG.error("Failed to create account", e);
            throw new InfrastructureException("Failed to create account", e);
        }
    }

    @Override
    public void updateAccount(String userId, UUID id, Account account) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            create.update(ACCOUNTS)
                    .set(ACCOUNTS.NAME, account.getName())
                    .set(ACCOUNTS.ON_BUDGET, account.isOnBudget())
                    .where(ACCOUNTS.ID.eq(id))
                    .execute();
        } catch (SQLException e) {
            LOG.error("Failed to update account {}", account.getId(), e);
            throw new InfrastructureException("Failed to update account " + id, e);
        }
    }

    @Override
    public boolean userHasAccount(String userId, UUID accountId) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            Result<Record> result = create
                    .select()
                    .from(USER_ACCOUNT)
                    .where(USER_ACCOUNT.USER_ID.eq(userId).and(USER_ACCOUNT.ACCOUNT_ID.eq(accountId)))
                    .fetch();
            return result.stream()
                    .findAny()
                    .isPresent();
        } catch (SQLException e) {
            LOG.error("Failed to retrieve account {} for user {}", accountId, userId, e);
            throw new InfrastructureException(String.format("Failed to retrieve account %s for user %s", accountId, userId), e);
        }
    }

    private Account accountFromRecord(Record record) {
        Account account = new Account();
        account.setId(record.get(ACCOUNTS.ID));
        account.setName(record.get(ACCOUNTS.NAME));
        account.setOnBudget(record.get(ACCOUNTS.ON_BUDGET));
        account.setTransactions(new ArrayList<>());
        return account;
    }
}
