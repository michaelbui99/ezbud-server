namespace EzBud.Domain.Account;

public static class AccountNameValidator
{
    public static DomainException? Validate(string name)
    {
        if (name.Length is 0 || string.IsNullOrWhiteSpace(name))
        {
            return new DomainException("Name must not be empty");
        }

        return name.Length > Domain.Account.Account.MaxNameLength ? new DomainException("Name must not exceed 70 characters") : null;
    }
}