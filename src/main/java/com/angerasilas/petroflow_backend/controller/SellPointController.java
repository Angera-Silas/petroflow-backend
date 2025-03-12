package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.service.SellPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sell-points")
public class SellPointController {

    @Autowired
    private SellPointService sellPointService;

    @PostMapping("/add")
    public ResponseEntity<SellPointDto> createSellPoint(@RequestBody SellPointDto sellPointDto) {
        SellPointDto createdSellPoint = sellPointService.createSellPoint(sellPointDto);
        return ResponseEntity.ok(createdSellPoint);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SellPointDto> updateSellPoint(@PathVariable Long id, @RequestBody SellPointDto sellPointDto) {
        SellPointDto updatedSellPoint = sellPointService.updateSellPoint(id, sellPointDto);
        return ResponseEntity.ok(updatedSellPoint);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSellPoint(@PathVariable Long id) {
        sellPointService.deleteSellPoint(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<SellPointDto> getSellPointById(@PathVariable Long id) {
        SellPointDto sellPoint = sellPointService.getSellPointById(id);
        return ResponseEntity.ok(sellPoint);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<SellPointDto>> getAllSellPoints() {
        List<SellPointDto> sellPoints = sellPointService.getAllSellPoints();
        return ResponseEntity.ok(sellPoints);
    }

}