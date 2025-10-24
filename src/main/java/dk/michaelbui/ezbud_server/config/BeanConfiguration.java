package dk.michaelbui.ezbud_server.config;

import dk.michaelbui.ezbud_server.domain.repository.AccountRepository;
import dk.michaelbui.ezbud_server.domain.repository.UserRepository;
import dk.michaelbui.ezbud_server.domain.service.AccountService;
import dk.michaelbui.ezbud_server.domain.service.AccountServiceImpl;
import dk.michaelbui.ezbud_server.infrastructure.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public AccountService accountService(ConnectionProvider connectionProvider) {
        return new AccountServiceImpl(accountRepository(connectionProvider));
    }

    @Bean
    public AccountRepository accountRepository(ConnectionProvider connectionProvider) {
        return new SqlAccountsRepository(connectionProvider);
    }

    @Bean
    public UserRepository userRepository(ConnectionProvider connectionProvider) {
        return new SqlUsersRepository(connectionProvider);
    }

}
