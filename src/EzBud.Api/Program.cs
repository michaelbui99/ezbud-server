using EzBud.Api;
using EzBud.Application;
using EzBud.Infrastructure;
using EzBud.Infrastructure.Options;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddApplicationServices();
builder.Services.AddInfrastructureServices();
builder.Services.AddServices(builder.Configuration);
builder.Services.Configure<DbOptions>(builder.Configuration.GetSection(DbOptions.Section));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
}
app.ConfigureCors(builder.Configuration);
app.UseSwagger();
app.UseSwaggerUI();
app.MapControllers();

app.UseHttpLogging();
app.UseHttpsRedirection();

app.Run();
return;
