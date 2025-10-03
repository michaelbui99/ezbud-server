using EzBud.Infrastructure.Data;
using EzBud.Infrastructure.Data.Account;
using Microsoft.Extensions.DependencyInjection;

namespace EzBud.Infrastructure;

public static class ServiceExtension
{
    public static IServiceCollection AddInfrastructureServices(this IServiceCollection services)
    {
        Dapper.DefaultTypeMap.MatchNamesWithUnderscores = true;
        services.AddScoped<IAccountRepository, SqlAccountRepository>();
        return services;
    }
}