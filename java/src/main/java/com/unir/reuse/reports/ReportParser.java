package com.unir.reuse.reports;

/**
 * Parses filenames that follow the contract described in the activity:
 * {@code CAT_TIPO_nombre.txt}. The parser is intentionally strict because a
 * malformed filename prevents the factory selection from being deterministic.
 */
public final class ReportParser {

    public ReportMetadata parse(final String fileName) {
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("The report file name cannot be null or blank.");
        }
        if (!fileName.endsWith(".txt")) {
            throw new IllegalArgumentException("The report file name must end with .txt: " + fileName);
        }

        String fileStem = fileName.substring(0, fileName.length() - 4);
        String[] tokens = fileStem.split("_");

        if (tokens.length < 3) {
            throw new IllegalArgumentException(
                "The report file name must follow CAT_TIPO_nombre.txt: " + fileName
            );
        }

        ReportCategory category = ReportCategory.fromCode(tokens[0]);
        ReportType type = ReportType.fromCode(tokens[1]);

        return new ReportMetadata(category, type, fileName);
    }
}

