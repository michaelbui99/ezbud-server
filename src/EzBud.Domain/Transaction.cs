namespace EzBud.Domain;

public class Transaction(
    DateTimeOffset date,
    string payee,
    string category,
    decimal payment,
    decimal deposit,
    bool cleared = false)
{
    public DateTimeOffset Date { get; set; } = date;
    public string Payee { get; set; } = payee;
    public string? Notes { get; set; }
    public string Category { get; set; } = category;
    public decimal Payment { get; set; } = payment;
    public decimal Deposit { get; set; } = deposit;
    public bool Cleared { get; set; } = cleared;
}