using EzBud.Application.Account;
using EzBud.Domain.Account;
using Microsoft.Extensions.DependencyInjection;

namespace EzBud.Application;

public static class ServiceExtension
{
    public static IServiceCollection AddApplicationServices(this IServiceCollection services)
    {
        services.AddTransient<IAccountService, AccountService>();
        return services;
    }
}