package com.unir.reuse.reports;

/**
 * Factory for OUTPUT processors.
 */
public final class OutputReportFactory extends AbstractMapBackedReportFactory {

    public OutputReportFactory() {
        register(ReportType.INVOICE, OutputInvoiceReportProcessor::new);
        register(ReportType.PURCHASE, OutputPurchaseReportProcessor::new);
    }

    @Override
    public ReportCategory supportedCategory() {
        return ReportCategory.OUTPUT;
    }
}

