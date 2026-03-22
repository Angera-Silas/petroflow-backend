package com.angerasilas.petroflow_backend.common.graphql;

public record PlatformInfo(
        String applicationName,
        String tenantId,
        String requestId,
        String timestamp) {
}
