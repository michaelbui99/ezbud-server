using Dapper.Contrib.Extensions;

namespace EzBud.Infrastructure.Data.User;

[Table("users")]
public class UserDto
{
    public string id { get; set; }
    public string name { get; set; }
}