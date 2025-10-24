package dk.michaelbui.ezbud_server.api.dtos;

import dk.michaelbui.ezbud_server.domain.model.Account;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReadAccountDto {
    private UUID id;
    private String name;
    private boolean onBudget;
    private Collection<ReadTransactionDto> transactions;

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
        return onBudget;
    }

    public void setOnBudget(boolean onBudget) {
        this.onBudget = onBudget;
    }

    public Collection<ReadTransactionDto> getTransactions() {
        return transactions;
    }

    public void setTransactions(Collection<ReadTransactionDto> transactions) {
        this.transactions = transactions;
    }

    public static ReadAccountDto from(Account src) {
        if (src == null) {
            return null;
        }
        ReadAccountDto dto = new ReadAccountDto();
        dto.setId(src.getId());
        dto.setName(src.getName());
        dto.setOnBudget(src.isOnBudget());
        dto.setTransactions(
                src.getTransactions()
                        .stream()
                        .map(ReadTransactionDto::from)
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
