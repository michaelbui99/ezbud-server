namespace EzBud.Infrastructure.Data.User;

public interface IUserRepository
{
    void CreateUser(string id, string name);
    bool UserExists(string id);
}