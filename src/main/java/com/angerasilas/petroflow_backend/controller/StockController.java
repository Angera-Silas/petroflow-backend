package com.angerasilas.petroflow_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.dto.StockInfoDto;
import com.angerasilas.petroflow_backend.service.StockService;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    @PostMapping("/add")
    public ResponseEntity<StockDto> addStock(@RequestBody StockDto stockDto) {
        StockDto createdStock = stockService.createStock(stockDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStock);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<StockDto> getStockById(@PathVariable Long id) {
        StockDto stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<StockDto>> getAllStocks() {
        List<StockDto> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StockDto> updateStock(@PathVariable Long id, @RequestBody StockDto stockDto) {
        StockDto updatedStock = stockService.updateStock(id, stockDto);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/general-stock")
    public ResponseEntity<List<StockInfoDto>> getAllStockInfo(){
        List<StockInfoDto> stocks = stockService.getStockInfo();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/get/stock/organization/{orgId}")
    public ResponseEntity<List<StockInfoDto>> getStockByOrg(@PathVariable("orgId") Long orgId) {
        List<StockInfoDto> stocks = stockService.getStockInfoByOrganization(orgId);
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/get/stock/organization/{orgId}/facility/{facilityId}")
    public ResponseEntity<List<StockInfoDto>> getStockByOrgAndFacility(@PathVariable("orgId") Long orgId, @PathVariable("facilityId")  Long facilityId) {
        List<StockInfoDto> stocks = stockService.getStockInfoByOrganizationAndFacility(orgId,facilityId);
        return ResponseEntity.ok(stocks);
    }
    
}