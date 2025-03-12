package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.entity.Sales;
import com.angerasilas.petroflow_backend.mapper.SalesMapper;
import com.angerasilas.petroflow_backend.repository.SalesRepository;
import com.angerasilas.petroflow_backend.service.SalesService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SalesServiceImpl implements SalesService {

    private final SalesRepository salesRepository;
    private final SalesMapper salesMapper;

    @Override
    public SalesDto createSales(SalesDto salesDto) {
        Sales sales = salesMapper.toEntity(salesDto);
        sales = salesRepository.save(sales);
        return salesMapper.toDto(sales);
    }

    @Override
    public SalesDto getSalesById(Long id) {
        Sales sales = salesRepository.findById(id).orElseThrow(() -> new RuntimeException("Sales not found"));
        return salesMapper.toDto(sales);
    }

    @Override
    public List<SalesDto> getAllSales() {
        return salesRepository.findAll().stream().map(salesMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public SalesDto updateSales(Long id, SalesDto salesDto) {
        Sales sales = salesRepository.findById(id).orElseThrow(() -> new RuntimeException("Sales not found"));
        sales.setDateTime(salesDto.getDateTime());
        sales.setUnitsSold(salesDto.getUnitsSold());
        sales.setAmountBilled(salesDto.getAmountBilled());
        sales.setDiscount(salesDto.getDiscount());
        sales.setAmountPaid(salesDto.getAmountPaid());
        sales.setPaymentMethod(salesDto.getPaymentMethod());
        sales.setPaymentStatus(salesDto.getPaymentStatus());
        sales.setStatus(salesDto.getStatus());
        sales = salesRepository.save(sales);
        return salesMapper.toDto(sales);
    }

    @Override
    public void deleteSales(Long id) {
        salesRepository.deleteById(id);
    }
}