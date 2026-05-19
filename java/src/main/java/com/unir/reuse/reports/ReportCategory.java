package com.unir.reuse.reports;

/**
 * Defines the supported report categories and the filename prefix used by each
 * one. The Spanish label is kept close to the enum so that presentation logic
 * remains consistent across the three language implementations.
 */
public enum ReportCategory {
    ENTRY("ENT", "ENTRADA"),
    OUTPUT("SAL", "SALIDA"),
    MIXED("MIX", "MIXTA");

    private final String code;
    private final String displayName;

    ReportCategory(final String code, final String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String code() {
        return code;
    }

    public String displayName() {
        return displayName;
    }

    public static ReportCategory fromCode(final String code) {
        for (ReportCategory category : values()) {
            if (category.code.equalsIgnoreCase(code)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unsupported report category: " + code);
    }
}

