package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.MeterReadingDto;
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

    @GetMapping("/get/general/meter-reading")
    public ResponseEntity<List<MeterReadingDto>> getMeterReading() {
        List<MeterReadingDto> meterReadings = pumpMeterReadingService.getMeterReading();
        return ResponseEntity.ok(meterReadings);
    }

    @GetMapping("/get/meter-reading/{id}")
    public ResponseEntity<MeterReadingDto> getMeterReadingById(@PathVariable Long id) {
        MeterReadingDto meterReading = pumpMeterReadingService.getMeterReadingById(id);
        return ResponseEntity.ok(meterReading);
    }

    @GetMapping("/get/meter-reading/org/{orgId}")
    public ResponseEntity<List<MeterReadingDto>> getMeterReadingByOrgId(@PathVariable Long orgId) {
        List<MeterReadingDto> meterReadings = pumpMeterReadingService.getMeterReadingByOrgId(orgId);
        return ResponseEntity.ok(meterReadings);
    }

    @GetMapping("/get/meter-reading/facility/{facilityId}")
    public ResponseEntity<List<MeterReadingDto>> getMeterReadingByFacilityId(@PathVariable Long facilityId) {
        List<MeterReadingDto> meterReadings = pumpMeterReadingService.getMeterReadingByFacilityId(facilityId);
        return ResponseEntity.ok(meterReadings);
    }


    @GetMapping("/get/meter-reading/sell-point/{sellPointId}")
    public ResponseEntity<List<MeterReadingDto>> getMeterReadingBySellPointId(@PathVariable Long sellPointId) {
        List<MeterReadingDto> meterReadings = pumpMeterReadingService.getMeterReadingBySellPointId(sellPointId);
        return ResponseEntity.ok(meterReadings);
    }

    @GetMapping("/get/meter-reading/shift/{shiftId}")
    public ResponseEntity<List<MeterReadingDto>> getMeterReadingByShiftId(@PathVariable Long shiftId) {
        List<MeterReadingDto> meterReadings = pumpMeterReadingService.getMeterReadingByShiftId(shiftId);
        return ResponseEntity.ok(meterReadings);
    }



}