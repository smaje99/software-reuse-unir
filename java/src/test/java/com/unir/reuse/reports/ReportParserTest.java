package com.unir.reuse.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ReportParserTest {

    private final ReportParser parser = new ReportParser();

    @Test
    void parsesEntryInvoiceReports() {
        ReportMetadata metadata = parser.parse("ENT_FAC_001.txt");

        assertEquals(ReportCategory.ENTRY, metadata.category());
        assertEquals(ReportType.INVOICE, metadata.type());
        assertEquals("ENT_FAC_001.txt", metadata.fileName());
    }

    @Test
    void rejectsUnknownCategories() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> parser.parse("XXX_FAC_001.txt"));

        assertEquals("Unsupported report category: XXX", exception.getMessage());
    }

    @Test
    void rejectsInvalidFormat() {
        IllegalArgumentException exception =
            assertThrows(IllegalArgumentException.class, () -> parser.parse("ENTFAC001.txt"));

        assertEquals(
            "The report file name must follow CAT_TIPO_nombre.txt: ENTFAC001.txt",
            exception.getMessage()
        );
    }
}

