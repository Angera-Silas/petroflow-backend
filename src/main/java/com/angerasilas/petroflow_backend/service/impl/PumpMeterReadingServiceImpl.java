package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.MeterReadingDto;
import com.angerasilas.petroflow_backend.dto.PumpMeterReadingDto;
import com.angerasilas.petroflow_backend.entity.PumpMeterReading;
import com.angerasilas.petroflow_backend.mapper.PumpMeterReadingMapper;
import com.angerasilas.petroflow_backend.repository.PumpMeterReadingRepository;
import com.angerasilas.petroflow_backend.service.PumpMeterReadingService;

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
    public PumpMeterReadingDto updateEndPumpMeterReading(Long id, PumpMeterReadingDto updatedDto) {
        PumpMeterReading pumpMeterReading = pumpMeterReadingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PumpMeterReading not found with id: " + id));

        // Update only the required fields
        pumpMeterReading.setEndReading(updatedDto.getEndReading());
        pumpMeterReading.setUpdatedBy(updatedDto.getUpdatedBy());
        pumpMeterReading.setUpdatedAt(updatedDto.getUpdatedAt());
        pumpMeterReading.setStatus(updatedDto.getStatus());
        pumpMeterReading.setTotalVolume(updatedDto.getTotalVolume());

        // Save the updated entity
        PumpMeterReading updatedEntity = pumpMeterReadingRepository.save(pumpMeterReading);

        // Return the updated DTO
        return new PumpMeterReadingDto(
                updatedEntity.getId(),
                updatedEntity.getOrganization().getId(),
                updatedEntity.getFacility().getId(),
                updatedEntity.getSellPoint().getId(),
                updatedEntity.getShift().getId(),
                updatedEntity.getStartReading(),
                updatedEntity.getEndReading(),
                updatedEntity.getReadingDate(),
                updatedEntity.getCreatedAt(),
                updatedEntity.getUpdatedAt(),
                updatedEntity.getCreatedBy(),
                updatedEntity.getUpdatedBy(),
                updatedEntity.getStatus(),
                updatedEntity.getTotalVolume());
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

    @Override
    public List<MeterReadingDto> getMeterReading() {
        return pumpMeterReadingRepository.findMeterReading();
    }

    @Override
    public MeterReadingDto getMeterReadingById(Long id) {
        return pumpMeterReadingRepository.findMeterReadingById(id);
    }

    @Override
    public List<MeterReadingDto> getMeterReadingByOrgId(Long orgId) {
        return pumpMeterReadingRepository.findMeterReadingByOrgId(orgId);
    }

    @Override
    public List<MeterReadingDto> getMeterReadingByFacilityId(Long facilityId) {
        return pumpMeterReadingRepository.findMeterReadingByFacilityId(facilityId);
    }

    @Override
    public List<MeterReadingDto> getMeterReadingBySellPointId(Long sellPointId) {
        return pumpMeterReadingRepository.findMeterReadingBySellPointId(sellPointId);
    }

    @Override
    public List<MeterReadingDto> getMeterReadingByShiftId(Long shiftId) {
        return pumpMeterReadingRepository.findMeterReadingByShiftId(shiftId);
    }

}