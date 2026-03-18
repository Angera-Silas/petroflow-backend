package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String jwt;
    private String refreshToken;
    private String deviceId;
    private String userId;
    private DeviceSessionResponse deviceConflict;
}
