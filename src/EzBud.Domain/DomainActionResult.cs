namespace EzBud.Domain;

public class DomainActionResult
{
    public bool IsSuccess { get; private set; }
    public Exception? Exception { get; protected set; }

    public static DomainActionResult Success()
    {
        return new DomainActionResult
        {
            IsSuccess = true,
            Exception = null
        };
    }

    public static DomainActionResult Failure(Exception e)
    {
        return new DomainActionResult
        {
            IsSuccess = false,
            Exception = e
        };
    }
}