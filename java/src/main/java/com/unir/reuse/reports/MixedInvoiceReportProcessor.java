package com.unir.reuse.reports;

/**
 * Handles invoice reports in the evolved MIXED category.
 */
public final class MixedInvoiceReportProcessor extends AbstractReportProcessor {

    public MixedInvoiceReportProcessor(final String fileName) {
        super(fileName, ReportCategory.MIXED, ReportType.INVOICE);
    }

    @Override
    protected String actionDescription() {
        return "orquestación híbrida de una factura compartida entre entrada y salida";
    }
}

