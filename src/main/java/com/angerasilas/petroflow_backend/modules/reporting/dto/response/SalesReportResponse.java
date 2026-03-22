package com.angerasilas.petroflow_backend.modules.reporting.dto.response;

public record SalesReportResponse(
        String periodLabel,
        long transactionCount,
        double grossSalesAmount,
        double refundAmount,
        String generatedAt,
        boolean dataReady) {
}
