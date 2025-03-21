package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.AvailableRolesDto;
import com.angerasilas.petroflow_backend.dto.DepartmentDto;
import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;

public interface OrganizationEmployeeService {
    
    OrganizationEmployeeDto addEmployeeToOrganization(OrganizationEmployeeDto organizationEmployeeDto);

    OrganizationEmployeeDto updateEmployeeToOrganization(OrganizationEmployeeId organizationEmployeeId, OrganizationEmployeeDto organizationEmployeeDto);

    OrganizationEmployeeDto getEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId);

    void deleteEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId);

    List<OrganizationEmployeeDto> saveAllOrganizationEmployees(List<OrganizationEmployeeDto> organizationEmployeeDtos );

    List<OrganizationEmployeeDto> getAllOrganizationEmployees();
    List<EmployeeDetailsDto> getEmployeesByOrganizationIdAndFacilityId(Long organizationId, Long facilityId);
    List<EmployeeDetailsDto> getEmployeesWithOrganizationAndFacilityNames(Long organizationId);
    List<EmployeeDetailsDto> getEmployeesWithOrganization();

    List<AvailableRolesDto> getDistinctRolesByOrganizationId(Long organizationId);
    List<AvailableRolesDto> getDistinctRolesByOrganizationIdAndFacilityId(Long organizationId, Long facilityId);
    List<UserInfoDto> getEmployeesByOrganizationIdAndRole(Long organizationId, String role);
    List<UserInfoDto> getEmployeesByOrganizationIdAndFacilityIdAndRole(Long organizationId, Long facilityId, String role);

    List<DepartmentDto> getDepartmentsByOrganizationId(Long organizationId);
    List<DepartmentDto> getDepartmentsByOrganizationIdAndFacilityId(Long organizationId, Long facilityId);
}
