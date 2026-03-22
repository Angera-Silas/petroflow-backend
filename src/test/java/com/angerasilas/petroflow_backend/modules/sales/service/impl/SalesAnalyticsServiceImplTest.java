package com.angerasilas.petroflow_backend.modules.sales.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class SalesAnalyticsServiceImplTest {

    private final SalesAnalyticsServiceImpl service = new SalesAnalyticsServiceImpl();

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void shouldReturnDefaultSalesOverviewForCurrentTenant() {
        TenantContextHolder.setCurrentTenant("tenant_demo");

        var response = service.getSalesOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_demo");
        assertThat(response.currencyCode()).isEqualTo("KES");
        assertThat(response.latestDailySummary()).isNotNull();
        assertThat(response.dataReady()).isFalse();
    }
}
