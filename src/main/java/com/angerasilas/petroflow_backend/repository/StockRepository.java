package com.angerasilas.petroflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query; // Add this import
import org.springframework.data.repository.query.Param;

import com.angerasilas.petroflow_backend.dto.StockInfoDto;
import com.angerasilas.petroflow_backend.entity.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.StockInfoDto(s.id, s.dateStocked, p.id, p.productName, o.id, o.name, f.id, f.name, s.unitsAvailable, s.unitsSold, s.unitsBought, s.unitsReturned, s.unitsDamaged, s.unitsLost, s.buyingPricePerUnit, s.sellingPricePerUnit) "
            +
            "FROM Stock s " +
            "JOIN s.product p " +
            "JOIN s.organization o " +
            "JOIN s.facility f")
    List<StockInfoDto> findStockInfo();

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.StockInfoDto(s.id, s.dateStocked, p.id, p.productName, o.id, o.name, f.id, f.name, s.unitsAvailable, s.unitsSold, s.unitsBought, s.unitsReturned, s.unitsDamaged, s.unitsLost, s.buyingPricePerUnit, s.sellingPricePerUnit) "
            +
            "FROM Stock s " +
            "JOIN s.product p " +
            "JOIN s.organization o " +
            "JOIN s.facility f " +
            "WHERE o.id = :orgId")
    List<StockInfoDto> findStockInfoByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.StockInfoDto(s.id, s.dateStocked, p.id, p.productName, o.id, o.name, f.id, f.name, s.unitsAvailable, s.unitsSold, s.unitsBought, s.unitsReturned, s.unitsDamaged, s.unitsLost, s.buyingPricePerUnit, s.sellingPricePerUnit) "
            +
            "FROM Stock s " +
            "JOIN s.product p " +
            "JOIN s.organization o " +
            "JOIN s.facility f " +
            "WHERE o.id = :orgId AND f.id = :facilityId")
    List<StockInfoDto> findStockInfoByOrgIdAndFacilityId(@Param("orgId") Long orgId,
            @Param("facilityId") Long facilityId);
}