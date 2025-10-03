namespace EzBud.Api;

public class CorsOptions
{
    public const string Section = "Cors";
    public ICollection<string> AllowedOrigins { get; set; }
    public ICollection<string> AllowedHeaders { get; set; }
    public ICollection<string> AllowedMethods { get; set; }
}