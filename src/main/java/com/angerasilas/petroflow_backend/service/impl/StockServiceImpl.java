package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.entity.Stock;
import com.angerasilas.petroflow_backend.mapper.StockMapper;
import com.angerasilas.petroflow_backend.repository.StockRepository;
import com.angerasilas.petroflow_backend.service.StockService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final StockMapper stockMapper;

    @Override
    public StockDto createStock(StockDto stockDto) {
        Stock stock = stockMapper.toEntity(stockDto);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public StockDto getStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        return stockMapper.toDto(stock);
    }

    @Override
    public List<StockDto> getAllStocks() {
        return stockRepository.findAll().stream().map(stockMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public StockDto updateStock(Long id, StockDto stockDto) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setDateStocked(stockDto.getDateStocked());
        stock.setUnitsAvailable(stockDto.getUnitsAvailable());
        stock.setUnitsSold(stockDto.getUnitsSold());
        stock.setUnitsBought(stockDto.getUnitsBought());
        stock.setUnitsReturned(stockDto.getUnitsReturned());
        stock.setUnitsDamaged(stockDto.getUnitsDamaged());
        stock.setUnitsLost(stockDto.getUnitsLost());
        stock.setBuyingPricePerUnit(stockDto.getBuyingPricePerUnit());
        stock.setSellingPricePerUnit(stockDto.getSellingPricePerUnit());
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}