package com.unir.reuse.reports;

/**
 * Supported report types. The additional ORDER type models the evolution
 * requested by the activity while preserving the same parsing contract.
 */
public enum ReportType {
    INVOICE("FAC", "FACTURA"),
    PURCHASE("COM", "COMPRA"),
    ORDER("PED", "PEDIDO");

    private final String code;
    private final String displayName;

    ReportType(final String code, final String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String code() {
        return code;
    }

    public String displayName() {
        return displayName;
    }

    public static ReportType fromCode(final String code) {
        for (ReportType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unsupported report type: " + code);
    }
}

