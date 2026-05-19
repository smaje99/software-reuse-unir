package com.unir.reuse.reports;

/**
 * Handles outgoing invoice reports.
 */
public final class OutputInvoiceReportProcessor extends AbstractReportProcessor {

    public OutputInvoiceReportProcessor(final String fileName) {
        super(fileName, ReportCategory.OUTPUT, ReportType.INVOICE);
    }

    @Override
    protected String actionDescription() {
        return "emisión de factura de salida hacia el sistema contable";
    }
}

