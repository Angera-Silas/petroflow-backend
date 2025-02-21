package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailsDto {
    private String name;
    private String physicalAddress;
    private String town;
    private String street;
    private String postalCode;
    private String type;
    private String email;
    private String phone;
    private Long facilityCount;
    private Long employeeCount;
}
