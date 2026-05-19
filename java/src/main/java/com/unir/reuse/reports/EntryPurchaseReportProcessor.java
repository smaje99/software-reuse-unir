package com.unir.reuse.reports;

/**
 * Handles incoming purchase reports.
 */
public final class EntryPurchaseReportProcessor extends AbstractReportProcessor {

    public EntryPurchaseReportProcessor(final String fileName) {
        super(fileName, ReportCategory.ENTRY, ReportType.PURCHASE);
    }

    @Override
    protected String actionDescription() {
        return "validación de compra entrante para conciliación interna";
    }
}

