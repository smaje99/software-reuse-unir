namespace ReportProcessing;

/// <summary>
/// High-level coordinator for parsing, factory resolution and processing.
/// </summary>
public sealed class ReportProcessingService
{
    private readonly ReportParser _parser = new();
    private readonly ReportFactoryProvider _factoryProvider = new();

    public IReadOnlyList<string> ProcessReports(IEnumerable<string> reportFileNames)
    {
        List<string> results = [];

        foreach (string reportFileName in reportFileNames)
        {
            try
            {
                ReportMetadata metadata = _parser.Parse(reportFileName);
                ICategoryReportFactory factory = _factoryProvider.GetFactory(metadata.Category);
                IReportProcessor processor = factory.CreateProcessor(metadata.Type, metadata.FileName);
                results.Add(processor.Process());
            }
            catch (ArgumentException exception)
            {
                results.Add($"Error procesando '{reportFileName}': {exception.Message}");
            }
        }

        return results;
    }
}

