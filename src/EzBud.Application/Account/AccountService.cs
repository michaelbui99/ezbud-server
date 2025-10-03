using EzBud.Domain;
using EzBud.Domain.Account;
using EzBud.Infrastructure.Data;
using EzBud.Infrastructure.Data.Account;
using Microsoft.Extensions.Logging;

namespace EzBud.Application.Account;

public class AccountService(ILogger<AccountService> logger, IAccountRepository accountRepository) : IAccountService
{
    public async Task<Domain.Account.Account?> GetAccountByIdAsync(string userId, Guid accountId)
    {
        return await accountRepository.GetAccountByIdAsync(userId, accountId);
    }

    public async Task<ICollection<Domain.Account.Account>> GetAllAccountsAsync(string userId)
    {
        return await accountRepository.GetAllAccountsAsync(userId);
    }

    public async Task<CreateEntityResult<Domain.Account.Account>> CreateAccountAsync(string userId, string name,
        bool onBudget)
    {
        DomainException? nameValidationException = AccountNameValidator.Validate(name);
        if (nameValidationException is not null)
        {
            return CreateEntityResult<Domain.Account.Account>.Failure(nameValidationException);
        }

        logger.LogDebug("Creating account '{}' for user {}", name, userId);
        try
        {
            Domain.Account.Account account = new(Guid.NewGuid(), name, onBudget);
            await accountRepository.CreateAccountAsync(userId, account);
            return CreateEntityResult<Domain.Account.Account>.Success(account);
        }
        catch (Exception e)
        {
            logger.LogError(e, "Error creating account '{}' for user {}", name, userId);
            return CreateEntityResult<Domain.Account.Account>.Failure(e);
        }
    }

    public async Task<DomainActionResult> UpdateAccountAsync(string userId, Domain.Account.Account updatedAccount)
    {
        Domain.Account.Account? existingAccount = await GetAccountByIdAsync(userId, updatedAccount.Id);
        if (existingAccount == null)
        {
            logger.LogDebug("No account '{}({})' exists for user '{}'. Creating new account.", updatedAccount.Name,
                updatedAccount.Id, userId);
            DomainActionResult createAccountResult =
                await CreateAccountAsync(userId, updatedAccount.Name, updatedAccount.OnBudget);
            return createAccountResult;
        }

        DomainException? nameValidationException = AccountNameValidator.Validate(updatedAccount.Name);
        if (nameValidationException is not null)
        {
            return DomainActionResult.Failure(nameValidationException);
        }

        try
        {
            logger.LogDebug("Updating account '{}({})' for user {}", updatedAccount.Name, updatedAccount.Id, userId);
            await accountRepository.UpdateAccountAsync(updatedAccount);
            return DomainActionResult.Success();
        }
        catch (Exception e)
        {
            logger.LogError(e, "Failed to update account '{}({})' for user {}", updatedAccount.Name, updatedAccount.Id,
                userId);
            return DomainActionResult.Failure(e);
        }
    }
}