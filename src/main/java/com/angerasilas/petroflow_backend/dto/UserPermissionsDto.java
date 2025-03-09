package com.angerasilas.petroflow_backend.dto;


import java.util.List;

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
    private List<String> permissions;

    // Constructor matching the query parameters
    public UserPermissionsDto(Long userId, Long organizationId, Long facilityId, String roleName, String username, String firstname, String lastname, String facilityName, String organizationName, String department) {
        this.userId = userId;
        this.organizationId = organizationId;
        this.facilityId = facilityId;
        this.roleName = roleName;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.facilityName = facilityName;
        this.organizationName = organizationName;
        this.department = department;
    }

}