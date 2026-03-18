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
public class CurrentSessionResponse {
    private ExistingSession currentDevice;
    private List<ExistingSession> otherSessions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ExistingSession {
        private String deviceId;
        private String deviceName;
        private String os;
        private LocalDateTime lastActive;
    }
}
