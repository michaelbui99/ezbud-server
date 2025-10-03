namespace EzBud.Infrastructure.Data.Account;

public interface IAccountRepository
{
    Task<Domain.Account.Account?> GetAccountByIdAsync(Guid accountId);
    Task<ICollection<Domain.Account.Account>> GetAllAccountsAsync(string userId);
    Task CreateAccountAsync(string userId, Domain.Account.Account account);
    Task UpdateAccountAsync(Domain.Account.Account account);
}


