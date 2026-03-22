package com.angerasilas.petroflow_backend.modules.inventory.graphql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;
import com.angerasilas.petroflow_backend.modules.inventory.service.StockService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class InventoryGraphQlControllerTest {

    @Mock
    private StockService stockService;

    @InjectMocks
    private InventoryGraphQlController controller;

    @Test
    void shouldExposeInventoryOverviewQuery() {
        when(stockService.getStockOverview()).thenReturn(new StockSummaryResponse(
                "tenant_inventory",
                15L,
                2L,
                1L,
                0L,
                7L,
                "2026-03-22T00:00:00Z",
                true));

        var response = controller.inventoryOverview();

        assertThat(response.tenantId()).isEqualTo("tenant_inventory");
        assertThat(response.trackedSkuCount()).isEqualTo(15L);
    }
}
