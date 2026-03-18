package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Approval request for sensitive operations.
 * Sales edits, refunds, and incident closures require manager approval.
 * Tracks approval workflow with full audit trail.
 */
@Entity
@Table(name = "approval_requests", indexes = {
    @Index(name = "idx_tenant_status", columnList = "tenant_id, status"),
    @Index(name = "idx_entity_id", columnList = "entity_id"),
    @Index(name = "idx_requester", columnList = "requester_id"),
    @Index(name = "idx_approver", columnList = "approver_id")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApprovalRequest {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "request_type", nullable = false)
    private String requestType;  // SALES_EDIT, REFUND, INCIDENT_CLOSURE, REQUEST_APPROVAL

    @Column(name = "entity_id", nullable = false)
    private String entityId;  // ID of the entity needing approval (sales/incident)

    @Column(name = "requester_id", nullable = false)
    private String requesterId;  // User who made the request

    @Column(name = "approver_id")
    private String approverId;  // User who will approve/reject

    @Column(name = "status", nullable = false)
    private String status;  // PENDING, APPROVED, REJECTED

    @Column(name = "priority")
    private String priority;  // HIGH, NORMAL, LOW

    @Column(name = "request_reason")
    private String requestReason;  // Why is this change needed?

    @Column(name = "approval_comment", columnDefinition = "TEXT")
    private String approvalComment;  // Approver's comments

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;  // Why was it rejected?

    @Column(name = "old_value", columnDefinition = "TEXT")
    private String oldValue;  // JSON of current state

    @Column(name = "proposed_value", columnDefinition = "TEXT")
    private String proposedValue;  // JSON of proposed changes

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;  // Auto-expire if not approved

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusDays(7);  // Expire in 7 days
    }
}
