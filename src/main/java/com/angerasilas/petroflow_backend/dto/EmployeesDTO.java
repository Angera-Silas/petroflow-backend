package com.angerasilas.petroflow_backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeesDTO {
    private Long id;
    private Long userId;
    private String jobTitle;
    private String employmentType;
    private Double salary;
    private String nssfNo;
    private String nhifNo;
    private String kraPin;
    private String hireDate;
    private String bankName;
    private String accountNo;
}