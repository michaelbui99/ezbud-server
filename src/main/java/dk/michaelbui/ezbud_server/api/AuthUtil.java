package dk.michaelbui.ezbud_server.api;

import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

public class AuthUtil {
    private AuthUtil() {}

    public static Optional<String> getUserId(JwtAuthenticationToken auth){
        if (auth == null || !auth.isAuthenticated()){
            return Optional.empty();
        }
        return Optional.ofNullable(auth.getToken().getClaimAsString(StandardClaimNames.SUB));
    }
}
