package com.angerasilas.petroflow_backend.modules.inventory.service;

import com.angerasilas.petroflow_backend.modules.inventory.dto.summary.StockSummaryResponse;

public interface StockService {

    StockSummaryResponse getStockOverview();
}
