package com.angerasilas.petroflow_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {

    private Long orgId;

    @NotEmpty
    private String orgName;

    private String orgCounty;
    private String orgTown;
    private String orgStreet;
    @NotEmpty
    private String orgPhone;

    @Email
    @NotEmpty
    private String orgEmail;
    private String physicalAddress;
    private String orgPostalCode;

    private Integer numberOfStations;

    private String registrationDate;
    private String lisenceNo;
    private String orgType;
    private String orgWebsite;
    private String orgDescription;

}

