package com.angerasilas.petroflow_backend.modules.inventory.service.impl;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;
import com.angerasilas.petroflow_backend.modules.inventory.service.StockService;
import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl implements StockService {

    @Override
    public StockSummaryResponse getStockOverview() {
        return new StockSummaryResponse(
                currentTenant(),
                0L,
                0L,
                0L,
                0L,
                0L,
                Instant.now().toString(),
                false);
    }

    private String currentTenant() {
        return TenantContextHolder.hasContext() ? TenantContextHolder.getCurrentTenant() : null;
    }
}
