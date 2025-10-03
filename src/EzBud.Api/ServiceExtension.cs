using System.Text.Json;
using EzBud.Infrastructure;
using Microsoft.IdentityModel.Tokens;

namespace EzBud.Api;

public static class ServiceExtension
{
    public static IServiceCollection AddServices(this IServiceCollection services, ConfigurationManager configuration)
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

    private static IServiceCollection AddAuth(this IServiceCollection services,
        ConfigurationManager configurationManager)
    {
        JwtOptions? jwtOptions = configurationManager.GetSection(JwtOptions.Section).Get<JwtOptions>();
        if (jwtOptions is null)
        {
            throw new ConfigurationException("JWT options has not been configured");
        }

        if (jwtOptions.Audience is null || jwtOptions.MetadataAddress is null || jwtOptions.ValidIssuer is null)
        {
            throw new ConfigurationException("JWT options has not been configured");
        }

        services.AddAuthorization();
        services.AddAuthentication()
            .AddJwtBearer(opts =>
            {
                opts.RequireHttpsMetadata = false;
                opts.Audience = jwtOptions.Audience;
                opts.MetadataAddress = jwtOptions.MetadataAddress;
                opts.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidIssuer = jwtOptions.ValidIssuer,
                    ValidateIssuerSigningKey = true,
                    ValidateAudience = false,
                    ValidateLifetime = true,
                    ValidateIssuer = true
                };
            });

        return services;
    }
}