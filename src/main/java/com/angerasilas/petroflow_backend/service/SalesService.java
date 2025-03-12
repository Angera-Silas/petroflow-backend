package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.SalesDto;

public interface SalesService {
    SalesDto createSales(SalesDto salesDto);
    SalesDto getSalesById(Long id);
    List<SalesDto> getAllSales();
    SalesDto updateSales(Long id, SalesDto salesDto);
    void deleteSales(Long id);
}