package com.angerasilas.petroflow_backend.modules.reporting.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.angerasilas.petroflow_backend.modules.reporting.dto.response.BranchPerformanceResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.InventoryReportResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.ReportingOverviewResponse;
import com.angerasilas.petroflow_backend.modules.reporting.dto.response.SalesReportResponse;
import com.angerasilas.petroflow_backend.modules.reporting.service.ReportingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReportingGraphQlControllerTest {

    @Mock
    private ReportingService reportingService;

    @InjectMocks
    private ReportingGraphQlController controller;

    @Test
    void shouldExposeReportingOverviewQuery() {
        when(reportingService.getReportingOverview()).thenReturn(new ReportingOverviewResponse(
                "tenant_reporting",
                new SalesReportResponse("2026-03-22", 6L, 2400.0d, 0.0d, "2026-03-22T00:00:00Z", true),
                new InventoryReportResponse("2026-03-22", 12L, 1L, 5L, "2026-03-22T00:00:00Z", true),
                new BranchPerformanceResponse("2026-03-22", 2L, "Mlolongo", 1200.0d, "2026-03-22T00:00:00Z", true),
                "2026-03-22T00:00:00Z",
                true));

        var response = controller.reportingOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_reporting");
        assertThat(response.branchPerformance().topPerformerLabel()).isEqualTo("Mlolongo");
    }
}
