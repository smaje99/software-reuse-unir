package com.unir.reuse.reports;

/**
 * Handles incoming invoice reports.
 */
public final class EntryInvoiceReportProcessor extends AbstractReportProcessor {

    public EntryInvoiceReportProcessor(final String fileName) {
        super(fileName, ReportCategory.ENTRY, ReportType.INVOICE);
    }

    @Override
    protected String actionDescription() {
        return "registro de factura recibida en el módulo de compras";
    }
}

