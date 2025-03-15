package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Sales;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.entity.Shift;
import com.angerasilas.petroflow_backend.mapper.SalesMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.repository.SalesRepository;
import com.angerasilas.petroflow_backend.repository.SellPointRepository;
import com.angerasilas.petroflow_backend.repository.ShiftRepository;
import com.angerasilas.petroflow_backend.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrganizationEmployeesRepository organizationEmployeesRepository;

    @Autowired
    private SellPointRepository sellPointRepository;

    @Autowired
    private ShiftRepository shiftRepository;

    @Override
    public SalesDto createSales(SalesDto salesDto) {
        Product product = productRepository.findById(salesDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(salesDto.getEmployeeNo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        SellPoint sellPoint = sellPointRepository.findById(salesDto.getSellPointId())
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        Shift shift = shiftRepository.findById(salesDto.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        Sales sales = SalesMapper.mapToSales(salesDto, product, employee, sellPoint, shift);
        Sales savedSales = salesRepository.save(sales);
        return SalesMapper.mapToSalesDto(savedSales);
    }

    @Override
    public SalesDto updateSales(Long id, SalesDto salesDto) {
        Sales existingSales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales not found"));
        Product product = productRepository.findById(salesDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(salesDto.getEmployeeNo())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        SellPoint sellPoint = sellPointRepository.findById(salesDto.getSellPointId())
                .orElseThrow(() -> new RuntimeException("SellPoint not found"));
        Shift shift = shiftRepository.findById(salesDto.getShiftId())
                .orElseThrow(() -> new RuntimeException("Shift not found"));

        Sales updatedSales = SalesMapper.mapToSales(salesDto, product, employee, sellPoint, shift);
        updatedSales.setId(existingSales.getId());
        Sales savedSales = salesRepository.save(updatedSales);
        return SalesMapper.mapToSalesDto(savedSales);
    }

    @Override
    public void deleteSales(Long id) {
        salesRepository.deleteById(id);
    }

    @Override
    public SalesDto getSalesById(Long id) {
        Sales sales = salesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sales not found"));
        return SalesMapper.mapToSalesDto(sales);
    }

    @Override
    public List<SalesDto> getAllSales() {
        return salesRepository.findAll().stream()
                .map(SalesMapper::mapToSalesDto)
                .collect(Collectors.toList());
    }
}