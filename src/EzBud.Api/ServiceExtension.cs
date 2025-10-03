using System.Text.Json;

namespace EzBud.Api;

public static class ServiceExtension
{
    public static IServiceCollection AddServices(this IServiceCollection services)
    {
        services.AddOpenApi();
        services.AddControllers();
        services.AddControllers().AddJsonOptions(opts =>
        {
            opts.JsonSerializerOptions.PropertyNamingPolicy = JsonNamingPolicy.CamelCase;
        });
        services.AddEndpointsApiExplorer();
        services.AddHttpClient(Constants.HttpClient).ConfigureHttpClient(client =>
        {
            client.DefaultRequestHeaders.Add("Accept", "application/json");
            client.Timeout = TimeSpan.FromSeconds(30);
        });
        services.AddSwaggerGen();
        services.AddHttpLogging();
        return services;
    }
}