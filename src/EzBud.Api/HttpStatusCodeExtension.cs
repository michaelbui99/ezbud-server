using System.Net;

namespace EzBud.Api;

public static class HttpStatusCodeExtension
{
    public static int AsInt(this HttpStatusCode statusCode)
    {
        return (int)statusCode;
    }
}