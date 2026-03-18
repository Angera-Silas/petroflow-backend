package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Immutable audit log for compliance & forensics.
 * Tracks all sensitive operations with full context.
 * Used to meet Kenya/Uganda/Tanzania data protection requirements.
 */
@Entity
@Table(name = "audit_logs", indexes = {
    @Index(name = "idx_tenant_timestamp", columnList = "tenant_id, timestamp"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_action", columnList = "action"),
    @Index(name = "idx_entity_type", columnList = "entity_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;  // Org ID

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "device_id")
    private String deviceId;  // Which device user was on

    @Column(name = "action", nullable = false)
    private String action;  // LOGIN, CREATE_SALE, EDIT_SALE, APPROVE, DELETE, etc.

    @Column(name = "entity_type")
    private String entityType;  // sales, inventory, employee, request, etc.

    @Column(name = "entity_id")
    private String entityId;

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;  // JSON of previous state (for updates)

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;  // JSON of new state

    @Column(name = "change_summary")
    private String changeSummary;  // Human-readable summary

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "status")
    private String status;  // SUCCESS, FAILURE, DENIED

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }
}
