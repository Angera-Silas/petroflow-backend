package com.angerasilas.petroflow_backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.dto.SalesInfo;
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


    @GetMapping("/get/info/all")
    public List<SalesInfo> getSalesInfo() {
        return salesService.getSalesInfo();
    }

    @GetMapping("/get/organization/{orgId}")
    public List<SalesInfo> getOrganizationSalesInfo(@PathVariable Long orgId) {
        return salesService.getOrganizationSalesInfo(orgId);
    }

    @GetMapping("/get/organization/{orgId}/facility/{facilityId}")
    public List<SalesInfo> getOrganizationFacilitySalesInfo(@PathVariable Long orgId, @PathVariable Long facilityId) {
        return salesService.getOrganizationFacilitySalesInfo(orgId, facilityId);
    }

    @GetMapping("/get/facility/{facilityId}")
    public List<SalesInfo> getFacilitySalesInfo(@PathVariable Long facilityId) {
        return salesService.getFacilitySalesInfo(facilityId);
    }

    @GetMapping("/get/organization/{orgId}/facility/{facilityId}/employee/{employeeNo}")
    public List<SalesInfo> getPersonalSalesInfoByOrganization(@PathVariable Long orgId, @PathVariable Long facilityId, @PathVariable String employeeNo) {
        return salesService.getPersonalSalesInfoByOrganization(orgId, facilityId, employeeNo);
    }

    @GetMapping("/get/employee/{employeeNo}")
    public List<SalesInfo> getPersonalSalesInfo(@PathVariable String employeeNo) {
        return salesService.getPersonalSalesInfo(employeeNo);
    }

    @GetMapping("/get/sellPoint/{sellPointId}")
    public List<SalesInfo> getSellingPointSalesInfo(@PathVariable Long sellPointId) {
        return salesService.getSellingPointSalesInfo(sellPointId);
    }

    @GetMapping("/get/product/{productId}")
    public List<SalesInfo> getProductSalesInfo(@PathVariable Long productId) {
        return salesService.getProductSalesInfo(productId);
    }

    @GetMapping("/get/shift/{shiftId}")
    public List<SalesInfo> getShiftSalesInfo(@PathVariable Long shiftId) {
        return salesService.getShiftSalesInfo(shiftId);
    }

    @GetMapping("/get/date")
    public List<SalesInfo> getSalesByDate(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return salesService.getSalesByDate(startDate, endDate);
    }

    @GetMapping("/year/{year}")
    public List<SalesInfo> getYearlySalesInfo(@PathVariable int year) {
        return salesService.getYearlySalesInfo(year);
    }
}