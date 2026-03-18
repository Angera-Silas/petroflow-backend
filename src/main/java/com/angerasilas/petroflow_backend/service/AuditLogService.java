package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.entity.AuditLog;
import com.angerasilas.petroflow_backend.repository.AuditLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Service for logging audit trail events.
 * Immutable & comprehensive - tracks all sensitive operations.
 * Used for compliance with East African data protection laws.
 */
@Service
@Slf4j
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final ObjectMapper objectMapper;
    private final HttpServletRequest httpRequest;

    public AuditLogService(AuditLogRepository auditLogRepository,
                          ObjectMapper objectMapper,
                          HttpServletRequest httpRequest) {
        this.auditLogRepository = auditLogRepository;
        this.objectMapper = objectMapper;
        this.httpRequest = httpRequest;
    }

    /**
     * Log a successful operation
     */
    @Transactional
    public void logOperation(String tenantId, String userId, String action,
                            String entityType, String entityId,
                            Object newValue, String summary) {
        logOperationInternal(tenantId, userId, action, entityType, entityId,
            null, newValue, summary, "SUCCESS", null);
    }

    /**
     * Log an update operation (track old and new values)
     */
    @Transactional
    public void logUpdate(String tenantId, String userId, String action,
                         String entityType, String entityId,
                         Object oldValue, Object newValue, String summary) {
        logOperationInternal(tenantId, userId, action, entityType, entityId,
            oldValue, newValue, summary, "SUCCESS", null);
    }

    /**
     * Log a failed operation
     */
    @Transactional
    public void logFailure(String tenantId, String userId, String action,
                          String entityType, String entityId,
                          String reason, String summary) {
        logOperationInternal(tenantId, userId, action, entityType, entityId,
            null, null, summary, "FAILURE", reason);
    }

    /**
     * Log sync operation
     */
    @Transactional
    public void logSync(String tenantId, String userId, String deviceId,
                       int appliedChanges, int conflictsResolved) {
        String summary = String.format("Sync: %d changes applied, %d conflicts resolved",
            appliedChanges, conflictsResolved);

        AuditLog auditEntry = AuditLog.builder()
            .id("audit_" + UUID.randomUUID().toString())
            .tenantId(tenantId)
            .userId(userId)
            .deviceId(deviceId)
            .action("SYNC")
            .entityType("sync")
            .changeSummary(summary)
            .ipAddress(getClientIp())
            .userAgent(httpRequest.getHeader("User-Agent"))
            .status("SUCCESS")
            .build();

        auditLogRepository.save(auditEntry);
        log.info("Sync logged - tenant: {}, user: {}, device: {}", tenantId, userId, deviceId);
    }

    /**
     * Log approval operation
     */
    @Transactional
    public void logApproval(String tenantId, String userId, String entityType,
                           String entityId, boolean approved, String comments) {
        String action = approved ? "APPROVAL_GRANTED" : "APPROVAL_DENIED";
        String summary = String.format("%s on %s: %s",
            action, entityType, comments);

        logOperationInternal(tenantId, userId, action, entityType, entityId,
            null, null, summary, "SUCCESS", null);
    }

    /**
     * Internal method - main logging implementation
     */
    private void logOperationInternal(String tenantId, String userId, String action,
                                      String entityType, String entityId,
                                      Object oldValue, Object newValue,
                                      String summary, String status, String failureReason) {
        try {
            String oldValueJson = oldValue != null ? objectMapper.writeValueAsString(oldValue) : null;
            String newValueJson = newValue != null ? objectMapper.writeValueAsString(newValue) : null;

            AuditLog auditEntry = AuditLog.builder()
                .id("audit_" + UUID.randomUUID().toString())
                .tenantId(tenantId)
                .userId(userId)
                .action(action)
                .entityType(entityType)
                .entityId(entityId)
                .oldValue(oldValueJson)
                .newValue(newValueJson)
                .changeSummary(summary)
                .ipAddress(getClientIp())
                .userAgent(httpRequest.getHeader("User-Agent"))
                .status(status)
                .failureReason(failureReason)
                .build();

            auditLogRepository.save(auditEntry);

            log.info("Audit logged - action: {}, entity: {}:{}, status: {}",
                action, entityType, entityId, status);

        } catch (Exception e) {
            log.error("Failed to log audit entry: {}", action, e);
            // Don't throw - audit failure shouldn't break operation
        }
    }

    /**
     * Get audit log for an entity
     */
    public List<AuditLog> getEntityAuditTrail(String tenantId, String entityId) {
        return auditLogRepository.findByTenantIdAndEntityIdOrderByTimestampDesc(
            tenantId, entityId);
    }

    /**
     * Get audit logs for a user
     */
    public Page<AuditLog> getUserAuditTrail(String tenantId, String userId, Pageable pageable) {
        return auditLogRepository.findByTenantIdAndUserIdOrderByTimestampDesc(
            tenantId, userId, pageable);
    }

    /**
     * Get audit logs for date range
     */
    public List<AuditLog> getAuditsByDateRange(String tenantId,
                                               LocalDateTime from,
                                               LocalDateTime to) {
        return auditLogRepository.findByTenantAndDateRange(tenantId, from, to);
    }

    /**
     * Get failed operations (security & debugging)
     */
    public List<AuditLog> getFailedOperations(String tenantId) {
        return auditLogRepository.findByTenantIdAndStatusOrderByTimestampDesc(
            tenantId, "FAILURE");
    }

    /**
     * Get action statistics
     */
    public long getActionCount(String tenantId, String action) {
        return auditLogRepository.countByTenantIdAndAction(tenantId, action);
    }

    /**
     * Extract client IP from request
     */
    private String getClientIp() {
        String ip = httpRequest.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty()) {
            return ip.split(",")[0].trim();
        }
        ip = httpRequest.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty()) {
            return ip;
        }
        return httpRequest.getRemoteAddr();
    }
}
