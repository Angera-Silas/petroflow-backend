package com.angerasilas.petroflow_backend.modules.sales.dto.summary;

public record DailySalesSummaryResponse(
        String businessDate,
        long transactionCount,
        double grossSalesAmount,
        double netSalesAmount,
        String lastUpdatedAt,
        boolean dataReady) {
}
