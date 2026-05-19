namespace ReportProcessing;

/// <summary>
/// Common product contract.
/// </summary>
public interface IReportProcessor
{
    string Process();
}

/// <summary>
/// Shared message formatting for concrete processors.
/// </summary>
public abstract class AbstractReportProcessor(
    string fileName,
    ReportCategory category,
    ReportType reportType) : IReportProcessor
{
    public string Process() =>
        $"Procesando informe '{fileName}' de {ReportLabels.CategoryDisplayName(category)} " +
        $"y tipo {ReportLabels.TypeDisplayName(reportType)}. Acción simulada: {ActionDescription()}.";

    protected abstract string ActionDescription();
}

public sealed class EntryInvoiceReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Entry, ReportType.Invoice)
{
    protected override string ActionDescription() =>
        "registro de factura recibida en el módulo de compras";
}

public sealed class EntryPurchaseReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Entry, ReportType.Purchase)
{
    protected override string ActionDescription() =>
        "validación de compra entrante para conciliación interna";
}

public sealed class EntryOrderReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Entry, ReportType.Order)
{
    protected override string ActionDescription() =>
        "alta de pedido de entrada para planificación logística";
}

public sealed class OutputInvoiceReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Output, ReportType.Invoice)
{
    protected override string ActionDescription() =>
        "emisión de factura de salida hacia el sistema contable";
}

public sealed class OutputPurchaseReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Output, ReportType.Purchase)
{
    protected override string ActionDescription() =>
        "cierre de compra de salida para auditoría de movimientos";
}

public sealed class MixedInvoiceReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Mixed, ReportType.Invoice)
{
    protected override string ActionDescription() =>
        "orquestación híbrida de una factura compartida entre entrada y salida";
}

public sealed class MixedPurchaseReportProcessor(string fileName)
    : AbstractReportProcessor(fileName, ReportCategory.Mixed, ReportType.Purchase)
{
    protected override string ActionDescription() =>
        "sincronización de compra mixta entre inventario y expedición";
}

/// <summary>
/// Abstract factory contract.
/// </summary>
public interface ICategoryReportFactory
{
    ReportCategory SupportedCategory { get; }

    IReportProcessor CreateProcessor(ReportType reportType, string fileName);
}

/// <summary>
/// Registry-based base factory that keeps the public interface stable.
/// </summary>
public abstract class MapBackedReportFactory : ICategoryReportFactory
{
    private readonly Dictionary<ReportType, Func<string, IReportProcessor>> _builders = new();

    public abstract ReportCategory SupportedCategory { get; }

    protected void Register(ReportType reportType, Func<string, IReportProcessor> builder)
    {
        _builders[reportType] = builder;
    }

    public IReportProcessor CreateProcessor(ReportType reportType, string fileName)
    {
        if (!_builders.TryGetValue(reportType, out Func<string, IReportProcessor>? builder))
        {
            throw new ArgumentException(
                $"The category {ReportLabels.CategoryDisplayName(SupportedCategory)} " +
                $"does not support reports of type {ReportLabels.TypeDisplayName(reportType)}."
            );
        }

        return builder(fileName);
    }
}

public sealed class EntryReportFactory : MapBackedReportFactory
{
    public EntryReportFactory()
    {
        Register(ReportType.Invoice, fileName => new EntryInvoiceReportProcessor(fileName));
        Register(ReportType.Purchase, fileName => new EntryPurchaseReportProcessor(fileName));
        Register(ReportType.Order, fileName => new EntryOrderReportProcessor(fileName));
    }

    public override ReportCategory SupportedCategory => ReportCategory.Entry;
}

public sealed class OutputReportFactory : MapBackedReportFactory
{
    public OutputReportFactory()
    {
        Register(ReportType.Invoice, fileName => new OutputInvoiceReportProcessor(fileName));
        Register(ReportType.Purchase, fileName => new OutputPurchaseReportProcessor(fileName));
    }

    public override ReportCategory SupportedCategory => ReportCategory.Output;
}

public sealed class MixedReportFactory : MapBackedReportFactory
{
    public MixedReportFactory()
    {
        Register(ReportType.Invoice, fileName => new MixedInvoiceReportProcessor(fileName));
        Register(ReportType.Purchase, fileName => new MixedPurchaseReportProcessor(fileName));
    }

    public override ReportCategory SupportedCategory => ReportCategory.Mixed;
}

/// <summary>
/// Decouples the application service from concrete factory classes.
/// </summary>
public sealed class ReportFactoryProvider
{
    private readonly Dictionary<ReportCategory, ICategoryReportFactory> _factories = new();

    public ReportFactoryProvider()
    {
        Register(new EntryReportFactory());
        Register(new OutputReportFactory());
        Register(new MixedReportFactory());
    }

    private void Register(ICategoryReportFactory factory)
    {
        _factories[factory.SupportedCategory] = factory;
    }

    public ICategoryReportFactory GetFactory(ReportCategory category)
    {
        if (!_factories.TryGetValue(category, out ICategoryReportFactory? factory))
        {
            throw new ArgumentException($"No factory registered for category {category}");
        }

        return factory;
    }
}

