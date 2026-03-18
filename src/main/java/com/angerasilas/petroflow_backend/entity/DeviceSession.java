package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Device session tracking for single-login-per-device enforcement.
 * Ensures users can only be logged in on one device at a time.
 */
@Entity
@Table(name = "device_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceSession {

    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "device_id", nullable = false, length = 100, unique = true)
    private String deviceId;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_os")
    private String deviceOs;  // Android, iOS, Windows, macOS, Linux, Web

    @Column(name = "device_hostname")
    private String deviceHostname;

    @Column(name = "device_model")
    private String deviceModel;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_active")
    private LocalDateTime lastActive;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @PrePersist
    void createdAt() {
        this.createdAt = LocalDateTime.now();
        this.lastActive = LocalDateTime.now();
        this.isActive = true;
    }

    @PreUpdate
    void updateLastActive() {
        this.lastActive = LocalDateTime.now();
    }
}
