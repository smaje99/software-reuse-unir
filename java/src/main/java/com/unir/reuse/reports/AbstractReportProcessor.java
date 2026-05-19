package com.unir.reuse.reports;

/**
 * Shared implementation for the concrete processors. Each subtype only needs
 * to provide the domain-specific simulated action that differentiates its
 * processing behavior.
 */
public abstract class AbstractReportProcessor implements ReportProcessor {

    private final String fileName;
    private final ReportCategory category;
    private final ReportType type;

    protected AbstractReportProcessor(
        final String fileName,
        final ReportCategory category,
        final ReportType type
    ) {
        this.fileName = fileName;
        this.category = category;
        this.type = type;
    }

    @Override
    public final String process() {
        return "Procesando informe '%s' de %s y tipo %s. Acción simulada: %s."
            .formatted(fileName, category.displayName(), type.displayName(), actionDescription());
    }

    protected abstract String actionDescription();
}

