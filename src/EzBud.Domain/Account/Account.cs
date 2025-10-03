namespace EzBud.Domain.Account;

public class Account(Guid id, string name, bool onBudget = true)
{
    public const int MaxNameLength = 70;

    public Guid Id { get; set; } = id;
    public string Name { get; set; } = name;
    public bool OnBudget { get; set; } = onBudget;
    public ICollection<Transaction> Transactions { get; set; } = new List<Transaction>();
}