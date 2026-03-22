package com.angerasilas.petroflow_backend.modules.reporting.dto.response;

public record InventoryReportResponse(
        String periodLabel,
        long trackedSkuCount,
        long lowStockItemCount,
        long stockMovementCount,
        String generatedAt,
        boolean dataReady) {
}
