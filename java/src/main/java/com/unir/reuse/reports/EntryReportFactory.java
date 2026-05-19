package com.unir.reuse.reports;

/**
 * Factory for ENTRY processors. It contains the only registration for ORDER,
 * which mirrors the asymmetrical evolution described in the activity.
 */
public final class EntryReportFactory extends AbstractMapBackedReportFactory {

    public EntryReportFactory() {
        register(ReportType.INVOICE, EntryInvoiceReportProcessor::new);
        register(ReportType.PURCHASE, EntryPurchaseReportProcessor::new);
        register(ReportType.ORDER, EntryOrderReportProcessor::new);
    }

    @Override
    public ReportCategory supportedCategory() {
        return ReportCategory.ENTRY;
    }
}

