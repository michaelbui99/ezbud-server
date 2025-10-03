// See https://aka.ms/new-console-template for more information

using System.Reflection;
using DbUp;
using DbUp.Engine;

try
{
    Console.WriteLine("DbUp");
    var connectionString = args.FirstOrDefault();

    if (connectionString is null)
    {
        Console.WriteLine("No connection string was provided. Skipping migrations.");
        return -1;
    }

    EnsureDatabase.For.PostgresqlDatabase(connectionString);
    UpgradeEngine upgrader = DeployChanges.To
        .PostgresqlDatabase(connectionString, "ezbud")
        .WithScriptsEmbeddedInAssembly(Assembly.GetExecutingAssembly())
        .LogToConsole()
        .Build();

    DatabaseUpgradeResult result = upgrader.PerformUpgrade();
    if (!result.Successful)
    {
        Console.ForegroundColor = ConsoleColor.Red;
        Console.WriteLine(result.Error);
        Console.ResetColor();
#if DEBUG
        Console.ReadLine();
#endif
        return -1;
    }

    Console.ForegroundColor = ConsoleColor.Green;
    Console.WriteLine("Success!");
    Console.ResetColor();
    return 0;
}
catch (Exception ex)
{
    Console.WriteLine($"DbUp failed: {ex.Message}");
    Console.WriteLine(ex.StackTrace);
    return -1;
}