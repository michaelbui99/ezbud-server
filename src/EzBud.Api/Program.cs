using EzBud.Api;
using EzBud.Application;
using EzBud.Infrastructure;
using EzBud.Infrastructure.Options;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddServices();
builder.Services.AddApplicationServices();
builder.Services.AddInfrastructureServices();
builder.Services.Configure<DbOptions>(builder.Configuration.GetSection(DbOptions.Section));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}

SetupCors();

app.UseSwagger();
app.UseSwaggerUI();
app.MapControllers();

app.UseHttpLogging();
app.UseHttpsRedirection();

app.Run();
return;

void SetupCors()
{
    CorsOptions? corsOptions = builder.Configuration.GetSection(CorsOptions.Section).Get<CorsOptions>();
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
}