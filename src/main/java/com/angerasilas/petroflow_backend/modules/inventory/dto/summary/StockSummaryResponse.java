package com.angerasilas.petroflow_backend.modules.inventory.dto.summary;

public record StockSummaryResponse(
        String tenantId,
        long trackedSkuCount,
        long lowStockItemCount,
        long outOfStockItemCount,
        long pendingAdjustmentCount,
        long stockMovementCount,
        String generatedAt,
        boolean dataReady) {
}
