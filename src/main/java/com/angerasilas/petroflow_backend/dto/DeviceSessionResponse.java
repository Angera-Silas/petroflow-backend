package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceSessionResponse {
    private boolean conflictDetected;
    private String suggestion;
    private List<ExistingDevice> existingDevices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExistingDevice {
        private String deviceId;
        private String deviceName;
        private String os;
        private LocalDateTime lastActive;
    }
}
