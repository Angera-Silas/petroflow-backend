package com.angerasilas.petroflow_backend.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.StockDto;
import com.angerasilas.petroflow_backend.dto.StockInfoDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.Product;
import com.angerasilas.petroflow_backend.entity.Stock;
import com.angerasilas.petroflow_backend.entity.SyncChange;
import com.angerasilas.petroflow_backend.mapper.StockMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.repository.ProductRepository;
import com.angerasilas.petroflow_backend.repository.StockRepository;
import com.angerasilas.petroflow_backend.repository.SyncChangeRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.angerasilas.petroflow_backend.service.StockService;
import com.angerasilas.petroflow_backend.service.AuditLogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class StockServiceImpl implements StockService {

    @Autowired
    private final StockRepository stockRepository;

    @Autowired
    private final StockMapper stockMapper;

    @Autowired
    private final ProductRepository productRepository;

    @Autowired
    private final OrganizationRepository organizationRepository;

    @Autowired
    private final FacilityRepository facilityRepository;

    @Autowired
    private SyncChangeRepository syncChangeRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public StockDto createStock(StockDto stockDto) {
        Product product = productRepository.findById(stockDto.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));
        Organization organization = organizationRepository.findById(stockDto.getOrgId()).orElseThrow(() -> new RuntimeException("Organization not found"));
        Facility facility = facilityRepository.findById(stockDto.getFacilityId()).orElseThrow(() -> new RuntimeException("Facility not found"));
        Stock stock = stockMapper.toEntity(stockDto, product, organization, facility);
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public StockDto getStockById(Long id) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        return stockMapper.toDto(stock);
    }

    @Override
    public List<StockDto> getAllStocks() {
        return stockRepository.findAll().stream().map(stockMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public StockDto updateStock(Long id, StockDto stockDto) {
        Stock stock = stockRepository.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
        stock.setDateStocked(stockDto.getDateStocked());
        stock.setUnitsAvailable(stockDto.getUnitsAvailable());
        stock.setUnitsSold(stockDto.getUnitsSold());
        stock.setUnitsBought(stockDto.getUnitsBought());
        stock.setUnitsReturned(stockDto.getUnitsReturned());
        stock.setUnitsDamaged(stockDto.getUnitsDamaged());
        stock.setUnitsLost(stockDto.getUnitsLost());
        stock.setBuyingPricePerUnit(stockDto.getBuyingPricePerUnit());
        stock.setSellingPricePerUnit(stockDto.getSellingPricePerUnit());
        stock = stockRepository.save(stock);
        return stockMapper.toDto(stock);
    }

    @Override
    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }

    @Override
    public List<StockInfoDto> getStockInfo(){
        return stockRepository.findStockInfo();
    }

    @Override
    public List<StockInfoDto> getStockInfoByOrganization(Long orgId){
        return stockRepository.findStockInfoByOrgId(orgId);
    }

    @Override
    public List<StockInfoDto> getStockInfoByOrganizationAndFacility(Long orgId, Long facilityId){
        return stockRepository.findStockInfoByOrgIdAndFacilityId(orgId, facilityId);
    }

    @Override
    public void updateStockLevels(Long productId, Long unitsSold) {
        stockRepository.updateStockLevels(productId, unitsSold);
    }

    @Override
    public void addIncomingStock(Long productId, Long unitsBought) {
        stockRepository.addIncomingStock(productId, unitsBought);
    }

    @Override
    public void addReturnedStock(Long productId, Long unitsReturned) {
        stockRepository.addReturnedStock(productId, unitsReturned);
    }

    @Override
    public void addDamagedStock(Long productId, Long unitsDamaged) {
        stockRepository.addDamagedStock(productId, unitsDamaged);
    }

    @Override
    public void addLostStock(Long productId, Long unitsLost) {
        stockRepository.addLostStock(productId, unitsLost);
    }

    @Override
    public void updateBuyingPrice(Long productId, Double buyingPrice) {
        stockRepository.updateBuyingPrice(productId, buyingPrice);
    }

    @Override
    public void updateSellingPrice(Long productId, Double sellingPrice) {
        stockRepository.updateSellingPrice(productId, sellingPrice);
    }

    /**
     * Create stock from sync request
     */
    @Override
    public boolean createFromSync(JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Long productId = data.get("productId").asLong();
            Long orgId = data.get("orgId").asLong();
            Long facilityId = data.get("facilityId").asLong();

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            Organization organization = organizationRepository.findById(orgId)
                    .orElseThrow(() -> new RuntimeException("Organization not found"));
            Facility facility = facilityRepository.findById(facilityId)
                    .orElseThrow(() -> new RuntimeException("Facility not found"));

            Stock stock = new Stock();
            stock.setProduct(product);
            stock.setOrganization(organization);
            stock.setFacility(facility);
            stock.setUnitsAvailable(data.get("unitsAvailable").asLong());
            stock.setUnitsSold(data.get("unitsSold") != null ? data.get("unitsSold").asLong() : 0L);
            stock.setUnitsBought(data.get("unitsBought") != null ? data.get("unitsBought").asLong() : 0L);
            stock.setUnitsReturned(data.get("unitsReturned") != null ? data.get("unitsReturned").asLong() : 0L);
            stock.setUnitsDamaged(data.get("unitsDamaged") != null ? data.get("unitsDamaged").asLong() : 0L);
            stock.setUnitsLost(data.get("unitsLost") != null ? data.get("unitsLost").asLong() : 0L);
            stock.setBuyingPricePerUnit(data.get("buyingPricePerUnit").asDouble());
            stock.setSellingPricePerUnit(data.get("sellingPricePerUnit").asDouble());

            Stock savedStock = stockRepository.save(stock);

            // Record in SyncChange
            String stockId = String.valueOf(savedStock.getId());
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(stockId)
                    .entityType("STOCK")
                    .action("create")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .newValue(objectMapper.writeValueAsString(savedStock))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "CREATE_STOCK",
                    "STOCK", stockId, savedStock, "Stock created from sync");

            log.info("Stock created from sync: {} by device {}", stockId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to create stock from sync", e);
            auditLogService.logFailure(tenantId, userId, "CREATE_STOCK", "STOCK",
                    "unknown", e.getMessage(), "Failed to create stock from sync");
            return false;
        }
    }

    /**
     * Update stock from sync request
     */
    @Override
    public boolean updateFromSync(Long id, JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Stock existingStock = stockRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Stock not found"));

            // Save old value for audit
            String oldValueJson = objectMapper.writeValueAsString(existingStock);

            // Update fields
            if (data.has("unitsAvailable")) {
                existingStock.setUnitsAvailable(data.get("unitsAvailable").asLong());
            }
            if (data.has("unitsSold")) {
                existingStock.setUnitsSold(data.get("unitsSold").asLong());
            }
            if (data.has("unitsBought")) {
                existingStock.setUnitsBought(data.get("unitsBought").asLong());
            }
            if (data.has("unitsReturned")) {
                existingStock.setUnitsReturned(data.get("unitsReturned").asLong());
            }
            if (data.has("unitsDamaged")) {
                existingStock.setUnitsDamaged(data.get("unitsDamaged").asLong());
            }
            if (data.has("unitsLost")) {
                existingStock.setUnitsLost(data.get("unitsLost").asLong());
            }
            if (data.has("buyingPricePerUnit")) {
                existingStock.setBuyingPricePerUnit(data.get("buyingPricePerUnit").asDouble());
            }
            if (data.has("sellingPricePerUnit")) {
                existingStock.setSellingPricePerUnit(data.get("sellingPricePerUnit").asDouble());
            }

            Stock updatedStock = stockRepository.save(existingStock);

            // Record in SyncChange
            String stockId = String.valueOf(id);
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(stockId)
                    .entityType("STOCK")
                    .action("update")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .newValue(objectMapper.writeValueAsString(updatedStock))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logUpdate(tenantId, userId, "UPDATE_STOCK", "STOCK", stockId,
                    oldValueJson, updatedStock, "Stock updated from sync");

            log.info("Stock updated from sync: {} by device {}", stockId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to update stock from sync", e);
            auditLogService.logFailure(tenantId, userId, "UPDATE_STOCK", "STOCK",
                    String.valueOf(id), e.getMessage(), "Failed to update stock from sync");
            return false;
        }
    }

    /**
     * Delete stock from sync request
     */
    @Override
    public boolean deleteFromSync(Long id, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Stock existingStock = stockRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Stock not found"));

            String oldValueJson = objectMapper.writeValueAsString(existingStock);
            String stockId = String.valueOf(id);

            // Delete the stock
            stockRepository.deleteById(id);

            // Record in SyncChange
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(stockId)
                    .entityType("STOCK")
                    .action("delete")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "DELETE_STOCK", "STOCK",
                    stockId, null, "Stock deleted from sync");

            log.info("Stock deleted from sync: {} by device {}", stockId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to delete stock from sync", e);
            auditLogService.logFailure(tenantId, userId, "DELETE_STOCK", "STOCK",
                    String.valueOf(id), e.getMessage(), "Failed to delete stock from sync");
            return false;
        }
    }
}