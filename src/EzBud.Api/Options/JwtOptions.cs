namespace EzBud.Api;

public class JwtOptions
{
    public const string Section = "Jwt";
    public string? MetadataAddress { get; set; }
    public string? ValidIssuer { get; set; }
    public string? Audience { get; set; }
}