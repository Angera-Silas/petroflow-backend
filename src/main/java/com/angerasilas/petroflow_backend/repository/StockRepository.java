package com.angerasilas.petroflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query; // Add this import
import org.springframework.data.repository.query.Param;

import com.angerasilas.petroflow_backend.dto.StockInfoDto;
import com.angerasilas.petroflow_backend.entity.Stock;

import jakarta.transaction.Transactional;

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

        // update stock levels by product id
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.unitsAvailable = s.unitsAvailable - :unitsSold, s.unitsSold = s.unitsSold + :unitsSold WHERE s.product.id = :productId")
    void updateStockLevels(@Param("productId") Long productId, @Param("unitsSold") Long unitsSold);

    // add incoming stock
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.unitsAvailable = s.unitsAvailable + :unitsBought, s.unitsBought = s.unitsBought + :unitsBought WHERE s.product.id = :productId")
    void addIncomingStock(@Param("productId") Long productId, @Param("unitsBought") Long unitsBought);

    // add returned stock
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.unitsAvailable = s.unitsAvailable + :unitsReturned, s.unitsReturned = s.unitsReturned + :unitsReturned WHERE s.product.id = :productId")
    void addReturnedStock(@Param("productId") Long productId, @Param("unitsReturned") Long unitsReturned);

    // add damaged stock
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.unitsAvailable = s.unitsAvailable - :unitsDamaged, s.unitsDamaged = s.unitsDamaged + :unitsDamaged WHERE s.product.id = :productId")
    void addDamagedStock(@Param("productId") Long productId, @Param("unitsDamaged") Long unitsDamaged);

    // add lost stock
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.unitsAvailable = s.unitsAvailable - :unitsLost, s.unitsLost = s.unitsLost + :unitsLost WHERE s.product.id = :productId")
    void addLostStock(@Param("productId") Long productId, @Param("unitsLost") Long unitsLost);

    // update buying price
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.buyingPricePerUnit = :buyingPrice WHERE s.product.id = :productId")
    void updateBuyingPrice(@Param("productId") Long productId, @Param("buyingPrice") Double buyingPrice);

    // update selling price
    @Modifying
    @Transactional
    @Query("UPDATE Stock s SET s.sellingPricePerUnit = :sellingPrice WHERE s.product.id = :productId")
    void updateSellingPrice(@Param("productId") Long productId, @Param("sellingPrice") Double sellingPrice);
}