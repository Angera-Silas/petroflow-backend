package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import java.util.List;

public interface SellPointService {
    SellPointDto createSellPoint(SellPointDto sellPointDto);
    SellPointDto updateSellPoint(Long id, SellPointDto sellPointDto);
    void deleteSellPoint(Long id);
    SellPointDto getSellPointById(Long id);
    List<SellPointDto> getAllSellPoints();
}