package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.EmployeesDTO;

public interface EmployeeService {
    EmployeesDTO createEmployee(EmployeesDTO employeeDto);
    EmployeesDTO getEmployeeById(Long id);
    List<EmployeesDTO> getAllEmployees();
    void deleteEmployee(Long id);
    EmployeesDTO updateEmployee(Long id, EmployeesDTO employeeDto);
    List<EmployeesDTO> createEmployees(List<EmployeesDTO> employeesDtos);
    List<EmployeeDetailsDto> getEmployeesByOrganizationIdAndFacilityId(Long organizationId, Long facilityId);
    List<EmployeeDetailsDto> getEmployeesWithOrganizationAndFacilityNames(Long organizationId);
    List<EmployeeDetailsDto> getEmployeesWithOrganization();

}