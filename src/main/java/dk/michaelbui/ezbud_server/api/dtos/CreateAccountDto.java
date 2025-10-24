package dk.michaelbui.ezbud_server.api.dtos;

public class CreateAccountDto {
    private String name;
    private boolean isOnBudget;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnBudget() {
        return isOnBudget;
    }

    public void setOnBudget(boolean onBudget) {
        isOnBudget = onBudget;
    }
}
