package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.SalesDto;
import com.angerasilas.petroflow_backend.dto.SalesInfo;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Sales;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.entity.Shift;
import com.angerasilas.petroflow_backend.entity.SyncChange;
import com.angerasilas.petroflow_backend.mapper.SalesMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.repository.SalesRepository;
import com.angerasilas.petroflow_backend.repository.SellPointRepository;
import com.angerasilas.petroflow_backend.repository.ShiftRepository;
import com.angerasilas.petroflow_backend.repository.SyncChangeRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.angerasilas.petroflow_backend.service.SalesService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.angerasilas.petroflow_backend.service.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    @Autowired
    private SyncChangeRepository syncChangeRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @Override
    public List<SalesInfo> getSalesInfo() {
        return salesRepository.getSalesInfo();
    }

    @Override
    public List<SalesInfo> getOrganizationSalesInfo(Long orgId) {
        return salesRepository.getOrganizationSalesInfo(orgId);
    }

    @Override
    public List<SalesInfo> getOrganizationFacilitySalesInfo(Long orgId, Long facilityId) {
        return salesRepository.getOrganizationFacilitySalesInfo(orgId, facilityId);
    }

    @Override
    public List<SalesInfo> getFacilitySalesInfo(Long facilityId) {
        return salesRepository.getFacilitySalesInfo(facilityId);
    }

    @Override
    public List<SalesInfo> getPersonalSalesInfoByOrganization(Long orgId, Long facilityId, String employeeNo) {
        return salesRepository.getPersonalSalesInfoByOrganization(orgId, facilityId, employeeNo);
    }

    @Override
    public List<SalesInfo> getPersonalSalesInfo(String employeeNo) {
        return salesRepository.getPersonalSalesInfo(employeeNo);
    }

    @Override
    public List<SalesInfo> getSellingPointSalesInfo(Long sellPointId) {
        return salesRepository.getSellingPointSalesInfo(sellPointId);
    }

    @Override
    public List<SalesInfo> getProductSalesInfo(Long productId) {
        return salesRepository.getProductSalesInfo(productId);
    }

    @Override
    public List<SalesInfo> getShiftSalesInfo(Long shiftId) {
        return salesRepository.getShiftSalesInfo(shiftId);
    }

    @Override
    public List<SalesInfo> getSalesByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return salesRepository.getSalesByDate(startDate, endDate);
    }

    @Override
    public List<SalesInfo> getYearlySalesInfo(int year) {
        return salesRepository.getYearlySalesInfo(year);
    }

    /**
     * Create sales from sync request
     */
    @Override
    @Transactional
    public boolean createFromSync(JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Long productId = data.get("productId").asLong();
            String employeeNo = data.get("employeeNo").asText();
            Long sellPointId = data.get("sellPointId").asLong();
            Long shiftId = data.get("shiftId").asLong();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(employeeNo)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));
            SellPoint sellPoint = sellPointRepository.findById(sellPointId)
                    .orElseThrow(() -> new RuntimeException("SellPoint not found"));
            Shift shift = shiftRepository.findById(shiftId)
                    .orElseThrow(() -> new RuntimeException("Shift not found"));

            Sales sales = new Sales();
            sales.setDateTime(new Date());
            sales.setProduct(product);
            sales.setEmployee(employee);
            sales.setSellPoint(sellPoint);
            sales.setShift(shift);
            sales.setUnitsSold(data.get("unitsSold").asText());
            sales.setAmountBilled(data.get("amountBilled").asDouble());
            sales.setDiscount(data.get("discount").asDouble());
            sales.setAmountPaid(data.get("amountPaid").asDouble());
            sales.setPaymentMethod(data.get("paymentMethod").asText());
            sales.setPaymentStatus(data.get("paymentStatus").asText());
            sales.setStatus(data.get("status") != null ? data.get("status").asText() : "to be reviewed");

            Sales savedSales = salesRepository.save(sales);

            // Record in SyncChange
            String salesId = String.valueOf(savedSales.getId());
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(salesId)
                    .entityType("SALES")
                    .action("create")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .newValue(objectMapper.writeValueAsString(savedSales))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "CREATE_SALE",
                    "SALES", salesId, savedSales, "Sales created from sync");

            log.info("Sales created from sync: {} by device {}", salesId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to create sales from sync", e);
            auditLogService.logFailure(tenantId, userId, "CREATE_SALE", "SALES",
                    "unknown", e.getMessage(), "Failed to create sales from sync");
            return false;
        }
    }

    /**
     * Update sales from sync request
     */
    @Override
    @Transactional
    public boolean updateFromSync(Long id, JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Sales existingSales = salesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sales not found"));

            // Save old value for audit
            String oldValueJson = objectMapper.writeValueAsString(existingSales);

            // Update fields
            if (data.has("unitsSold")) {
                existingSales.setUnitsSold(data.get("unitsSold").asText());
            }
            if (data.has("amountBilled")) {
                existingSales.setAmountBilled(data.get("amountBilled").asDouble());
            }
            if (data.has("discount")) {
                existingSales.setDiscount(data.get("discount").asDouble());
            }
            if (data.has("amountPaid")) {
                existingSales.setAmountPaid(data.get("amountPaid").asDouble());
            }
            if (data.has("paymentMethod")) {
                existingSales.setPaymentMethod(data.get("paymentMethod").asText());
            }
            if (data.has("paymentStatus")) {
                existingSales.setPaymentStatus(data.get("paymentStatus").asText());
            }
            if (data.has("status")) {
                existingSales.setStatus(data.get("status").asText());
            }

            Sales updatedSales = salesRepository.save(existingSales);

            // Record in SyncChange
            String salesId = String.valueOf(id);
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(salesId)
                    .entityType("SALES")
                    .action("update")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .newValue(objectMapper.writeValueAsString(updatedSales))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logUpdate(tenantId, userId, "EDIT_SALE", "SALES", salesId,
                    oldValueJson, updatedSales, "Sales updated from sync");

            log.info("Sales updated from sync: {} by device {}", salesId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to update sales from sync", e);
            auditLogService.logFailure(tenantId, userId, "EDIT_SALE", "SALES",
                    String.valueOf(id), e.getMessage(), "Failed to update sales from sync");
            return false;
        }
    }

    /**
     * Delete sales from sync request
     */
    @Override
    @Transactional
    public boolean deleteFromSync(Long id, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Sales existingSales = salesRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Sales not found"));

            String oldValueJson = objectMapper.writeValueAsString(existingSales);
            String salesId = String.valueOf(id);

            // Delete the sales
            salesRepository.deleteById(id);

            // Record in SyncChange
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(salesId)
                    .entityType("SALES")
                    .action("delete")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "DELETE_SALE", "SALES",
                    salesId, null, "Sales deleted from sync");

            log.info("Sales deleted from sync: {} by device {}", salesId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to delete sales from sync", e);
            auditLogService.logFailure(tenantId, userId, "DELETE_SALE", "SALES",
                    String.valueOf(id), e.getMessage(), "Failed to delete sales from sync");
            return false;
        }
    }
}