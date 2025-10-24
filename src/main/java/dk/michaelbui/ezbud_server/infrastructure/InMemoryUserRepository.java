package dk.michaelbui.ezbud_server.infrastructure;

import dk.michaelbui.ezbud_server.domain.model.User;
import dk.michaelbui.ezbud_server.domain.repository.UserRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void ensureUser(String id, String name) {
        users.put(id, new User(id, name));
    }

    @Override
    public Collection<User> getUsers() {
        return users.values();
    }
}
