package com.unir.reuse.reports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ReportProcessingServiceTest {

    private final ReportProcessingService service = new ReportProcessingService();

    @Test
    void processesReportsFromAllSupportedFamilies() {
        List<String> results = service.processReports(
            new String[] {"ENT_FAC_001.txt", "SAL_COM_002.txt", "MIX_COM_003.txt", "ENT_PED_004.txt"}
        );

        assertEquals(4, results.size());
        assertEquals(
            "Procesando informe 'ENT_FAC_001.txt' de ENTRADA y tipo FACTURA. Acción simulada: "
                + "registro de factura recibida en el módulo de compras.",
            results.get(0)
        );
        assertEquals(
            "Procesando informe 'SAL_COM_002.txt' de SALIDA y tipo COMPRA. Acción simulada: "
                + "cierre de compra de salida para auditoría de movimientos.",
            results.get(1)
        );
        assertEquals(
            "Procesando informe 'MIX_COM_003.txt' de MIXTA y tipo COMPRA. Acción simulada: "
                + "sincronización de compra mixta entre inventario y expedición.",
            results.get(2)
        );
        assertEquals(
            "Procesando informe 'ENT_PED_004.txt' de ENTRADA y tipo PEDIDO. Acción simulada: "
                + "alta de pedido de entrada para planificación logística.",
            results.get(3)
        );
    }

    @Test
    void reportsUnsupportedCategoryTypeCombinations() {
        List<String> results = service.processReports(new String[] {"SAL_PED_999.txt"});

        assertEquals(
            "Error procesando 'SAL_PED_999.txt': The category SALIDA does not support reports of type PEDIDO.",
            results.getFirst()
        );
    }
}

