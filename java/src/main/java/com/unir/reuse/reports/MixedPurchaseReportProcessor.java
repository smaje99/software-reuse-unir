package com.unir.reuse.reports;

/**
 * Handles purchase reports in the evolved MIXED category.
 */
public final class MixedPurchaseReportProcessor extends AbstractReportProcessor {

    public MixedPurchaseReportProcessor(final String fileName) {
        super(fileName, ReportCategory.MIXED, ReportType.PURCHASE);
    }

    @Override
    protected String actionDescription() {
        return "sincronización de compra mixta entre inventario y expedición";
    }
}

