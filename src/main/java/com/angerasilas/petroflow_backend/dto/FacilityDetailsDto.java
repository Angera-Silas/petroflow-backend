package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDetailsDto {
    private String name;
    private String county;
    private String town;
    private String street;
    private String physicalAddress;
    private String postalCode;
    private String phone;
    private String email;
    private String servicesOffered;
    private Long employeeCount;
}
