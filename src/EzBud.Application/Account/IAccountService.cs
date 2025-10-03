using EzBud.Domain;
using EzBud.Domain.Account;

namespace EzBud.Application.Account;

public interface IAccountService
{
    Task<Domain.Account.Account?> GetAccountByIdAsync(string userId, Guid accountId);
    Task<ICollection<Domain.Account.Account>> GetAllAccountsAsync(string userId);
    Task<CreateEntityResult<Domain.Account.Account>> CreateAccountAsync(string userId, string name, bool onBudget);
    Task<DomainActionResult> UpdateAccountAsync(string userId, Domain.Account.Account updatedAccount);
}