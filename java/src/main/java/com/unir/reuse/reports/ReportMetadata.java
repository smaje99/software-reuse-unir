package com.unir.reuse.reports;

/**
 * Immutable value object produced by the parser. It concentrates filename
 * interpretation in one place so that factories never need to understand the
 * raw text format used by incoming files.
 */
public record ReportMetadata(ReportCategory category, ReportType type, String fileName) {
}

