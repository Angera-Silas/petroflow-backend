package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.entity.Sales;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;

import org.springframework.stereotype.Component;

@Component
public class SalesMapper {

    private OrganizationEmployeesRepository organizationRepository;
    private ProductRepository productRepository;

    public SalesDto toDto(Sales sales) {
        if (sales == null) {
            return null;
        }

        SalesDto salesDto = new SalesDto();
        salesDto.setId(sales.getId());
        salesDto.setDateTime(sales.getDateTime());
        salesDto.setProductId(sales.getProduct() != null ? sales.getProduct().getId() : null);
        salesDto.setEmployeeNo(sales.getEmployee() != null ? sales.getEmployee().getEmployeeNo() : null);
        salesDto.setUnitsSold(sales.getUnitsSold());
        salesDto.setAmountBilled(sales.getAmountBilled());
        salesDto.setDiscount(sales.getDiscount());
        salesDto.setAmountPaid(sales.getAmountPaid());
        salesDto.setPaymentMethod(sales.getPaymentMethod());
        salesDto.setPaymentStatus(sales.getPaymentStatus());
        salesDto.setBalance(sales.getBalance());
        salesDto.setStatus(sales.getStatus());

        return salesDto;
    }

    public Sales toEntity(SalesDto salesDto) {
        if (salesDto == null) {
            return null;
        }

        Sales sales = new Sales();
        sales.setId(salesDto.getId());
        sales.setDateTime(salesDto.getDateTime());
        sales.setProduct(productRepository.findById(salesDto.getProductId()).orElse(null));
        sales.getProduct().setId(salesDto.getProductId());
        sales.setEmployee(organizationRepository.findByEmployeeNo(salesDto.getEmployeeNo()).orElse(null));
        sales.getEmployee().setEmployeeNo(salesDto.getEmployeeNo());
        sales.setUnitsSold(salesDto.getUnitsSold());
        sales.setAmountBilled(salesDto.getAmountBilled());
        sales.setDiscount(salesDto.getDiscount());
        sales.setAmountPaid(salesDto.getAmountPaid());
        sales.setPaymentMethod(salesDto.getPaymentMethod());
        sales.setPaymentStatus(salesDto.getPaymentStatus());
        sales.setBalance(salesDto.getBalance());
        sales.setStatus(salesDto.getStatus());

        return sales;
    }
}