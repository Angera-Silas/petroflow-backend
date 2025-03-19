package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Stock;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockMapper {

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

    public Stock toEntity(StockDto stockDto, Product product, Organization organization, Facility facility) {
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
        entity.setProduct(product);
        entity.setOrganization(organization);
        entity.setFacility(facility);
        return entity;
    }
}