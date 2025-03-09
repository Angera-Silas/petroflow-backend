package com.angerasilas.petroflow_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionsDto {
    private Long roleId;
    private String roleName;
    private List<String> permissions;

    public RolePermissionsDto(Long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
}