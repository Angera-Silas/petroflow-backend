package com.angerasilas.petroflow_backend.modules.reporting.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;
import com.angerasilas.petroflow_backend.modules.inventory.service.StockService;
import com.angerasilas.petroflow_backend.modules.sales.dto.summary.DailySalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.service.SalesAnalyticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportingServiceImplTest {

    @Mock
    private SalesAnalyticsService salesAnalyticsService;

    @Mock
    private StockService stockService;

    @InjectMocks
    private ReportingServiceImpl service;

    @Test
    void shouldAssembleReportingOverviewFromSalesAndInventoryReadModels() {
        when(salesAnalyticsService.getSalesOverview()).thenReturn(new SalesSummaryResponse(
                "tenant_report",
                "KES",
                12L,
                3400.0d,
                3300.0d,
                1L,
                "2026-03-22",
                new DailySalesSummaryResponse("2026-03-22", 12L, 3400.0d, 3300.0d, "2026-03-22T00:00:00Z", true),
                "2026-03-22T00:00:00Z",
                true));
        when(stockService.getStockOverview()).thenReturn(new StockSummaryResponse(
                "tenant_report",
                20L,
                2L,
                1L,
                1L,
                10L,
                "2026-03-22T00:00:00Z",
                true));

        var response = service.getReportingOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_report");
        assertThat(response.sales().transactionCount()).isEqualTo(12L);
        assertThat(response.inventory().trackedSkuCount()).isEqualTo(20L);
        assertThat(response.dataReady()).isTrue();
    }
}
