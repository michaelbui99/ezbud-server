package dk.michaelbui.ezbud_server.api.controllers;

import dk.michaelbui.ezbud_server.api.AuthUtil;
import dk.michaelbui.ezbud_server.api.dtos.CreateAccountDto;
import dk.michaelbui.ezbud_server.domain.model.Account;
import dk.michaelbui.ezbud_server.domain.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody CreateAccountDto dto, JwtAuthenticationToken auth) {
        Optional<String> userId = AuthUtil.getUserId(auth);
        return userId
                .map(id -> ResponseEntity.ok(accountService.createAccount(id, dto.getName(), dto.isOnBudget())))
                .orElseGet(() -> ResponseEntity.status(HttpStatusCode.valueOf(401)).build());
    }

    @GetMapping
    public ResponseEntity<Collection<Account>> getAccounts(JwtAuthenticationToken auth) {
        Optional<String> userId = AuthUtil.getUserId(auth);
        return userId
                .map(id -> ResponseEntity.ok(accountService.getAllAccounts(id)))
                .orElseGet(() -> ResponseEntity.status(HttpStatusCode.valueOf(401)).build());
    }


    @GetMapping("{accountId}")
    public ResponseEntity<Account> getAccounts(@PathVariable UUID accountId, JwtAuthenticationToken auth) {
        Optional<String> userId = AuthUtil.getUserId(auth);
        return userId
                .map(id -> accountService.getAccountById(accountId)
                        .map(ResponseEntity::ok)
                        .orElse(ResponseEntity.notFound().build())
                )
                .orElseGet(() -> ResponseEntity.status(HttpStatusCode.valueOf(401)).build());
    }
}
