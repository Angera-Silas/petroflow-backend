package com.angerasilas.petroflow_backend.modules.inventory.service.impl;

import static org.assertj.core.api.Assertions.assertThat;

import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class StockServiceImplTest {

    private final StockServiceImpl service = new StockServiceImpl();

    @AfterEach
    void tearDown() {
        TenantContextHolder.clear();
    }

    @Test
    void shouldReturnDefaultInventoryOverviewForCurrentTenant() {
        TenantContextHolder.setCurrentTenant("tenant_inventory");

        var response = service.getStockOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_inventory");
        assertThat(response.trackedSkuCount()).isZero();
        assertThat(response.dataReady()).isFalse();
    }
}
