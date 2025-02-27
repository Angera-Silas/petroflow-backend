package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    private Long organizationId;
    private Long facilityId;
    private Long employeeId;
    private String facilityName;
    private String employeeNo;
    private String firstname;
    private String lastname;
    private String department;
    private String jobTitle;
    private String employmentType;
    private String email;
    private String phoneNumber;
    private String organizationName;
    private String city;
    private String postalCode;
    private String physicalAddress;
    private String dateOfBirth;
    private String hireDate;
    private Long userId;
}
