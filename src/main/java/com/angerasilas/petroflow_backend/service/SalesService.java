package com.angerasilas.petroflow_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.dto.SalesInfo;

public interface SalesService {
    SalesDto createSales(SalesDto salesDto);
    SalesDto getSalesById(Long id);
    List<SalesDto> getAllSales();
    SalesDto updateSales(Long id, SalesDto salesDto);
    void deleteSales(Long id);
    List<SalesInfo> getSalesInfo();
    List<SalesInfo> getOrganizationSalesInfo(Long orgId);
    List<SalesInfo> getOrganizationFacilitySalesInfo(Long orgId, Long facilityId);
    List<SalesInfo> getFacilitySalesInfo(Long facilityId);
    List<SalesInfo> getPersonalSalesInfoByOrganization(Long orgId, Long facilityId, String employeeNo);
    List<SalesInfo> getPersonalSalesInfo(String employeeNo);
    List<SalesInfo> getSellingPointSalesInfo(Long sellPointId);
    List<SalesInfo> getProductSalesInfo(Long productId);
    List<SalesInfo> getShiftSalesInfo(Long shiftId);
    List<SalesInfo> getSalesByDate(LocalDateTime startDate, LocalDateTime endDate);
    List<SalesInfo> getYearlySalesInfo(int year);
}