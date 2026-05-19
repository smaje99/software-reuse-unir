package com.unir.reuse.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Application service that coordinates parsing, factory selection and processor
 * execution. The goal is to keep the high-level workflow independent from the
 * concrete families and products.
 */
public final class ReportProcessingService {

    private final ReportParser parser;
    private final ReportFactoryProvider factoryProvider;

    public ReportProcessingService() {
        this(new ReportParser(), new ReportFactoryProvider());
    }

    public ReportProcessingService(
        final ReportParser parser,
        final ReportFactoryProvider factoryProvider
    ) {
        this.parser = parser;
        this.factoryProvider = factoryProvider;
    }

    public List<String> processReports(final String[] reportFileNames) {
        return processReports(Arrays.asList(reportFileNames));
    }

    public List<String> processReports(final List<String> reportFileNames) {
        List<String> results = new ArrayList<>();
        for (String reportFileName : reportFileNames) {
            try {
                ReportMetadata metadata = parser.parse(reportFileName);
                CategoryReportFactory factory = factoryProvider.getFactory(metadata.category());
                ReportProcessor processor =
                    factory.createProcessor(metadata.type(), metadata.fileName());
                results.add(processor.process());
            } catch (IllegalArgumentException exception) {
                results.add("Error procesando '%s': %s".formatted(reportFileName, exception.getMessage()));
            }
        }
        return results;
    }
}

