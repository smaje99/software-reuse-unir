package com.unir.reuse.reports;

/**
 * Abstract factory contract. Each concrete implementation represents a
 * category-specific family of processors and decides which report types are
 * valid for that family.
 */
public interface CategoryReportFactory {

    ReportCategory supportedCategory();

    ReportProcessor createProcessor(ReportType type, String fileName);
}

