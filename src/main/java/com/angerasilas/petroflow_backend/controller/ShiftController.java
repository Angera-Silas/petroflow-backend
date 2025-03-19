package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.EmployeeShift;
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

    @GetMapping("/get/all/employees")
    public ResponseEntity<List<EmployeeShift>> findAllEmployeeShifts() {
        List<EmployeeShift> employeeShifts = shiftService.findAllEmployeeShifts();
        return ResponseEntity.ok(employeeShifts);
    }

    @GetMapping("/get/org/{orgId}")
    public ResponseEntity<List<EmployeeShift>> findEmployeeShiftsByOrgId(@PathVariable Long orgId) {
        List<EmployeeShift> employeeShifts = shiftService.findEmployeeShiftsByOrgId(orgId);
        return ResponseEntity.ok(employeeShifts);
    }

    @GetMapping("/get/facility/{facilityId}")
    public ResponseEntity<List<EmployeeShift>> findEmployeeShiftsByFacilityId(@PathVariable Long facilityId) {
        List<EmployeeShift> employeeShifts = shiftService.findEmployeeShiftsByFacilityId(facilityId);
        return ResponseEntity.ok(employeeShifts);
    }

    @GetMapping("/get/type/{type}")
    public ResponseEntity<List<EmployeeShift>> findEmployeeShiftsByType(@PathVariable String type) {
        List<EmployeeShift> employeeShifts = shiftService.findEmployeeShiftsByType(type);
        return ResponseEntity.ok(employeeShifts);
    }

    @GetMapping("/get/employee/{employeeNo}")
    public ResponseEntity<List<EmployeeShift>> findEmployeeShiftsByEmployeeNo(@PathVariable String employeeNo) {
        List<EmployeeShift> employeeShifts = shiftService.findEmployeeShiftsByEmployeeNo(employeeNo);
        return ResponseEntity.ok(employeeShifts);
    }

    @GetMapping("/get/active")
    public ResponseEntity<List<ShiftDto>> getActiveShifts() {
        List<ShiftDto> activeShifts = shiftService.getActiveShifts();
        return ResponseEntity.ok(activeShifts);
    }
}