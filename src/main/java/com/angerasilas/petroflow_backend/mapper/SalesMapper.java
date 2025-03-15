package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Sales;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.entity.Shift;

@Component
public class SalesMapper {

    public static SalesDto mapToSalesDto(Sales sales) {
        if (sales == null) {
            return null;
        }

        return new SalesDto(
            sales.getId(),
            sales.getDateTime(),
            sales.getProduct() != null ? sales.getProduct().getId() : null,
            sales.getEmployee() != null ? sales.getEmployee().getEmployeeNo() : null,
            sales.getSellPoint() != null ? sales.getSellPoint().getId() : null,
            sales.getShift() != null ? sales.getShift().getId() : null,
            sales.getUnitsSold(),
            sales.getAmountBilled(),
            sales.getDiscount(),
            sales.getAmountPaid(),
            sales.getPaymentMethod(),
            sales.getPaymentStatus(),
            sales.getBalance(),
            sales.getStatus()
        );
    }

    public static Sales mapToSales(SalesDto salesDto, Product product, OrganizationEmployees employee, SellPoint sellPoint, Shift shift) {
        if (salesDto == null) {
            return null;
        }

        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        if (sellPoint == null) {
            throw new IllegalArgumentException("SellPoint cannot be null");
        }

        if (shift == null) {
            throw new IllegalArgumentException("Shift cannot be null");
        }

        return new Sales(
            salesDto.getId(),
            salesDto.getDateTime(),
            product,
            employee,
            sellPoint,
            shift,
            salesDto.getUnitsSold(),
            salesDto.getAmountBilled(),
            salesDto.getDiscount(),
            salesDto.getAmountPaid(),
            salesDto.getPaymentMethod(),
            salesDto.getPaymentStatus(),
            salesDto.getBalance(),
            salesDto.getStatus()
        );
    }
}