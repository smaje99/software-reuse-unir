package com.unir.reuse.reports;

import java.util.EnumMap;
import java.util.Map;

/**
 * Centralizes the mapping between report categories and their factories. The
 * processing service depends on this provider instead of knowing concrete
 * factory classes directly.
 */
public final class ReportFactoryProvider {

    private final Map<ReportCategory, CategoryReportFactory> factories =
        new EnumMap<>(ReportCategory.class);

    public ReportFactoryProvider() {
        register(new EntryReportFactory());
        register(new OutputReportFactory());
        register(new MixedReportFactory());
    }

    private void register(final CategoryReportFactory factory) {
        factories.put(factory.supportedCategory(), factory);
    }

    public CategoryReportFactory getFactory(final ReportCategory category) {
        CategoryReportFactory factory = factories.get(category);
        if (factory == null) {
            throw new IllegalArgumentException("No factory registered for category " + category);
        }
        return factory;
    }
}

