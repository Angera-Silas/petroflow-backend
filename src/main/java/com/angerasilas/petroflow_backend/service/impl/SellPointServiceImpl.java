package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.mapper.SellPointMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.SellPointRepository;
import com.angerasilas.petroflow_backend.service.SellPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SellPointServiceImpl implements SellPointService {

    @Autowired
    private SellPointRepository sellPointRepository;

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public SellPointDto createSellPoint(SellPointDto sellPointDto) {
        Facility facility = facilityRepository.findById(sellPointDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));
        SellPoint sellPoint = SellPointMapper.mapToSellPoint(sellPointDto, facility);
        SellPoint savedSellPoint = sellPointRepository.save(sellPoint);
        return SellPointMapper.mapToSellPointDto(savedSellPoint);
    }

    @Override
    public SellPointDto updateSellPoint(Long id, SellPointDto sellPointDto) {
        SellPoint existingSellPoint = sellPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        Facility facility = facilityRepository.findById(sellPointDto.getFacilityId())
                .orElseThrow(() -> new RuntimeException("Facility not found"));
        SellPoint updatedSellPoint = SellPointMapper.mapToSellPoint(sellPointDto, facility);
        updatedSellPoint.setId(existingSellPoint.getId());
        SellPoint savedSellPoint = sellPointRepository.save(updatedSellPoint);
        return SellPointMapper.mapToSellPointDto(savedSellPoint);
    }

    @Override
    public void deleteSellPoint(Long id) {
        sellPointRepository.deleteById(id);
    }

    @Override
    public SellPointDto getSellPointById(Long id) {
        SellPoint sellPoint = sellPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        return SellPointMapper.mapToSellPointDto(sellPoint);
    }

    @Override
    public List<SellPointDto> getAllSellPoints() {
        return sellPointRepository.findAll().stream()
                .map(SellPointMapper::mapToSellPointDto)
                .collect(Collectors.toList());
    }

    @Override
    public SellPointDto getSellPointByName(String name) {
        SellPoint sellPoint = sellPointRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        return SellPointMapper.mapToSellPointDto(sellPoint);
    }

    @Override
    public List<SellPointDto> getSellPointsByFacilityId(Long facilityId) {
        return sellPointRepository.findByFacilityId(facilityId).stream()
                .map(SellPointMapper::mapToSellPointDto)
                .collect(Collectors.toList());
    }
}