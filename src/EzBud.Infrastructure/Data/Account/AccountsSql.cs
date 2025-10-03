namespace EzBud.Infrastructure.Data.Account;

public static class AccountsSql
{
    public const string GetAccounts = "SELECT id, name, on_budget FROM ezbud.accounts WHERE id in (SELECT account_id FROM ezbud.user_account where user_id = @UserId)";

    public const string AccountById = "SELECT id, name, on_budget FROM ezbud.accounts where id = @Id";
}