package dk.michaelbui.ezbud_server.api.dtos;

import java.util.Collection;

public class UserInfoDto {
    private String id;
    private String name;
    private Collection<String> authorities;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<String> authorities) {
        this.authorities = authorities;
    }
}
