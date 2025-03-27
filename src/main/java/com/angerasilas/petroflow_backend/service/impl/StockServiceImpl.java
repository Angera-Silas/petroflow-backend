package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.dto.StockInfoDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Stock;
import com.angerasilas.petroflow_backend.mapper.StockMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.repository.StockRepository;
import com.angerasilas.petroflow_backend.service.StockService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private final StockRepository stockRepository;

    @Autowired
    private final StockMapper stockMapper;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final OrganizationRepository organizationRepository;

    @Autowired
    private final FacilityRepository facilityRepository;

    @Override
    public StockDto createStock(StockDto stockDto) {
        Product product = productRepository.findById(stockDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Organization organization = organizationRepository.findById(stockDto.getOrgId()).orElseThrow(() -> new RuntimeException("Organization not found"));
        Facility facility = facilityRepository.findById(stockDto.getFacilityId()).orElseThrow(() -> new RuntimeException("Facility not found"));
        Stock stock = stockMapper.toEntity(stockDto, product, organization, facility);
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

    @Override
    public List<StockInfoDto> getStockInfo(){
        return stockRepository.findStockInfo();
    }

    @Override
    public List<StockInfoDto> getStockInfoByOrganization(Long orgId){
        return stockRepository.findStockInfoByOrgId(orgId);
    }

    @Override
    public List<StockInfoDto> getStockInfoByOrganizationAndFacility(Long orgId, Long facilityId){
        return stockRepository.findStockInfoByOrgIdAndFacilityId(orgId, facilityId);
    }

    @Override
    public void updateStockLevels(Long productId, Long unitsSold) {
        stockRepository.updateStockLevels(productId, unitsSold);
    }

    @Override
    public void addIncomingStock(Long productId, Long unitsBought) {
        stockRepository.addIncomingStock(productId, unitsBought);
    }

    @Override
    public void addReturnedStock(Long productId, Long unitsReturned) {
        stockRepository.addReturnedStock(productId, unitsReturned);
    }

    @Override
    public void addDamagedStock(Long productId, Long unitsDamaged) {
        stockRepository.addDamagedStock(productId, unitsDamaged);
    }

    @Override
    public void addLostStock(Long productId, Long unitsLost) {
        stockRepository.addLostStock(productId, unitsLost);
    }

    @Override
    public void updateBuyingPrice(Long productId, Double buyingPrice) {
        stockRepository.updateBuyingPrice(productId, buyingPrice);
    }

    @Override
    public void updateSellingPrice(Long productId, Double sellingPrice) {
        stockRepository.updateSellingPrice(productId, sellingPrice);
    }
    
}