package dk.michaelbui.ezbud_server.api.controllers;

import dk.michaelbui.ezbud_server.api.dtos.UserInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/me")
public class MeController {

    @GetMapping
    public ResponseEntity<UserInfoDto> getInfo(JwtAuthenticationToken auth) {
        String id = auth.getToken().getClaimAsString(StandardClaimNames.SUB);
        String preferredName = auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME);
        List<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        UserInfoDto dto = new UserInfoDto();
        dto.setId(id);
        dto.setName(preferredName);
        dto.setAuthorities(authorities);
        return ResponseEntity.ok(dto);
    }
}
