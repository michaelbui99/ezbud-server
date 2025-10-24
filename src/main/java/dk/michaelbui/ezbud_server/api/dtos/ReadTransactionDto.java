package dk.michaelbui.ezbud_server.api.dtos;

import dk.michaelbui.ezbud_server.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class ReadTransactionDto {
    private UUID id;
    private OffsetDateTime date;
    private String payee;
    private String category;
    private BigDecimal payment;
    private BigDecimal deposit;
    private boolean cleared;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public OffsetDateTime getDate() { return date; }
    public void setDate(OffsetDateTime date) { this.date = date; }

    public String getPayee() { return payee; }
    public void setPayee(String payee) { this.payee = payee; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public BigDecimal getPayment() { return payment; }
    public void setPayment(BigDecimal payment) { this.payment = payment; }

    public BigDecimal getDeposit() { return deposit; }
    public void setDeposit(BigDecimal deposit) { this.deposit = deposit; }

    public boolean isCleared() { return cleared; }
    public void setCleared(boolean cleared) { this.cleared = cleared; }

    public static ReadTransactionDto from(Transaction src) {
        if (src == null) return null;
        ReadTransactionDto dto = new ReadTransactionDto();
        dto.setId(src.getId());
        dto.setDate(src.getDate());
        dto.setPayee(src.getPayee());
        dto.setCategory(src.getCategory());
        dto.setPayment(src.getPayment());
        dto.setDeposit(src.getDeposit());
        dto.setCleared(src.isCleared());
        return dto;
    }
}
