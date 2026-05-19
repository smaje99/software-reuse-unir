package com.unir.reuse.reports;

/**
 * Handles outgoing purchase reports.
 */
public final class OutputPurchaseReportProcessor extends AbstractReportProcessor {

    public OutputPurchaseReportProcessor(final String fileName) {
        super(fileName, ReportCategory.OUTPUT, ReportType.PURCHASE);
    }

    @Override
    protected String actionDescription() {
        return "cierre de compra de salida para auditoría de movimientos";
    }
}

