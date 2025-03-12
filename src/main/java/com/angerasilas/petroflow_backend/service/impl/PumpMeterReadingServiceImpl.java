package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.PumpMeterReadingDto;
import com.angerasilas.petroflow_backend.entity.PumpMeterReading;
import com.angerasilas.petroflow_backend.mapper.PumpMeterReadingMapper;
import com.angerasilas.petroflow_backend.repository.PumpMeterReadingRepository;
import com.angerasilas.petroflow_backend.service.PumpMeterReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PumpMeterReadingServiceImpl implements PumpMeterReadingService {

    @Autowired
    private PumpMeterReadingRepository pumpMeterReadingRepository;

    @Autowired
    private PumpMeterReadingMapper pumpMeterReadingMapper;

    @Override
    public PumpMeterReadingDto createPumpMeterReading(PumpMeterReadingDto pumpMeterReadingDto) {
        PumpMeterReading pumpMeterReading = pumpMeterReadingMapper.toEntity(pumpMeterReadingDto);
        PumpMeterReading savedPumpMeterReading = pumpMeterReadingRepository.save(pumpMeterReading);
        return pumpMeterReadingMapper.toDto(savedPumpMeterReading);
    }

    @Override
    public PumpMeterReadingDto updatePumpMeterReading(Long id, PumpMeterReadingDto pumpMeterReadingDto) {
        PumpMeterReading existingPumpMeterReading = pumpMeterReadingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PumpMeterReading not found"));
        PumpMeterReading updatedPumpMeterReading = pumpMeterReadingMapper.toEntity(pumpMeterReadingDto);
        updatedPumpMeterReading.setId(existingPumpMeterReading.getId());
        PumpMeterReading savedPumpMeterReading = pumpMeterReadingRepository.save(updatedPumpMeterReading);
        return pumpMeterReadingMapper.toDto(savedPumpMeterReading);
    }

    @Override
    public void deletePumpMeterReading(Long id) {
        pumpMeterReadingRepository.deleteById(id);
    }

    @Override
    public PumpMeterReadingDto getPumpMeterReadingById(Long id) {
        PumpMeterReading pumpMeterReading = pumpMeterReadingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PumpMeterReading not found"));
        return pumpMeterReadingMapper.toDto(pumpMeterReading);
    }

    @Override
    public List<PumpMeterReadingDto> getAllPumpMeterReadings() {
        return pumpMeterReadingRepository.findAll().stream()
                .map(pumpMeterReadingMapper::toDto)
                .collect(Collectors.toList());
    }
}