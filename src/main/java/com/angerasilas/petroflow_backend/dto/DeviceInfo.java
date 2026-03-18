package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceInfo {
    private String deviceId;
    private String deviceName;
    private String os;
    private String hostname;
    private String model;
    private String ipAddress;
    private String userAgent;
}
