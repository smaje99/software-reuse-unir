package com.unir.reuse.reports;

/**
 * Demonstrates the base system and the two requested evolutions with a single
 * executable entry point. The last sample intentionally shows a validation
 * error to prove that unsupported combinations are rejected explicitly.
 */
public final class Main {

    private Main() {
    }

    public static void main(final String[] args) {
        String[] reports = {
            "ENT_FAC_001.txt",
            "ENT_COM_002.txt",
            "SAL_FAC_003.txt",
            "SAL_COM_004.txt",
            "MIX_FAC_005.txt",
            "MIX_COM_006.txt",
            "ENT_PED_007.txt",
            "SAL_PED_008.txt"
        };

        ReportProcessingService service = new ReportProcessingService();
        service.processReports(reports).forEach(System.out::println);
    }
}

