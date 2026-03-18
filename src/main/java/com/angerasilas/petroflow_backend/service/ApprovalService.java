package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.entity.ApprovalRequest;
import com.angerasilas.petroflow_backend.repository.ApprovalRequestRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service for approval workflows.
 * Handles sales edits, refunds, incident closures, and staff requests.
 * Ensures accountability through multi-level approvals.
 */
@Service
@Slf4j
public class ApprovalService {

    private final ApprovalRequestRepository approvalRepository;
    private final AuditLogService auditLogService;
    private final ObjectMapper objectMapper;

    public ApprovalService(ApprovalRequestRepository approvalRepository,
                          AuditLogService auditLogService,
                          ObjectMapper objectMapper) {
        this.approvalRepository = approvalRepository;
        this.auditLogService = auditLogService;
        this.objectMapper = objectMapper;
    }

    /**
     * Create an approval request
     */
    @Transactional
    public ApprovalRequest createApprovalRequest(String requestType,
                                                 String entityId,
                                                 String requesterId,
                                                 String approverId,
                                                 String requestReason,
                                                 Object oldValue,
                                                 Object proposedValue,
                                                 String priority) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            ApprovalRequest approval = ApprovalRequest.builder()
                .id("approval_" + UUID.randomUUID().toString())
                .tenantId(tenantId)
                .requestType(requestType)
                .entityId(entityId)
                .requesterId(requesterId)
                .approverId(approverId)
                .status("PENDING")
                .priority(priority != null ? priority : "NORMAL")
                .requestReason(requestReason)
                .oldValue(objectMapper.writeValueAsString(oldValue))
                .proposedValue(objectMapper.writeValueAsString(proposedValue))
                .build();

            approvalRepository.save(approval);

            auditLogService.logOperation(tenantId, requesterId,
                "APPROVAL_REQUESTED", requestType, entityId,
                proposedValue, "Approval requested: " + requestReason);

            log.info("Approval request created: {} for {} by {}",
                approval.getId(), requestType, requesterId);

            return approval;

        } catch (Exception e) {
            log.error("Failed to create approval request", e);
            throw new RuntimeException("Failed to create approval request", e);
        }
    }

    /**
     * Approve a request
     */
    @Transactional
    @PreAuthorize("hasAuthority('APPROVE_CHANGES')")
    public ApprovalRequest approveRequest(String approvalId, String approverId,
                                         String comment, String currentUserId) {
        String tenantId = TenantContext.getCurrentTenant();

        ApprovalRequest approval = approvalRepository.findById(approvalId)
            .orElseThrow(() -> new RuntimeException("Approval request not found: " + approvalId));

        if (!approval.getStatus().equals("PENDING")) {
            throw new RuntimeException("Approval already " + approval.getStatus());
        }

        approval.setStatus("APPROVED");
        approval.setApproverId(approverId);
        approval.setApprovedAt(LocalDateTime.now());
        approval.setApprovalComment(comment);

        approvalRepository.save(approval);

        auditLogService.logApproval(tenantId, currentUserId,
            approval.getRequestType(), approval.getEntityId(), true, comment);

        log.info("Approval granted: {} (entity: {})", approvalId, approval.getEntityId());

        // TODO: Apply the approved changes to the actual entity
        applyApprovedChanges(approval);

        return approval;
    }

    /**
     * Reject a request
     */
    @Transactional
    @PreAuthorize("hasAuthority('APPROVE_CHANGES')")
    public ApprovalRequest rejectRequest(String approvalId, String approverId,
                                        String rejectionReason, String currentUserId) {
        String tenantId = TenantContext.getCurrentTenant();

        ApprovalRequest approval = approvalRepository.findById(approvalId)
            .orElseThrow(() -> new RuntimeException("Approval request not found: " + approvalId));

        if (!approval.getStatus().equals("PENDING")) {
            throw new RuntimeException("Approval already " + approval.getStatus());
        }

        approval.setStatus("REJECTED");
        approval.setApproverId(approverId);
        approval.setRejectedAt(LocalDateTime.now());
        approval.setRejectionReason(rejectionReason);

        approvalRepository.save(approval);

        auditLogService.logApproval(tenantId, currentUserId,
            approval.getRequestType(), approval.getEntityId(), false, rejectionReason);

        log.info("Approval rejected: {} (reason: {})", approvalId, rejectionReason);

        return approval;
    }

    /**
     * Get pending approvals for a user
     */
    public Page<ApprovalRequest> getPendingApprovalsFor(String approverId, Pageable pageable) {
        String tenantId = TenantContext.getCurrentTenant();
        return approvalRepository.findByTenantIdAndApproverIdAndStatusOrderByCreatedAtDesc(
            tenantId, approverId, "PENDING", pageable);
    }

    /**
     * Get all pending approvals for tenant
     */
    public Page<ApprovalRequest> getAllPendingApprovals(Pageable pageable) {
        String tenantId = TenantContext.getCurrentTenant();
        return approvalRepository.findByTenantIdAndStatusOrderByCreatedAtDesc(
            tenantId, "PENDING", pageable);
    }

    /**
     * Get high priority pending approvals
     */
    public List<ApprovalRequest> getHighPriorityPending() {
        String tenantId = TenantContext.getCurrentTenant();
        return approvalRepository.findByTenantIdAndStatusAndPriorityOrderByCreatedAtDesc(
            tenantId, "PENDING", "HIGH");
    }

    /**
     * Get approval history for an entity
     */
    public List<ApprovalRequest> getEntityApprovalHistory(String entityId) {
        String tenantId = TenantContext.getCurrentTenant();
        // Note: Repository doesn't have this method, would need to add it
        // For now return empty list
        return List.of();
    }

    /**
     * Check if entity has pending approval
     */
    public boolean hasPendingApproval(String entityId) {
        String tenantId = TenantContext.getCurrentTenant();
        Optional<ApprovalRequest> pending =
            approvalRepository.findByTenantIdAndEntityIdAndStatus(
                tenantId, entityId, "PENDING");
        return pending.isPresent();
    }

    /**
     * Get count of pending approvals
     */
    public long getPendingApprovalCount() {
        String tenantId = TenantContext.getCurrentTenant();
        return approvalRepository.countByTenantIdAndStatus(tenantId, "PENDING");
    }

    /**
     * Cleanup expired approvals (auto-reject)
     */
    @Transactional
    public int cleanupExpiredApprovals() {
        String tenantId = TenantContext.getCurrentTenant();
        List<ApprovalRequest> expired =
            approvalRepository.findExpiredApprovals(tenantId, LocalDateTime.now());

        for (ApprovalRequest approval : expired) {
            approval.setStatus("REJECTED");
            approval.setRejectionReason("Auto-rejected: Expired after 7 days");
            approval.setRejectedAt(LocalDateTime.now());
            approvalRepository.save(approval);
        }

        log.info("Cleaned up {} expired approvals", expired.size());
        return expired.size();
    }

    /**
     * Apply approved changes to the entity
     * This is entity-specific and would be implemented for each entity type
     */
    private void applyApprovedChanges(ApprovalRequest approval) {
        try {
            switch (approval.getRequestType()) {
                case "SALES_EDIT":
                    // TODO: Update sales with proposed changes
                    log.info("Applying approved sales edit: {}", approval.getEntityId());
                    break;

                case "REFUND":
                    // TODO: Process refund
                    log.info("Processing approved refund: {}", approval.getEntityId());
                    break;

                case "INCIDENT_CLOSURE":
                    // TODO: Close incident
                    log.info("Closing approved incident: {}", approval.getEntityId());
                    break;

                case "REQUEST_APPROVAL":
                    // TODO: Approve request
                    log.info("Approving request: {}", approval.getEntityId());
                    break;

                default:
                    log.warn("Unknown approval type: {}", approval.getRequestType());
            }
        } catch (Exception e) {
            log.error("Failed to apply approved changes", e);
            throw new RuntimeException("Failed to apply approved changes", e);
        }
    }
}
