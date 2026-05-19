package com.unir.reuse.reports;

/**
 * Handles the evolved ORDER report supported only by the ENTRY category.
 */
public final class EntryOrderReportProcessor extends AbstractReportProcessor {

    public EntryOrderReportProcessor(final String fileName) {
        super(fileName, ReportCategory.ENTRY, ReportType.ORDER);
    }

    @Override
    protected String actionDescription() {
        return "alta de pedido de entrada para planificación logística";
    }
}

