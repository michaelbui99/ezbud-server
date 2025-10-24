using System.Security.Claims;
using System.Text.Json;
using EzBud.Infrastructure;
using EzBud.Infrastructure.Data.User;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;

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
        services.AddSwaggerGen(options =>
        {
            options.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
            {
                Name = "Authorization",
                In = ParameterLocation.Header,
                Type =SecuritySchemeType.ApiKey,
                Scheme = "Bearer"
            });
        } );
        services.AddHttpLogging();
        services.AddAuth(configuration);
        return services;
    }

    private static IServiceCollection AddAuth(this IServiceCollection services,
        ConfigurationManager configuration)
    {
        JwtOptions? jwtOptions = configuration.GetSection(JwtOptions.Section).Get<JwtOptions>();
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
                opts.Events = new JwtBearerEvents()
                {
                    OnAuthenticationFailed = context =>
                    {
                        var logger = context.HttpContext.RequestServices.GetRequiredService<ILogger<JwtBearerEvents>>();
                        logger.LogInformation("Auth failed for: {}", context.Result.Failure);
                        return Task.CompletedTask;
                    },
                    OnTokenValidated = context =>
                    {
                        if (context.Principal is null)
                        {
                            return Task.CompletedTask;
                        }

                        var userId = context.Principal.Claims.FirstOrDefault(c => c.Type == ClaimTypes.NameIdentifier)
                            ?.Value;
                        if (userId is null)
                        {
                            return Task.CompletedTask;
                        }

                        var logger = context.HttpContext.RequestServices.GetRequiredService<ILogger<JwtBearerEvents>>();
                        var userRepository = context.HttpContext.RequestServices.GetRequiredService<IUserRepository>();
                        if (userRepository.UserExists(userId))
                        {
                            return Task.CompletedTask;
                        }

                        var name = context.Principal.Claims.FirstOrDefault(c => c.Type == "name")?.Value;
                        if (name is null)
                        {
                            return Task.CompletedTask;
                        }

                        logger.LogInformation("Creating new user {}.", userId);
                        userRepository.CreateUser(userId, name);
                        return Task.CompletedTask;
                    }
                };
            });

        return services;
    }
}