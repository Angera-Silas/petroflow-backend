package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.DeviceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceSessionRepository extends JpaRepository<DeviceSession, String> {

    List<DeviceSession> findByUserId(String userId);

    List<DeviceSession> findByUserIdAndIsActiveTrue(String userId);

    Optional<DeviceSession> findByDeviceId(String deviceId);

    @Modifying
    @Query("UPDATE DeviceSession ds SET ds.isActive = false WHERE ds.userId = ?1 AND ds.id != ?2")
    void invalidateOtherSessions(String userId, String currentSessionId);

    @Modifying
    @Query("UPDATE DeviceSession ds SET ds.isActive = false WHERE ds.userId = ?1 AND ds.deviceId != ?2")
    void invalidateOthersButDevice(String userId, String currentDeviceId);

    @Modifying
    @Query("DELETE FROM DeviceSession ds WHERE ds.isActive = false AND ds.lastActive < ?1")
    void cleanupInactiveSessions(LocalDateTime before);

    @Query("SELECT COUNT(ds) FROM DeviceSession ds WHERE ds.userId = ?1 AND ds.isActive = true")
    int countActiveSessionsFor(String userId);
}
