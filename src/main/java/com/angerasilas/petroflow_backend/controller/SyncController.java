package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.SyncRequest;
import com.angerasilas.petroflow_backend.dto.SyncResponse;
import com.angerasilas.petroflow_backend.service.impl.SyncService;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

/**
 * REST API endpoint for handling offline sync from mobile/desktop clients.
 *
 * Endpoint: POST /api/sync
 *
 * This is the core endpoint that allows mobile and desktop clients to:
 * 1. Send their local changes to the server
 * 2. Receive changes from other devices/users
 * 3. Handle conflicts (last-write-wins)
 * 4. Stay in sync even with poor connectivity
 *
 * The endpoint ensures data consistency across all platforms using
 * optimistic locking and timestamp-based conflict resolution.
 */
@RestController
@RequestMapping("/api/sync")
@Slf4j
public class SyncController {

    private final SyncService syncService;

    public SyncController(SyncService syncService) {
        this.syncService = syncService;
    }

    /**
     * Main sync endpoint - processes offline changes
     *
     * @param request - SyncRequest containing device changes since last sync
     * @param authentication - Spring Security principal (user info)
     * @param httpRequest - HTTP servlet request (for IP, user agent)
     * @return SyncResponse with applied changes, conflicts, and server deltas
     *
     * Example Request:
     * POST /api/sync
     * Authorization: Bearer <JWT>
     * X-Tenant-Id: org_shell_kenya
     * Content-Type: application/json
     *
     * {
     *   "deviceId": "device_abc123",
     *   "transactionType": "delta",
     *   "lastSyncTimestamp": "2026-03-15T10:00:00Z",
     *   "changes": [
     *     {
     *       "entityId": "sales_123",
     *       "entityType": "sales",
     *       "action": "create",
     *       "data": {
     *         "amount": 5000,
     *         "product": "Premium Diesel",
     *         "station": "Mlolongo",
     *         "timestamp": "2026-03-15T10:15:00Z"
     *       },
     *       "clientTimestamp": "2026-03-15T10:15:00Z"
     *     }
     *   ]
     * }
     *
     * Example Response:
     * {
     *   "success": true,
     *   "syncedAt": "2026-03-15T10:30:00Z",
     *   "appliedChanges": 1,
     *   "conflicts": [],
     *   "serverDeltas": [
     *     {
     *       "entityId": "sales_999",
     *       "entityType": "sales",
     *       "action": "update",
     *       "data": {
     *         "status": "approved",
     *         "approvedBy": "manager_123"
     *       },
     *       "timestamp": "2026-03-15T10:25:00Z",
     *       "changedBy": "manager_123"
     *     }
     *   ],
     *   "newEntities": [],
     *   "message": "Synced 1 changes, 0 conflicts resolved"
     * }
     */
    @PostMapping
    public ResponseEntity<SyncResponse> sync(
        @RequestBody SyncRequest request,
        Authentication authentication,
        HttpServletRequest httpRequest) {

        try {
            String userId = authentication.getName();
            String tenantId = TenantContext.getCurrentTenant();
            String deviceId = request.getDeviceId();

            log.info("Sync request - User: {}, Tenant: {}, Device: {}, Changes: {}",
                userId, tenantId, deviceId, request.getChanges().size());

            // Process the sync
            SyncResponse response = syncService.processSync(request, deviceId, userId);

            return ResponseEntity.ok(response);

        } catch (IllegalStateException e) {
            log.error("Tenant context not set", e);
            return ResponseEntity.status(400)
                .body(SyncResponse.builder()
                    .success(false)
                    .message("Tenant context not found")
                    .build());

        } catch (Exception e) {
            log.error("Sync failed", e);
            return ResponseEntity.status(500)
                .body(SyncResponse.builder()
                    .success(false)
                    .message("Sync failed: " + e.getMessage())
                    .build());
        }
    }

    /**
     * Health check endpoint for sync
     * Clients can call this to verify sync endpoint is available
     */
    @GetMapping("/health")
    public ResponseEntity<?> syncHealth() {
        return ResponseEntity.ok(new Object() {
            public String status = "ok";
            public long timestamp = System.currentTimeMillis();
            public String version = "1.0";
        });
    }
}
