package com.angerasilas.petroflow_backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationEmployeeDto {
    private String employeeNo;  // Assigned by organization
    private Long organizationId;
    private Long facilityId;
    private String shift;
    private String department;
    private Long employeeId;  // Links to Employees table
    private String employmentStatus;
    private String transferDate;
}

