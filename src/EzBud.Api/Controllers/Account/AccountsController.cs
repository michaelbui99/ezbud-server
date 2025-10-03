using System.Net;
using EzBud.Api.Controllers.Account.Dtos;
using EzBud.Application.Account;
using EzBud.Domain;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace EzBud.Api.Controllers.Account;

[Authorize]
[ApiController]
[Route("api/v1/accounts")]
// TODO: Convert to read DTOs instead of exposing the domain objects.
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

    [HttpGet("{accountId}")]
    public async Task<ActionResult<Domain.Account.Account>> GetAccountById(Guid accountId)
    {
        try
        {
            var account = await accountService.GetAccountByIdAsync("test", accountId);
            return account != null ? Ok(account) : NotFound();
        }
        catch (Exception e)
        {
            logger.LogError(e, "Failed to fetch account");
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