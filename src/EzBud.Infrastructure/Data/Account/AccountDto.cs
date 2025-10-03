using System.ComponentModel.DataAnnotations.Schema;
using Dapper.Contrib.Extensions;

namespace EzBud.Infrastructure.Data.Account;

[Dapper.Contrib.Extensions.Table("accounts")]
public class AccountDto
{
     public Guid id { get; set; }
     public string? name { get; set; }
     public bool on_budget { get; set; }
}