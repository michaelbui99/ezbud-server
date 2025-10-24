package dk.michaelbui.ezbud_server.domain.repository;

import dk.michaelbui.ezbud_server.domain.model.User;

import java.util.Collection;

public interface UserRepository {
    void ensureUser(String id, String name);
    Collection<User> getUsers();
}
