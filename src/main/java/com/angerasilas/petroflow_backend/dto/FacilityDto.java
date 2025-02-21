package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacilityDto {
    private Long id;
    private String facilityName;
    private String facilityCounty;
    private String facilityTown;
    private String facilityStreet;
    private String physicalAddress;
    private String facilityPostalCode;
    private String facilityPhone;
    private String facilityEmail;
    private String servicesOffered;


}
