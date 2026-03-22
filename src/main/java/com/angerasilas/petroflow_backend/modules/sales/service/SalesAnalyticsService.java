package com.angerasilas.petroflow_backend.modules.sales.service;

import com.angerasilas.petroflow_backend.modules.sales.dto.summary.SalesSummaryResponse;

public interface SalesAnalyticsService {

    SalesSummaryResponse getSalesOverview();
}
