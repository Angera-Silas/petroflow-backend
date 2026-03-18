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
public class SyncResponse {
    private boolean success;
    private LocalDateTime syncedAt;
    private int appliedChanges;
    private List<Conflict> conflicts;
    private List<Delta> serverDeltas;
    private List<NewEntity> newEntities;
    private String message;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Conflict {
        private String entityId;
        private String entityType;
        private String action;
        private Object clientVersion;
        private Object serverVersion;
        private String resolution;
        private LocalDateTime conflictAt;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Delta {
        private String entityId;
        private String entityType;
        private String action;
        private Object data;
        private LocalDateTime timestamp;
        private String changedBy;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class NewEntity {
        private String entityId;
        private String entityType;
        private Object data;
    }
}
