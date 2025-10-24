package dk.michaelbui.ezbud_server.domain;

public enum Role {
    USER("user"),
    ADMIN("admin");

    private String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }

    public String authority() {
        return role;
    }
}
