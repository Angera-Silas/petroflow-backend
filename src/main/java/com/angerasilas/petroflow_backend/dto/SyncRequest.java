package com.angerasilas.petroflow_backend.dto;

import com.fasterxml.jackson.databind.JsonNode;
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
public class SyncRequest {
    private String deviceId;
    private String transactionType;
    private LocalDateTime lastSyncTimestamp;
    private List<Change> changes;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Change {
        private String entityId;
        private String entityType;
        private String action;
        private String tenantId;
        private JsonNode data;
        private LocalDateTime clientTimestamp;
        private int clientVersion;
    }
}
