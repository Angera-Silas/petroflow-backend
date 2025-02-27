package com.angerasilas.petroflow_backend.dto;

import com.angerasilas.petroflow_backend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPermissionsDto {
    private Long userId;
    private Long organizationId;
    private Long facilityId;
    private Role role;
    private String username;
    private String firstname;
    private String lastname;
    private String facilityName;
    private String organizationName;
    private String department;
    private String permissions;

}