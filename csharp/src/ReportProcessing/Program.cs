namespace ReportProcessing;

/// <summary>
/// Executable demonstration entry point.
/// </summary>
internal static class Program
{
    private static void Main()
    {
        string[] reports =
        [
            "ENT_FAC_001.txt",
            "ENT_COM_002.txt",
            "SAL_FAC_003.txt",
            "SAL_COM_004.txt",
            "MIX_FAC_005.txt",
            "MIX_COM_006.txt",
            "ENT_PED_007.txt",
            "SAL_PED_008.txt"
        ];

        ReportProcessingService service = new();
        foreach (string message in service.ProcessReports(reports))
        {
            Console.WriteLine(message);
        }
    }
}

