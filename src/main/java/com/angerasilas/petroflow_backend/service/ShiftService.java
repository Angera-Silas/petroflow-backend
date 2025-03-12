package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import java.util.List;

public interface ShiftService {
    ShiftDto createShift(ShiftDto shiftDto);
    ShiftDto updateShift(Long id, ShiftDto shiftDto);
    void deleteShift(Long id);
    ShiftDto getShiftById(Long id);
    List<ShiftDto> getAllShifts();
}