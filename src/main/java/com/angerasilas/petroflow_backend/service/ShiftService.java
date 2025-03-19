package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.EmployeeShift;
import com.angerasilas.petroflow_backend.dto.ShiftDto;
import java.util.List;

public interface ShiftService {
    ShiftDto createShift(ShiftDto shiftDto);
    ShiftDto updateShift(Long id, ShiftDto shiftDto);
    void deleteShift(Long id);
    ShiftDto getShiftById(Long id);
    List<ShiftDto> getAllShifts();
    List<ShiftDto> getShiftsByEmployeeNo(String employeeNo);
    List<ShiftDto> getShiftsByFacilityId(Long facilityId);
    List<ShiftDto> getShiftsByType(String type);
    List<ShiftDto> getShiftsByEmployeeNoAndFacilityId(String employeeNo, Long facilityId);
    List<ShiftDto> getShiftsByFacilityIdAndType(Long facilityId, String type);
    List<ShiftDto> getShiftsByFacilityIdAndTypeAndEmployeeNo(Long facilityId, String type, String employeeNo);

    List<EmployeeShift> findAllEmployeeShifts();
    List<EmployeeShift> findEmployeeShiftsByOrgId(Long orgId);
    List<EmployeeShift> findEmployeeShiftsByFacilityId(Long facilityId);
    List<EmployeeShift> findEmployeeShiftsByType(String type);
    List<EmployeeShift> findEmployeeShiftsByEmployeeNo(String employeeNo);

    List<ShiftDto> getActiveShifts();
}