package com.unir.reuse.reports;

/**
 * Factory introduced by the first system evolution.
 */
public final class MixedReportFactory extends AbstractMapBackedReportFactory {

    public MixedReportFactory() {
        register(ReportType.INVOICE, MixedInvoiceReportProcessor::new);
        register(ReportType.PURCHASE, MixedPurchaseReportProcessor::new);
    }

    @Override
    public ReportCategory supportedCategory() {
        return ReportCategory.MIXED;
    }
}

