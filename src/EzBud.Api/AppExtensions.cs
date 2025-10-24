using EzBud.Infrastructure;

namespace EzBud.Api;

public static class AppExtensions
{
    public static WebApplication ConfigureCors(this WebApplication app, ConfigurationManager configuration)
    {
        CorsOptions? corsOptions = configuration.GetSection(CorsOptions.Section).Get<CorsOptions>();
        if (corsOptions is null)
        {
            throw new ConfigurationException("Missing Cors configuration");
        }

        app.UseCors(opts =>
        {
            opts
                .WithOrigins(corsOptions.AllowedOrigins.ToArray())
                .WithHeaders(corsOptions.AllowedHeaders.ToArray())
                .WithMethods(corsOptions.AllowedMethods.ToArray());
        });

        return app;
    }
}