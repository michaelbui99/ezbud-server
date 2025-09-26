package dk.michaelbui.ezbud.account.model;

public class Payee {
    private String name;
    private String id;

    public String getName() {
        if (name == null && id != null) {
            return getId();
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        if (id == null && name != null) {
            return getName();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
