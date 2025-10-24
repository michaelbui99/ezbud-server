package dk.michaelbui.ezbud_server.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class CategoryGroup {
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean isIncomeGroup;
    private Collection<Category> categories = new ArrayList<>();

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

    public boolean isIncomeGroup() {
        return isIncomeGroup;
    }

    public void setIncomeGroup(boolean incomeGroup) {
        isIncomeGroup = incomeGroup;
    }

    public Collection<Category> getCategories() {
        return categories;
    }

    public void setCategories(Collection<Category> categories) {
        this.categories = categories;
    }
}
