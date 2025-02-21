package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;

public interface OrganizationEmployeeService {
    
    OrganizationEmployeeDto addEmployeeToOrganization(OrganizationEmployeeDto organizationEmployeeDto);

    OrganizationEmployeeDto updateEmployeeToOrganization(OrganizationEmployeeId organizationEmployeeId, OrganizationEmployeeDto organizationEmployeeDto);

    OrganizationEmployeeDto getEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId);

    void deleteEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId);

    List<OrganizationEmployeeDto> saveAllOrganizationEmployees(List<OrganizationEmployeeDto> organizationEmployeeDtos );

    List<OrganizationEmployeeDto> getAllOrganizationEmployees();
}
