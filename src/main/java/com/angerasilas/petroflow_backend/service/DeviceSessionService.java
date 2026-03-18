package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.DeviceInfo;
import com.angerasilas.petroflow_backend.dto.DeviceSessionResponse;
import com.angerasilas.petroflow_backend.dto.CurrentSessionResponse;
import com.angerasilas.petroflow_backend.entity.DeviceSession;
import com.angerasilas.petroflow_backend.repository.DeviceSessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeviceSessionService {

    private final DeviceSessionRepository sessionRepository;

    public DeviceSessionService(DeviceSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    /**
     * Handle device login - check for conflicts, manage sessions
     */
    @Transactional
    public DeviceSessionResponse handleDeviceLogin(String userId, DeviceInfo deviceInfo) {
        log.info("Handling device login for user: {} on device: {}", userId, deviceInfo.getDeviceName());

        // Check for existing active sessions
        List<DeviceSession> existingSessions = sessionRepository.findByUserIdAndIsActiveTrue(userId);

        if (!existingSessions.isEmpty()) {
            // User already logged in elsewhere
            log.warn("Device conflict: user {} already has {} active sessions",
                userId, existingSessions.size());

            return DeviceSessionResponse.builder()
                .conflictDetected(true)
                .suggestion("LOGOUT_EXISTING_OR_USE_DEVICE")
                .existingDevices(existingSessions.stream()
                    .map(s -> DeviceSessionResponse.ExistingDevice.builder()
                        .deviceId(s.getDeviceId())
                        .deviceName(s.getDeviceName())
                        .os(s.getDeviceOs())
                        .lastActive(s.getLastActive())
                        .build())
                    .collect(Collectors.toList()))
                .build();
        }

        // Create new session
        String deviceId = deviceInfo.getDeviceId() != null ?
            deviceInfo.getDeviceId() : "device_" + UUID.randomUUID().toString();

        DeviceSession session = DeviceSession.builder()
            .id("session_" + UUID.randomUUID().toString())
            .userId(userId)
            .deviceId(deviceId)
            .deviceName(deviceInfo.getDeviceName())
            .deviceOs(deviceInfo.getOs())
            .deviceHostname(deviceInfo.getHostname())
            .deviceModel(deviceInfo.getModel())
            .ipAddress(deviceInfo.getIpAddress())
            .userAgent(deviceInfo.getUserAgent())
            .isActive(true)
            .build();

        sessionRepository.save(session);
        log.info("Device session created: {} for user: {}", session.getId(), userId);

        return DeviceSessionResponse.builder()
            .conflictDetected(false)
            .suggestion(null)
            .build();
    }

    /**
     * Force logout other devices
     */
    @Transactional
    public void forceLogoutOtherDevices(String userId, String currentDeviceId) {
        log.info("Force logging out other devices for user: {}", userId);
        sessionRepository.invalidateOthersButDevice(userId, currentDeviceId);
    }

    /**
     * Get all sessions for a user
     */
    public CurrentSessionResponse getUserSessions(String userId, String currentDeviceId) {
        List<DeviceSession> allSessions = sessionRepository.findByUserIdAndIsActiveTrue(userId);

        Optional<DeviceSession> currentSession = allSessions.stream()
            .filter(s -> s.getDeviceId().equals(currentDeviceId))
            .findFirst();

        List<CurrentSessionResponse.ExistingSession> otherSessions = allSessions.stream()
            .filter(s -> !s.getDeviceId().equals(currentDeviceId))
            .map(s -> CurrentSessionResponse.ExistingSession.builder()
                .deviceId(s.getDeviceId())
                .deviceName(s.getDeviceName())
                .os(s.getDeviceOs())
                .lastActive(s.getLastActive())
                .build())
            .collect(Collectors.toList());

        CurrentSessionResponse.ExistingSession current = currentSession
            .map(s -> CurrentSessionResponse.ExistingSession.builder()
                .deviceId(s.getDeviceId())
                .deviceName(s.getDeviceName())
                .os(s.getDeviceOs())
                .lastActive(s.getLastActive())
                .build())
            .orElse(null);

        return CurrentSessionResponse.builder()
            .currentDevice(current)
            .otherSessions(otherSessions)
            .build();
    }

    /**
     * Update last active time for a session
     */
    @Transactional
    public void updateLastActive(String deviceId) {
        Optional<DeviceSession> session = sessionRepository.findByDeviceId(deviceId);
        if (session.isPresent()) {
            session.get().setLastActive(LocalDateTime.now());
            sessionRepository.save(session.get());
        }
    }

    /**
     * Logout a device session
     */
    @Transactional
    public void logoutDevice(String deviceId) {
        Optional<DeviceSession> session = sessionRepository.findByDeviceId(deviceId);
        if (session.isPresent()) {
            session.get().setActive(false);
            sessionRepository.save(session.get());
            log.info("Device session logged out: {}", deviceId);
        }
    }

    /**
     * Cleanup inactive sessions (e.g., scheduled task)
     */
    @Transactional
    public void cleanupInactiveSessions() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(30);
        sessionRepository.cleanupInactiveSessions(cutoffTime);
        log.info("Cleaned up inactive sessions before: {}", cutoffTime);
    }
}
