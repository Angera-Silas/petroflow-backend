package com.angerasilas.petroflow_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.service.SalesService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sales")
public class SalesController {

    private final SalesService salesService;

    @PostMapping("/add")
    public ResponseEntity<SalesDto> addSales(@RequestBody SalesDto salesDto) {
        SalesDto createdSales = salesService.createSales(salesDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSales);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SalesDto> getSalesById(@PathVariable Long id) {
        SalesDto sales = salesService.getSalesById(id);
        return ResponseEntity.ok(sales);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SalesDto>> getAllSales() {
        List<SalesDto> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SalesDto> updateSales(@PathVariable Long id, @RequestBody SalesDto salesDto) {
        SalesDto updatedSales = salesService.updateSales(id, salesDto);
        return ResponseEntity.ok(updatedSales);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSales(@PathVariable Long id) {
        salesService.deleteSales(id);
        return ResponseEntity.noContent().build();
    }
}