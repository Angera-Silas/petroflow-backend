package com.angerasilas.petroflow_backend.modules.reporting.dto.response;

public record BranchPerformanceResponse(
        String periodLabel,
        long activeBranchCount,
        String topPerformerLabel,
        double averageDailySalesAmount,
        String generatedAt,
        boolean dataReady) {
}
