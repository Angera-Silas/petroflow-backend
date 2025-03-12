package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.entity.Stock;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockMapper {

    private final OrganizationRepository organizationRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;

    public StockDto toDto(Stock stock) {
        StockDto dto = new StockDto();
        dto.setId(stock.getId());
        dto.setDateStocked(stock.getDateStocked());
        dto.setProductId(stock.getProduct().getId());
        dto.setOrgId(stock.getOrganization().getId());
        dto.setFacilityId(stock.getFacility().getId());
        dto.setUnitsAvailable(stock.getUnitsAvailable());
        dto.setUnitsSold(stock.getUnitsSold());
        dto.setUnitsBought(stock.getUnitsBought());
        dto.setUnitsReturned(stock.getUnitsReturned());
        dto.setUnitsDamaged(stock.getUnitsDamaged());
        dto.setUnitsLost(stock.getUnitsLost());
        dto.setBuyingPricePerUnit(stock.getBuyingPricePerUnit());
        dto.setSellingPricePerUnit(stock.getSellingPricePerUnit());
        return dto;
    }

    public Stock toEntity(StockDto stockDto) {
        Stock entity = new Stock();
        entity.setId(stockDto.getId());
        entity.setDateStocked(stockDto.getDateStocked());
        entity.setUnitsAvailable(stockDto.getUnitsAvailable());
        entity.setUnitsSold(stockDto.getUnitsSold());
        entity.setUnitsBought(stockDto.getUnitsBought());
        entity.setUnitsReturned(stockDto.getUnitsReturned());
        entity.setUnitsDamaged(stockDto.getUnitsDamaged());
        entity.setUnitsLost(stockDto.getUnitsLost());
        entity.setBuyingPricePerUnit(stockDto.getBuyingPricePerUnit());
        entity.setSellingPricePerUnit(stockDto.getSellingPricePerUnit());
        entity.setProduct(productRepository.findById(stockDto.getProductId()).orElse(null));
        entity.setOrganization(organizationRepository.findById(stockDto.getOrgId()).orElse(null));
        entity.setFacility(facilityRepository.findById(stockDto.getFacilityId()).orElse(null));
        return entity;
    }
}