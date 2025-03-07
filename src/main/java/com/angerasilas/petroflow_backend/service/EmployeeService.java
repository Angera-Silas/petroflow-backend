package com.angerasilas.petroflow_backend.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.EmployeesDTO;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;

public interface EmployeeService {
    EmployeesDTO createEmployee(EmployeesDTO employeeDto);
    EmployeesDTO getEmployeeById(Long id);
    List<EmployeesDTO> getAllEmployees();
    void deleteEmployee(Long id);
    void deleteEmployees(List<Long> ids);
    EmployeesDTO updateEmployee(Long id, EmployeesDTO employeeDto);
    List<EmployeesDTO> createEmployees(List<EmployeesDTO> employeesDtos);
    List<EmployeesDTO> updateEmployees(List<EmployeesDTO> employeesDtos);
    UserInfoDto getUserInfo(Long userId);
    List<UserInfoDto> getUsersInfo();
    Page<UserPermissionsDto> getUsersPermissions(Pageable pageable);

}