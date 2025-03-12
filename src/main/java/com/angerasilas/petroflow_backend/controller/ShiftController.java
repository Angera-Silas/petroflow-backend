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
}