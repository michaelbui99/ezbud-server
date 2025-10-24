package dk.michaelbui.ezbud_server.domain.model;

import java.util.UUID;

public class Category {
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean isIncome;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return isIncome;
    }

    public void setIncome(boolean income) {
        isIncome = income;
    }
}
