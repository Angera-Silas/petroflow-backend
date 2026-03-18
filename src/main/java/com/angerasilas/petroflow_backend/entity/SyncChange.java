package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Tracks all changes made in the system for sync/audit purposes.
 * Used to provide delta sync to clients.
 */
@Entity
@Table(name = "sync_changes", indexes = {
    @Index(name = "idx_tenant_timestamp", columnList = "tenant_id, timestamp"),
    @Index(name = "idx_entity_id", columnList = "entity_id"),
    @Index(name = "idx_entity_type", columnList = "entity_type")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncChange {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "entity_id", nullable = false)
    private String entityId;

    @Column(name = "entity_type", nullable = false)
    private String entityType;  // 'sales', 'inventory', 'request', etc.

    @Column(name = "action", nullable = false)
    private String action;  // 'create', 'update', 'delete'

    @Column(name = "changed_by")
    private String changedBy;  // User ID

    @Column(name = "changed_from_device")
    private String changedFromDevice;  // Device ID

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;  // JSON of previous state

    @Column(name = "new_value", columnDefinition = "TEXT")
    private String newValue;  // JSON of new state

    @Column(name = "timestamp", nullable = false)
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
