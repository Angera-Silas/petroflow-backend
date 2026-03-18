package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.ApprovalRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, String> {

    /**
     * Find pending approvals for a tenant
     */
    Page<ApprovalRequest> findByTenantIdAndStatusOrderByCreatedAtDesc(
        String tenantId, String status, Pageable pageable);

    /**
     * Find pending approvals for a specific approver
     */
    Page<ApprovalRequest> findByTenantIdAndApproverIdAndStatusOrderByCreatedAtDesc(
        String tenantId, String approverId, String status, Pageable pageable);

    /**
     * Find approval by entity ID
     */
    Optional<ApprovalRequest> findByTenantIdAndEntityIdAndStatus(
        String tenantId, String entityId, String status);

    /**
     * Find approvals by request type
     */
    Page<ApprovalRequest> findByTenantIdAndRequestTypeAndStatusOrderByCreatedAtDesc(
        String tenantId, String requestType, String status, Pageable pageable);

    /**
     * Find approvals by requester
     */
    Page<ApprovalRequest> findByTenantIdAndRequesterIdOrderByCreatedAtDesc(
        String tenantId, String requesterId, Pageable pageable);

    /**
     * Count pending approvals
     */
    long countByTenantIdAndStatus(String tenantId, String status);

    /**
     * Find high priority pending approvals
     */
    List<ApprovalRequest> findByTenantIdAndStatusAndPriorityOrderByCreatedAtDesc(
        String tenantId, String status, String priority);

    /**
     * Find expired approvals (not acted on within 7 days)
     */
    @Query("SELECT ar FROM ApprovalRequest ar " +
           "WHERE ar.tenantId = ?1 " +
           "AND ar.status = 'PENDING' " +
           "AND ar.expiresAt < ?2")
    List<ApprovalRequest> findExpiredApprovals(String tenantId, LocalDateTime now);

    /**
     * Find approvals in date range
     */
    List<ApprovalRequest> findByTenantIdAndCreatedAtBetweenOrderByCreatedAtDesc(
        String tenantId, LocalDateTime from, LocalDateTime to);
}
