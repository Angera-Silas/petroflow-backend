package com.angerasilas.petroflow_backend.modules.sales.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.angerasilas.petroflow_backend.modules.sales.dto.summary.DailySalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.service.SalesAnalyticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SalesGraphQlControllerTest {

    @Mock
    private SalesAnalyticsService salesAnalyticsService;

    @InjectMocks
    private SalesGraphQlController controller;

    @Test
    void shouldExposeSalesOverviewQuery() {
        when(salesAnalyticsService.getSalesOverview()).thenReturn(new SalesSummaryResponse(
                "tenant_sales",
                "KES",
                3L,
                1500.0d,
                1450.0d,
                0L,
                "2026-03-22",
                new DailySalesSummaryResponse("2026-03-22", 3L, 1500.0d, 1450.0d, "2026-03-22T00:00:00Z", true),
                "2026-03-22T00:00:00Z",
                true));

        var response = controller.salesOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_sales");
        assertThat(response.totalSalesCount()).isEqualTo(3L);
    }
}
