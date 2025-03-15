package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import com.angerasilas.petroflow_backend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    @Autowired
    private ShiftService shiftService;

    @PostMapping("/add")
    public ResponseEntity<ShiftDto> createShift(@RequestBody ShiftDto shiftDto) {
        ShiftDto createdShift = shiftService.createShift(shiftDto);
        return ResponseEntity.ok(createdShift);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ShiftDto> updateShift(@PathVariable Long id, @RequestBody ShiftDto shiftDto) {
        ShiftDto updatedShift = shiftService.updateShift(id, shiftDto);
        return ResponseEntity.ok(updatedShift);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ShiftDto> getShiftById(@PathVariable Long id) {
        ShiftDto shift = shiftService.getShiftById(id);
        return ResponseEntity.ok(shift);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<ShiftDto>> getAllShifts() {
        List<ShiftDto> shifts = shiftService.getAllShifts();
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/byemployeeno/{employeeNo}")
    public ResponseEntity<List<ShiftDto>> getShiftsByEmployeeNo(@PathVariable String employeeNo) {
        List<ShiftDto> shifts = shiftService.getShiftsByEmployeeNo(employeeNo);
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/byfacility/{facilityId}")
    public ResponseEntity<List<ShiftDto>> getShiftsByFacilityId(@PathVariable Long facilityId) {
        List<ShiftDto> shifts = shiftService.getShiftsByFacilityId(facilityId);
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/bytype/{type}")
    public ResponseEntity<List<ShiftDto>> getShiftsByType(@PathVariable String type) {
        List<ShiftDto> shifts = shiftService.getShiftsByType(type);
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/byef/{employeeNo}/{facilityId}")
    public ResponseEntity<List<ShiftDto>> getShiftsByEmployeeNoAndFacilityId(@PathVariable String employeeNo, @PathVariable Long facilityId) {
        List<ShiftDto> shifts = shiftService.getShiftsByEmployeeNoAndFacilityId(employeeNo, facilityId);
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/byft/{facilityId}/{type}")
    public ResponseEntity<List<ShiftDto>> getShiftsByFacilityIdAndType(@PathVariable Long facilityId, @PathVariable String type) {
        List<ShiftDto> shifts = shiftService.getShiftsByFacilityIdAndType(facilityId, type);
        return ResponseEntity.ok(shifts);
    }

    @GetMapping("/get/byfte/{facilityId}/{type}/{employeeNo}")
    public ResponseEntity<List<ShiftDto>> getShiftsByFacilityIdAndTypeAndEmployeeNo(@PathVariable Long facilityId, @PathVariable String type, @PathVariable String employeeNo) {
        List<ShiftDto> shifts = shiftService.getShiftsByFacilityIdAndTypeAndEmployeeNo(facilityId, type, employeeNo);
        return ResponseEntity.ok(shifts);
    }
}