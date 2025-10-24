package dk.michaelbui.ezbud_server.config;

import dk.michaelbui.ezbud_server.domain.repository.AccountRepository;
import dk.michaelbui.ezbud_server.domain.repository.UserRepository;
import dk.michaelbui.ezbud_server.domain.service.AccountService;
import dk.michaelbui.ezbud_server.domain.service.AccountServiceImpl;
import dk.michaelbui.ezbud_server.infrastructure.InMemoryAccountRepository;
import dk.michaelbui.ezbud_server.infrastructure.InMemoryUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public AccountService accountService() {
        return new AccountServiceImpl(accountRepository());
    }

    @Bean
    public AccountRepository accountRepository() {
        return new InMemoryAccountRepository();
    }

    @Bean
    public UserRepository userRepository() {
        return new InMemoryUserRepository();
    }

}
