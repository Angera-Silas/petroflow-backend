package com.angerasilas.petroflow_backend.dto;

import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsDto {
    private Long roleId;
    private String roleName;
    private PermissionEntity permission;
}