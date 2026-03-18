package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.SyncChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SyncChangeRepository extends JpaRepository<SyncChange, String> {

    /**
     * Find all changes for a tenant since a specific timestamp
     */
    List<SyncChange> findByTenantIdAndTimestampAfter(String tenantId, LocalDateTime since);

    /**
     * Find changes for a specific entity
     */
    List<SyncChange> findByEntityIdOrderByTimestampDesc(String entityId);

    /**
     * Find all changes for a tenant since timestamp, ordered by timestamp
     */
    @Query("SELECT sc FROM SyncChange sc " +
           "WHERE sc.tenantId = ?1 AND sc.timestamp > ?2 " +
           "ORDER BY sc.timestamp ASC")
    List<SyncChange> findChangesSince(String tenantId, LocalDateTime since);

    /**
     * Find changes by entity type for a tenant
     */
    List<SyncChange> findByTenantIdAndEntityTypeAndTimestampAfter(
        String tenantId,
        String entityType,
        LocalDateTime since
    );

    /**
     * Count changes for a tenant
     */
    long countByTenantId(String tenantId);
}
