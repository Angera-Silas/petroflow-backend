package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.PumpMeterReadingDto;
import com.angerasilas.petroflow_backend.service.PumpMeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pump-meter-readings")
public class PumpMeterReadingController {

    @Autowired
    private PumpMeterReadingService pumpMeterReadingService;

    @PostMapping("/add")
    public ResponseEntity<PumpMeterReadingDto> createPumpMeterReading(@RequestBody PumpMeterReadingDto pumpMeterReadingDto) {
        PumpMeterReadingDto createdPumpMeterReading = pumpMeterReadingService.createPumpMeterReading(pumpMeterReadingDto);
        return ResponseEntity.ok(createdPumpMeterReading);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PumpMeterReadingDto> updatePumpMeterReading(@PathVariable Long id, @RequestBody PumpMeterReadingDto pumpMeterReadingDto) {
        PumpMeterReadingDto updatedPumpMeterReading = pumpMeterReadingService.updatePumpMeterReading(id, pumpMeterReadingDto);
        return ResponseEntity.ok(updatedPumpMeterReading);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePumpMeterReading(@PathVariable Long id) {
        pumpMeterReadingService.deletePumpMeterReading(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PumpMeterReadingDto> getPumpMeterReadingById(@PathVariable Long id) {
        PumpMeterReadingDto pumpMeterReading = pumpMeterReadingService.getPumpMeterReadingById(id);
        return ResponseEntity.ok(pumpMeterReading);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<PumpMeterReadingDto>> getAllPumpMeterReadings() {
        List<PumpMeterReadingDto> pumpMeterReadings = pumpMeterReadingService.getAllPumpMeterReadings();
        return ResponseEntity.ok(pumpMeterReadings);
    }
}