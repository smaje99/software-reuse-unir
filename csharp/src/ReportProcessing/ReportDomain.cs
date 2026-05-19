namespace ReportProcessing;

/// <summary>
/// Supported report categories and their file prefixes.
/// </summary>
public enum ReportCategory
{
    Entry,
    Output,
    Mixed
}

/// <summary>
/// Supported report types.
/// </summary>
public enum ReportType
{
    Invoice,
    Purchase,
    Order
}

/// <summary>
/// Immutable parser output.
/// </summary>
/// <param name="Category">Resolved report category.</param>
/// <param name="Type">Resolved report type.</param>
/// <param name="FileName">Original file name.</param>
public sealed record ReportMetadata(ReportCategory Category, ReportType Type, string FileName);

/// <summary>
/// Centralized mappings between enum values and the external Spanish domain
/// labels used by the activity.
/// </summary>
public static class ReportLabels
{
    public static string CategoryCode(ReportCategory category) => category switch
    {
        ReportCategory.Entry => "ENT",
        ReportCategory.Output => "SAL",
        ReportCategory.Mixed => "MIX",
        _ => throw new ArgumentOutOfRangeException(nameof(category), category, null)
    };

    public static string CategoryDisplayName(ReportCategory category) => category switch
    {
        ReportCategory.Entry => "ENTRADA",
        ReportCategory.Output => "SALIDA",
        ReportCategory.Mixed => "MIXTA",
        _ => throw new ArgumentOutOfRangeException(nameof(category), category, null)
    };

    public static string TypeCode(ReportType type) => type switch
    {
        ReportType.Invoice => "FAC",
        ReportType.Purchase => "COM",
        ReportType.Order => "PED",
        _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
    };

    public static string TypeDisplayName(ReportType type) => type switch
    {
        ReportType.Invoice => "FACTURA",
        ReportType.Purchase => "COMPRA",
        ReportType.Order => "PEDIDO",
        _ => throw new ArgumentOutOfRangeException(nameof(type), type, null)
    };

    public static ReportCategory CategoryFromCode(string code) => code switch
    {
        "ENT" => ReportCategory.Entry,
        "SAL" => ReportCategory.Output,
        "MIX" => ReportCategory.Mixed,
        _ => throw new ArgumentException($"Unsupported report category: {code}")
    };

    public static ReportType TypeFromCode(string code) => code switch
    {
        "FAC" => ReportType.Invoice,
        "COM" => ReportType.Purchase,
        "PED" => ReportType.Order,
        _ => throw new ArgumentException($"Unsupported report type: {code}")
    };
}

/// <summary>
/// Parses raw filenames into strongly typed metadata.
/// </summary>
public sealed class ReportParser
{
    public ReportMetadata Parse(string fileName)
    {
        if (string.IsNullOrWhiteSpace(fileName))
        {
            throw new ArgumentException("The report file name cannot be null or blank.");
        }

        if (!fileName.EndsWith(".txt", StringComparison.OrdinalIgnoreCase))
        {
            throw new ArgumentException($"The report file name must end with .txt: {fileName}");
        }

        string stem = fileName[..^4];
        string[] tokens = stem.Split('_');

        if (tokens.Length < 3)
        {
            throw new ArgumentException(
                $"The report file name must follow CAT_TIPO_nombre.txt: {fileName}"
            );
        }

        return new ReportMetadata(
            ReportLabels.CategoryFromCode(tokens[0]),
            ReportLabels.TypeFromCode(tokens[1]),
            fileName
        );
    }
}

