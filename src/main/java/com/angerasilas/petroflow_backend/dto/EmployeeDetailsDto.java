package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDto {
    private String firstname;
    private String lastname;
    private String role;
    private String department;
    private String jobTitle;
    private String employmentType;
    private String email;
    private String phoneNumber;
    private String organizationName;
    private String facilityName;
    private String employeeNo;
    private String city;
    private String postalCode;
    private String physicalAddress;
    private String salary;
    private String dateOfBirth;
    private String hireDate;
    private String employmentStatus;
}