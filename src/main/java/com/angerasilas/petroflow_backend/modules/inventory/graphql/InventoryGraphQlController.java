package com.angerasilas.petroflow_backend.modules.inventory.graphql;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;
import com.angerasilas.petroflow_backend.modules.inventory.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class InventoryGraphQlController {

    private final StockService stockService;

    @QueryMapping
    public StockSummaryResponse inventoryOverview() {
        return stockService.getStockOverview();
    }
}
