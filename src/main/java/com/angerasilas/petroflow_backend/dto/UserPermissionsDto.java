package com.angerasilas.petroflow_backend.dto;


import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionsDto {
    private Long userId;
    private Long organizationId;
    private Long facilityId;
    private String roleName;
    private String username;
    private String firstname;
    private String lastname;
    private String facilityName;
    private String organizationName;
    private String department;
    private PermissionEntity permissions;
}
