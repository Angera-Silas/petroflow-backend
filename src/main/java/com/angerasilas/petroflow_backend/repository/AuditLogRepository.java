package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, String> {

    /**
     * Find all audit logs for a tenant
     */
    Page<AuditLog> findByTenantIdOrderByTimestampDesc(String tenantId, Pageable pageable);

    /**
     * Find audit logs for a specific user
     */
    Page<AuditLog> findByTenantIdAndUserIdOrderByTimestampDesc(
        String tenantId, String userId, Pageable pageable);

    /**
     * Find audit logs for a specific action
     */
    List<AuditLog> findByTenantIdAndActionOrderByTimestampDesc(
        String tenantId, String action);

    /**
     * Find audit logs for a specific entity
     */
    List<AuditLog> findByTenantIdAndEntityIdOrderByTimestampDesc(
        String tenantId, String entityId);

    /**
     * Find audit logs for entity type
     */
    Page<AuditLog> findByTenantIdAndEntityTypeOrderByTimestampDesc(
        String tenantId, String entityType, Pageable pageable);

    /**
     * Find audit logs in date range
     */
    @Query("SELECT al FROM AuditLog al " +
           "WHERE al.tenantId = ?1 " +
           "AND al.timestamp BETWEEN ?2 AND ?3 " +
           "ORDER BY al.timestamp DESC")
    List<AuditLog> findByTenantAndDateRange(
        String tenantId, LocalDateTime from, LocalDateTime to);

    /**
     * Find failed operations
     */
    List<AuditLog> findByTenantIdAndStatusOrderByTimestampDesc(
        String tenantId, String status);

    /**
     * Count operations by action
     */
    long countByTenantIdAndAction(String tenantId, String action);
}
