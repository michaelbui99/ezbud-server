using EzBud.Domain.Account;
using JetBrains.Annotations;
using Xunit;

namespace EzBud.Domain.Tests.Account;

[TestSubject(typeof(AccountNameValidator))]
public class AccountNameValidatorTest
{
    [Theory]
    [InlineData("")]
    [InlineData(" ")]
    [InlineData("     ")]
    public void Validate_NameIsEmpty_ReturnsDomainException(string name)
    {
        //Act
        DomainException? domainException = AccountNameValidator.Validate(name);

        // Assert
        Assert.NotNull(domainException);
        Assert.IsType<DomainException>(domainException);
    }
}