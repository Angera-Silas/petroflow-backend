package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.dto.StockInfoDto;

public interface StockService {
    StockDto createStock(StockDto stockDto);
    StockDto getStockById(Long id);
    List<StockDto> getAllStocks();
    StockDto updateStock(Long id, StockDto stockDto);
    void deleteStock(Long id);

    List<StockInfoDto> getStockInfo();
    
    List<StockInfoDto> getStockInfoByOrganizationAndFacility(Long orgId, Long facilityId);

    List<StockInfoDto> getStockInfoByOrganization(Long orgId);
}