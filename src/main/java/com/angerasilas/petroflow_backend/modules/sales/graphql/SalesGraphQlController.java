package com.angerasilas.petroflow_backend.modules.sales.graphql;

import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;
import com.angerasilas.petroflow_backend.modules.sales.service.SalesAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SalesGraphQlController {

    private final SalesAnalyticsService salesAnalyticsService;

    @QueryMapping
    public SalesSummaryResponse salesOverview() {
        return salesAnalyticsService.getSalesOverview();
    }
}
