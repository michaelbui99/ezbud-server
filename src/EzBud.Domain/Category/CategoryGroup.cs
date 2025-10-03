namespace EzBud.Domain.Category;

public class CategoryGroup(Guid id, string name, bool isIncomeGroup = false)
{
    public Guid Id { get; set; } = id;
    public string Name { get; set; } = name;
    public bool IsIncomeGroup { get; set; } = isIncomeGroup;
    public ICollection<Category> Categories { get; set; } = new List<Category>();
}