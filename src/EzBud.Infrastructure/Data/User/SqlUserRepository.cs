using Dapper;
using EzBud.Infrastructure.Options;
using Microsoft.Extensions.Options;
using Npgsql;

namespace EzBud.Infrastructure.Data.User;

public class SqlUserRepository : IUserRepository
{
    private readonly string _connectionString;

    public SqlUserRepository(IOptions<DbOptions> dbOptions)
    {
        _connectionString = dbOptions.Value.ConnectionString ??
                            throw new ConfigurationException("DB Connection string has not been configured.");
    }

    public void CreateUser(string id, string name)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        connection.Execute("INSERT INTO ezbud.users(id, name) VALUES (@Id, @Name)", new
        {
            Id = id,
            Name = name
        });
    }

    public bool UserExists(string id)
    {
        using var connection = new NpgsqlConnection(_connectionString);
        return connection.Query<string>("SELECT id from ezbud.users WHERE id = @Id",
            new { Id = id }).Any();
    }
}