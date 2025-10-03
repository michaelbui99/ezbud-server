namespace EzBud.Domain.Account;

public class CreateEntityResult<T> : DomainActionResult
{
    public bool IsSuccess => CreatedEntity != null;
    public T? CreatedEntity { get; private set; }

    public static CreateEntityResult<T> Success(T entity)
    {
        return new CreateEntityResult<T>
        {
            CreatedEntity = entity
        };
    }

    public static CreateEntityResult<T> Failure(Exception exception)
    {
        return new CreateEntityResult<T>
        {
            Exception = exception
        };
    }
}