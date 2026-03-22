package com.angerasilas.petroflow_backend.modules.reporting.service.impl;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;
import com.angerasilas.petroflow_backend.modules.inventory.service.StockService;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.BranchPerformanceResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.InventoryReportResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.ReportingOverviewResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.SalesReportResponse;
import com.angerasilas.petroflow_backend.modules.reporting.service.ReportingService;
import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.service.SalesAnalyticsService;
import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import java.time.Instant;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportingServiceImpl implements ReportingService {

    private final SalesAnalyticsService salesAnalyticsService;
    private final StockService stockService;

    @Override
    public ReportingOverviewResponse getReportingOverview() {
        Instant now = Instant.now();
        String periodLabel = LocalDate.now().toString();
        SalesSummaryResponse salesSummary = salesAnalyticsService.getSalesOverview();
        StockSummaryResponse stockSummary = stockService.getStockOverview();

        SalesReportResponse sales = new SalesReportResponse(
                periodLabel,
                salesSummary.totalSalesCount(),
                salesSummary.grossSalesAmount(),
                0.0d,
                now.toString(),
                salesSummary.dataReady());

        InventoryReportResponse inventory = new InventoryReportResponse(
                periodLabel,
                stockSummary.trackedSkuCount(),
                stockSummary.lowStockItemCount(),
                stockSummary.stockMovementCount(),
                now.toString(),
                stockSummary.dataReady());

        BranchPerformanceResponse branchPerformance = new BranchPerformanceResponse(
                periodLabel,
                0L,
                "No branch data available yet",
                salesSummary.netSalesAmount(),
                now.toString(),
                false);

        return new ReportingOverviewResponse(
                resolveTenantId(salesSummary, stockSummary),
                sales,
                inventory,
                branchPerformance,
                now.toString(),
                salesSummary.dataReady() && stockSummary.dataReady());
    }

    private String resolveTenantId(SalesSummaryResponse salesSummary, StockSummaryResponse stockSummary) {
        if (TenantContextHolder.hasContext()) {
            return TenantContextHolder.getCurrentTenant();
        }
        if (salesSummary.tenantId() != null) {
            return salesSummary.tenantId();
        }
        return stockSummary.tenantId();
    }
}
