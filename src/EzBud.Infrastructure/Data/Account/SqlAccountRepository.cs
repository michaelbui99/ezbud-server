using Dapper;
using Dapper.Contrib.Extensions;
using EzBud.Infrastructure.Options;
using Microsoft.Extensions.Options;
using Npgsql;

namespace EzBud.Infrastructure.Data.Account;

public class SqlAccountRepository : IAccountRepository
{
    private readonly string _connectionString;

    public SqlAccountRepository(IOptions<DbOptions> dbOptions)
    {
        _connectionString = dbOptions.Value.ConnectionString ??
                            throw new ConfigurationException("DB Connection string has not been configured.");
    }

    public async Task<Domain.Account.Account?> GetAccountByIdAsync(Guid accountId)
    {
        await using NpgsqlConnection connection = new(_connectionString);
        AccountDto? dto =
            await connection.QueryFirstOrDefaultAsync<AccountDto>(AccountsSql.AccountById, new { Id = accountId });
        if (dto is null)
        {
            return null;
        }

        if (dto.name is null)
        {
            throw new DataException("Account name is null");
        }

        return new Domain.Account.Account(dto.id, dto.name, dto.on_budget);
    }

    public async Task<ICollection<Domain.Account.Account>> GetAllAccountsAsync(string userId)
    {
        await using NpgsqlConnection connection = new(_connectionString);
        return (await connection.QueryAsync<AccountDto>(AccountsSql.GetAccounts, new { UserId = userId }))
            .Where(dto => dto.name is not null)
            .Select(dto => new Domain.Account.Account(dto.id, dto.name!, dto.on_budget))
            .ToList();
    }

    public async Task CreateAccountAsync(string userId, Domain.Account.Account account)
    {
        await using NpgsqlConnection connection = new(_connectionString);
        await connection.ExecuteAsync("INSERT INTO ezbud.accounts (id, name, on_budget) VALUES (@Id, @Name, @OnBudget)",
            new
            {
                Id = account.Id,
                Name = account.Name,
                OnBudget = account.OnBudget
            }
        );
        await connection.ExecuteAsync(
            "INSERT INTO ezbud.user_account(user_id, account_id) VALUES(@UserId, @AccountId)",
            new
            {
                UserId = userId,
                AccountId = account.Id
            }
        );
    }

    public Task UpdateAccountAsync(Domain.Account.Account account)
    {
        throw new NotImplementedException();
    }
}