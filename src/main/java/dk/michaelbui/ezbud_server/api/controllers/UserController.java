package dk.michaelbui.ezbud_server.api.controllers;

import dk.michaelbui.ezbud_server.domain.model.User;
import dk.michaelbui.ezbud_server.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getUsers(){
        return ResponseEntity.ok(userRepository.getUsers());
    }
}
