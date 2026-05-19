package com.unir.reuse.reports;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Base implementation for concrete factories. A type-to-constructor registry
 * keeps the public factory interface stable while still allowing each category
 * to support a different subset of report types.
 */
public abstract class AbstractMapBackedReportFactory implements CategoryReportFactory {

    private final Map<ReportType, Function<String, ReportProcessor>> builders =
        new EnumMap<>(ReportType.class);

    protected final void register(
        final ReportType type,
        final Function<String, ReportProcessor> builder
    ) {
        builders.put(type, builder);
    }

    @Override
    public final ReportProcessor createProcessor(final ReportType type, final String fileName) {
        Function<String, ReportProcessor> builder = builders.get(type);
        if (builder == null) {
            throw new IllegalArgumentException(
                "The category %s does not support reports of type %s."
                    .formatted(supportedCategory().displayName(), type.displayName())
            );
        }
        return builder.apply(fileName);
    }
}

