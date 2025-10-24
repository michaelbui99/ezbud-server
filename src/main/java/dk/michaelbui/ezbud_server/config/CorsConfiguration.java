package dk.michaelbui.ezbud_server.config;

import dk.michaelbui.ezbud_server.config.properties.CorsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration {
    private final CorsProperties corsProperties;

    @Autowired
    public CorsConfiguration(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods(corsProperties.getAllowedMethods().toArray(new String[0]))
                        .allowedHeaders(corsProperties.getAllowedHeaders().toArray(new String[0]))
                        .allowedOrigins(corsProperties.getAllowedOrigins().toArray(new String[0]));
            }
        };
    }
}
