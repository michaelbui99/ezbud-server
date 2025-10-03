namespace EzBud.Infrastructure.Options;

public class DbOptions
{
    public const string Section = "Db";

    public string? ConnectionString { get; set; }
}