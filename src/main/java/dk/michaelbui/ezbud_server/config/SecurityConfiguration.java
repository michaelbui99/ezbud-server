package dk.michaelbui.ezbud_server.config;

import dk.michaelbui.ezbud_server.config.properties.CorsProperties;
import dk.michaelbui.ezbud_server.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final CorsProperties corsProperties;

    @Autowired
    public SecurityConfiguration(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @Bean
    SecurityFilterChain resourceServerSecurityFilterChain(
            HttpSecurity http,
            Converter<Jwt, AbstractAuthenticationToken> authenticationConverter) throws Exception {
        http.oauth2ResourceServer(resourceServer ->
                resourceServer.jwt(jwtDecoder -> jwtDecoder.jwtAuthenticationConverter(authenticationConverter))
        );

        http.cors(Customizer.withDefaults());

        http
                .sessionManagement(sessions -> sessions.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(requests ->
                requests.requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/swagger-ui/**"
                        ).permitAll()
                        .requestMatchers(
                                "api/v1/users"
                        ).hasAuthority(Role.ADMIN.authority())
                        .anyRequest().authenticated()
        );

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration c = new CorsConfiguration();
        c.setAllowedOrigins(corsProperties.getAllowedOrigins());
        c.setAllowedHeaders(corsProperties.getAllowedHeaders());
        c.setAllowedMethods(corsProperties.getAllowedMethods());

        UrlBasedCorsConfigurationSource s = new UrlBasedCorsConfigurationSource();
        s.registerCorsConfiguration("/**", c);
        return s;
    }


    @Bean
    @SuppressWarnings("unchecked")
    public AuthoritiesConverter authoritiesConverter() {
        return claims -> {
            var realmAccess = Optional.ofNullable((Map<String, Object>) claims.get("realm_access"));
            var roles = realmAccess.flatMap(map -> Optional.ofNullable((List<String>) map.get("roles")));
            return roles.stream().flatMap(Collection::stream)
                    .map(SimpleGrantedAuthority::new)
                    .map(GrantedAuthority.class::cast)
                    .toList();
        };
    }

    @Bean
    JwtAuthenticationConverter authenticationConverter(AuthoritiesConverter authoritiesConverter) {
        var authenticationConverter = new JwtAuthenticationConverter();
        authenticationConverter.setJwtGrantedAuthoritiesConverter(jwt ->
                authoritiesConverter.convert(jwt.getClaims())
        );
        return authenticationConverter;
    }
}
