package com.angerasilas.petroflow_backend.modules.sales.service.impl;

import com.angerasilas.petroflow_backend.modules.sales.dto.summary.DailySalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.service.SalesAnalyticsService;
import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import java.time.Instant;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class SalesAnalyticsServiceImpl implements SalesAnalyticsService {

    @Override
    public SalesSummaryResponse getSalesOverview() {
        Instant now = Instant.now();
        LocalDate businessDate = LocalDate.now();
        DailySalesSummaryResponse latestDailySummary = new DailySalesSummaryResponse(
                businessDate.toString(),
                0L,
                0.0d,
                0.0d,
                now.toString(),
                false);

        return new SalesSummaryResponse(
                currentTenant(),
                "KES",
                0L,
                0.0d,
                0.0d,
                0L,
                businessDate.toString(),
                latestDailySummary,
                now.toString(),
                false);
    }

    private String currentTenant() {
        return TenantContextHolder.hasContext() ? TenantContextHolder.getCurrentTenant() : null;
    }
}
