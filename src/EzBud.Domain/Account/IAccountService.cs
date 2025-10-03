namespace EzBud.Domain.Account;

public interface IAccountService
{
    Task<Account?> GetAccountByIdAsync(string userId, Guid accountId);
    Task<ICollection<Account>> GetAllAccountsAsync(string userId);
    Task<CreateEntityResult<Account>> CreateAccountAsync(string userId, string name, bool onBudget);
    Task<DomainActionResult> UpdateAccountAsync(string userId, Account updatedAccount);
}