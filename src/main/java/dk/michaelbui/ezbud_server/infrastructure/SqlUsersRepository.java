package dk.michaelbui.ezbud_server.infrastructure;

import dk.michaelbui.ezbud_server.domain.model.User;
import dk.michaelbui.ezbud_server.domain.repository.UserRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static dk.michaelbui.ezbud_server.generated.Tables.USERS;

public class SqlUsersRepository implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(SqlUsersRepository.class);
    private final ConnectionProvider connectionProvider;

    public SqlUsersRepository(ConnectionProvider connectionProvider) {
        this.connectionProvider = connectionProvider;
    }

    @Override
    public void ensureUser(String id, String name) {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            create.insertInto(USERS, USERS.ID, USERS.NAME)
                    .values(id, name)
                    .onDuplicateKeyUpdate()
                    .set(USERS.NAME, name)
                    .execute();
        } catch (SQLException e) {
            LOG.error("Failed to ensure user is created", e);
            throw new InfrastructureException("Failed to ensure user is created", e);
        }
    }

    @Override
    public Collection<User> getUsers() {
        try (Connection connection = connectionProvider.getConnection()) {
            DSLContext create = DSL.using(connection, SQLDialect.POSTGRES);
            Result<Record> result = create.select().from(USERS).fetch();
            return result.stream()
                    .map(record -> new User(record.get(USERS.ID), record.get(USERS.NAME)))
                    .toList();
        } catch (SQLException e) {
            LOG.error("Failed to retrieve users", e);
            throw new InfrastructureException("Failed to retrieve users", e);
        }
    }
}
