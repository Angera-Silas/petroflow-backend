package com.angerasilas.petroflow_backend.modules.reporting.dto.response;

public record ReportingOverviewResponse(
        String tenantId,
        SalesReportResponse sales,
        InventoryReportResponse inventory,
        BranchPerformanceResponse branchPerformance,
        String generatedAt,
        boolean dataReady) {
}
