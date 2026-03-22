package com.angerasilas.petroflow_backend.modules.sales.dto.summary;

public record SalesSummaryResponse(
        String tenantId,
        String currencyCode,
        long totalSalesCount,
        double grossSalesAmount,
        double netSalesAmount,
        long pendingRefundCount,
        String latestBusinessDate,
        DailySalesSummaryResponse latestDailySummary,
        String generatedAt,
        boolean dataReady) {
}
