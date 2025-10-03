using System.Net;
using EzBud.Api.Controllers.Account.Dtos;
using EzBud.Domain;
using EzBud.Domain.Account;
using Microsoft.AspNetCore.Mvc;

namespace EzBud.Api.Controllers.Account;

[ApiController]
[Route("api/v1/accounts")]
public class AccountsController(ILogger<AccountsController> logger, IAccountService accountService) : ControllerBase
{
    [HttpGet]
    public async Task<ActionResult<ICollection<Domain.Account.Account>>> GetAccounts()
    {
        try
        {
            var accounts = await accountService.GetAllAccountsAsync("test");
            return Ok(accounts);
        }
        catch (Exception e)
        {
            logger.LogError(e, "Failed to fetch accounts");
            return StatusCode(HttpStatusCode.InternalServerError.AsInt());
        }
    }

    [HttpPost]
    public async Task<ActionResult<Domain.Account.Account>> CreateAccount([FromBody] CreateAccountDto dto)
    {
        var result = await accountService.CreateAccountAsync("test", dto.Name, dto.OnBudget);
        if (result.IsSuccess) return Ok(result.CreatedEntity);
        if (result.Exception is DomainException)
        {
            return BadRequest(result.Exception.Message);
        }

        logger.LogError(result.Exception, "Failed to create account");
        return BadRequest();

    }
}