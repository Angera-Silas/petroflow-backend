package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.dto.SyncRequest;
import com.angerasilas.petroflow_backend.dto.SyncResponse;
import com.angerasilas.petroflow_backend.entity.SyncChange;
import com.angerasilas.petroflow_backend.repository.SyncChangeRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.angerasilas.petroflow_backend.service.SalesService;
import com.angerasilas.petroflow_backend.service.RequestService;
import com.angerasilas.petroflow_backend.service.StockService;
import com.angerasilas.petroflow_backend.service.AuditLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Core sync service handling offline changes from mobile/desktop clients
 * Implements:
 * - Delta sync (only changed data)
 * - Conflict resolution (last-write-wins)
 * - Batch processing
 * - Change tracking
 */
@Service
@Slf4j
public class SyncService {

    private final SyncChangeRepository changeRepository;
    private final SalesService salesService;
    private final StockService stockService;
    private final RequestService requestService;
    private final AuditLogService auditLogService;

    public SyncService(
        SyncChangeRepository changeRepository,
        SalesService salesService,
        StockService stockService,
        RequestService requestService,
        AuditLogService auditLogService
    ) {
        this.changeRepository = changeRepository;
        this.salesService = salesService;
        this.stockService = stockService;
        this.requestService = requestService;
        this.auditLogService = auditLogService;
    }

    /**
     * Main sync endpoint - processes changes from mobile/desktop
     */
    @Transactional
    public SyncResponse processSync(SyncRequest request, String deviceId, String userId) {
        String tenantId = TenantContext.getCurrentTenant();
        log.info("Processing sync for tenant: {}, device: {}, changes: {}",
            tenantId, deviceId, request.getChanges().size());

        List<SyncResponse.Conflict> conflicts = new ArrayList<>();
        List<SyncResponse.Delta> serverDeltas = new ArrayList<>();
        int appliedChanges = 0;

        // 1. Process each incoming change
        for (SyncRequest.Change change : request.getChanges()) {
            try {
                boolean applied = applyChange(change, userId, deviceId);
                if (applied) {
                    appliedChanges++;
                    log.debug("Applied {} on entity: {}", change.getAction(), change.getEntityId());
                }
            } catch (Exception e) {
                log.error("Error applying change: {}", change.getEntityId(), e);
                // Continue processing other changes
            }
        }

        // 2. Get server deltas (changes from other devices/users)
        if (request.getLastSyncTimestamp() != null) {
            serverDeltas = fetchServerDeltas(tenantId, request.getLastSyncTimestamp(),
                deviceId, userId);
        }

        // 3. Audit log the sync
        auditLogService.logSync(tenantId, userId, deviceId, appliedChanges, 0);

        // 4. Build response
        SyncResponse response = SyncResponse.builder()
            .success(true)
            .syncedAt(LocalDateTime.now())
            .appliedChanges(appliedChanges)
            .conflicts(conflicts)
            .serverDeltas(serverDeltas)
            .message(String.format("Synced %d changes", appliedChanges))
            .build();

        log.info("Sync completed for device: {} - applied: {}",
            deviceId, appliedChanges);

        return response;
    }

    /**
     * Apply a single change to the database
     * Implements last-write-wins conflict resolution
     */
    private boolean applyChange(SyncRequest.Change change, String userId, String deviceId) {
        log.debug("Applying {} {} from client", change.getAction(), change.getEntityType());

        switch (change.getEntityType().toLowerCase()) {
            case "sales":
                return applySalesChange(change, userId, deviceId);

            case "stock":
            case "inventory":
                return applyStockChange(change, userId, deviceId);

            case "request":
                return applyRequestChange(change, userId, deviceId);

            default:
                log.warn("Unknown entity type: {}", change.getEntityType());
                return false;
        }
    }

    private boolean applySalesChange(SyncRequest.Change change, String userId, String deviceId) {
        switch (change.getAction().toLowerCase()) {
            case "create":
                return salesService.createFromSync(change.getData(), userId, deviceId);

            case "update":
                try {
                    Long salesId = Long.parseLong(change.getEntityId());
                    return salesService.updateFromSync(salesId, change.getData(), userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid sales ID: {}", change.getEntityId());
                    return false;
                }

            case "delete":
                try {
                    Long salesId = Long.parseLong(change.getEntityId());
                    return salesService.deleteFromSync(salesId, userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid sales ID: {}", change.getEntityId());
                    return false;
                }

            default:
                return false;
        }
    }

    private boolean applyStockChange(SyncRequest.Change change, String userId, String deviceId) {
        switch (change.getAction().toLowerCase()) {
            case "create":
                return stockService.createFromSync(change.getData(), userId, deviceId);

            case "update":
                try {
                    Long stockId = Long.parseLong(change.getEntityId());
                    return stockService.updateFromSync(stockId, change.getData(), userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid stock ID: {}", change.getEntityId());
                    return false;
                }

            case "delete":
                try {
                    Long stockId = Long.parseLong(change.getEntityId());
                    return stockService.deleteFromSync(stockId, userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid stock ID: {}", change.getEntityId());
                    return false;
                }

            default:
                return false;
        }
    }

    private boolean applyRequestChange(SyncRequest.Change change, String userId, String deviceId) {
        switch (change.getAction().toLowerCase()) {
            case "create":
                return requestService.createFromSync(change.getData(), userId, deviceId);

            case "update":
                try {
                    Long requestId = Long.parseLong(change.getEntityId());
                    return requestService.updateFromSync(requestId, change.getData(), userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid request ID: {}", change.getEntityId());
                    return false;
                }

            case "delete":
                try {
                    Long requestId = Long.parseLong(change.getEntityId());
                    return requestService.deleteFromSync(requestId, userId, deviceId);
                } catch (NumberFormatException e) {
                    log.error("Invalid request ID: {}", change.getEntityId());
                    return false;
                }

            default:
                return false;
        }
    }

    /**
     * Fetch changes from server since last sync
     * These are changes from other devices/users that this device needs
     */
    private List<SyncResponse.Delta> fetchServerDeltas(String tenantId,
                                                       LocalDateTime since,
                                                       String deviceId,
                                                       String userId) {
        log.debug("Fetching server deltas since: {} for tenant: {}", since, tenantId);

        List<SyncResponse.Delta> deltas = new ArrayList<>();

        // Get recent changes (from other attendants/managers)
        List<SyncChange> recentChanges =
            changeRepository.findByTenantIdAndTimestampAfter(tenantId, since);

        for (SyncChange change : recentChanges) {
            // Skip changes from the same device
            if (change.getChangedFromDevice() != null &&
                !change.getChangedFromDevice().equals(deviceId)) {

                SyncResponse.Delta delta = SyncResponse.Delta.builder()
                    .entityId(change.getEntityId())
                    .entityType(change.getEntityType())
                    .action(change.getAction())
                    .data(change.getNewValue())
                    .timestamp(change.getTimestamp())
                    .changedBy(change.getChangedBy())
                    .build();

                deltas.add(delta);
            }
        }

        log.debug("Found {} server deltas to send", deltas.size());
        return deltas;
    }
}
