package dk.michaelbui.ezbud_server.api;

import dk.michaelbui.ezbud_server.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class AuthEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(AuthEventListener.class);
    private final UserRepository userRepository;

    @Autowired
    public AuthEventListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        if (event.getAuthentication() instanceof JwtAuthenticationToken auth) {
            String id = auth.getToken().getClaimAsString(StandardClaimNames.SUB);
            String name = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
            userRepository.ensureUser(id, name);
        }
    }
}
