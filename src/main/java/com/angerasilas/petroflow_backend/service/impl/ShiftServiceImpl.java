package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import com.angerasilas.petroflow_backend.entity.Shift;
import com.angerasilas.petroflow_backend.mapper.ShiftMapper;
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
    private ShiftMapper shiftMapper;

    @Override
    public ShiftDto createShift(ShiftDto shiftDto) {
        Shift shift = shiftMapper.toEntity(shiftDto);
        Shift savedShift = shiftRepository.save(shift);
        return shiftMapper.toDto(savedShift);
    }

    @Override
    public ShiftDto updateShift(Long id, ShiftDto shiftDto) {
        Shift existingShift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        Shift updatedShift = shiftMapper.toEntity(shiftDto);
        updatedShift.setId(existingShift.getId());
        Shift savedShift = shiftRepository.save(updatedShift);
        return shiftMapper.toDto(savedShift);
    }

    @Override
    public void deleteShift(Long id) {
        shiftRepository.deleteById(id);
    }

    @Override
    public ShiftDto getShiftById(Long id) {
        Shift shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift not found"));
        return shiftMapper.toDto(shift);
    }

    @Override
    public List<ShiftDto> getAllShifts() {
        return shiftRepository.findAll().stream()
                .map(shiftMapper::toDto)
                .collect(Collectors.toList());
    }
}