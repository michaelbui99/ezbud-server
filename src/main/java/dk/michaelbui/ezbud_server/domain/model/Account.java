package dk.michaelbui.ezbud_server.domain.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Account {
    private UUID id = UUID.randomUUID();
    private String name;
    private boolean isOnBudget;
    private Collection<Transaction> transactions = new ArrayList<>();

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

    public boolean isOnBudget() {
        return isOnBudget;
    }

    public void setOnBudget(boolean onBudget) {
        isOnBudget = onBudget;
    }

    public Collection<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<Transaction> transactions) {
        this.transactions = transactions;
    }
}
