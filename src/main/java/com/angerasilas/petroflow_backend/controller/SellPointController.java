package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.service.SellPointService;

@RestController
@RequestMapping("/api/sellpoints")
public class SellPointController {

    @Autowired
    private SellPointService sellPointService;

    @PostMapping("/create")
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

    @GetMapping("/get/byname/{name}")
    public ResponseEntity<SellPointDto> getSellPointByName(@PathVariable String name) {
        SellPointDto sellPoint = sellPointService.getSellPointByName(name);
        return ResponseEntity.ok(sellPoint);
    }

    @GetMapping("/get/byfacility/{facilityId}")
    public ResponseEntity<List<SellPointDto>> getSellPointsByFacilityId(@PathVariable("facilityId") Long facilityId) {
        List<SellPointDto> sellPoints = sellPointService.getSellPointsByFacilityId(facilityId);
        return ResponseEntity.ok(sellPoints);
    }

}