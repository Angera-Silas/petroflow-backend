package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.mapper.SellPointMapper;
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
    private SellPointMapper sellPointMapper;

    @Override
    public SellPointDto createSellPoint(SellPointDto sellPointDto) {
        SellPoint sellPoint = sellPointMapper.toEntity(sellPointDto);
        SellPoint savedSellPoint = sellPointRepository.save(sellPoint);
        return sellPointMapper.toDto(savedSellPoint);
    }

    @Override
    public SellPointDto updateSellPoint(Long id, SellPointDto sellPointDto) {
        SellPoint existingSellPoint = sellPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        SellPoint updatedSellPoint = sellPointMapper.toEntity(sellPointDto);
        updatedSellPoint.setId(existingSellPoint.getId());
        SellPoint savedSellPoint = sellPointRepository.save(updatedSellPoint);
        return sellPointMapper.toDto(savedSellPoint);
    }

    @Override
    public void deleteSellPoint(Long id) {
        sellPointRepository.deleteById(id);
    }

    @Override
    public SellPointDto getSellPointById(Long id) {
        SellPoint sellPoint = sellPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        return sellPointMapper.toDto(sellPoint);
    }

    @Override
    public List<SellPointDto> getAllSellPoints() {
        return sellPointRepository.findAll().stream()
                .map(sellPointMapper::toDto)
                .collect(Collectors.toList());
    }
}