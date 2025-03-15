package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Shift;
import com.angerasilas.petroflow_backend.mapper.ShiftMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.repository.ShiftRepository;
import com.angerasilas.petroflow_backend.service.ShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShiftServiceImpl implements ShiftService {

    @Autowired
    private ShiftRepository shiftRepository;

    @Autowired
    private OrganizationEmployeesRepository organizationEmployeesRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(shiftDto.getEmployeeNo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Facility facility = facilityRepository.findById(shiftDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));
        Shift shift = ShiftMapper.mapToShift(shiftDto, employee, facility);
        Shift savedShift = shiftRepository.save(shift);
        return ShiftMapper.mapToShiftDto(savedShift);
    }

    @Override
    public ShiftDto updateShift(Long id, ShiftDto shiftDto) {
        Shift existingShift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(shiftDto.getEmployeeNo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Facility facility = facilityRepository.findById(shiftDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));
        Shift updatedShift = ShiftMapper.mapToShift(shiftDto, employee, facility);
        updatedShift.setId(existingShift.getId());
        Shift savedShift = shiftRepository.save(updatedShift);
        return ShiftMapper.mapToShiftDto(savedShift);
    }

    @Override
    public void deleteShift(Long id) {
        shiftRepository.deleteById(id);
    }

    @Override
    public ShiftDto getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        return ShiftMapper.mapToShiftDto(shift);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShiftDto> getShiftsByEmployeeNo(String employeeNo) {
        return shiftRepository.findByEmployee_EmployeeNo(employeeNo)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ShiftDto> getShiftsByFacilityId(Long facilityId) {
        return shiftRepository.findByFacility_Id(facilityId)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ShiftDto> getShiftsByType(String type) {
        Shift.ShiftType shiftType = Shift.ShiftType.valueOf(type.toUpperCase());
        return shiftRepository.findByType(shiftType)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ShiftDto> getShiftsByEmployeeNoAndFacilityId(String employeeNo, Long facilityId) {
        return shiftRepository.findByEmployee_EmployeeNoAndFacility_Id(employeeNo, facilityId)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ShiftDto> getShiftsByFacilityIdAndType(Long facilityId, String type) {
        Shift.ShiftType shiftType = Shift.ShiftType.valueOf(type.toUpperCase());
        return shiftRepository.findByFacility_IdAndType(facilityId, shiftType)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ShiftDto> getShiftsByFacilityIdAndTypeAndEmployeeNo(Long facilityId, String type, String employeeNo) {
        Shift.ShiftType shiftType = Shift.ShiftType.valueOf(type.toUpperCase());
        return shiftRepository.findByFacility_IdAndTypeAndEmployee_EmployeeNo(facilityId, shiftType, employeeNo)
                .stream()
                .map(ShiftMapper::mapToShiftDto)
                .collect(Collectors.toList());
    }
    
}