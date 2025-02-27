package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.EmployeesDTO;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;
import com.angerasilas.petroflow_backend.service.EmployeeService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    // Add a single employee
    @PostMapping("/add")
    public ResponseEntity<EmployeesDTO> addEmployee(@RequestBody EmployeesDTO employeeDto) {
        EmployeesDTO createdEmployee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    // Add multiple employees
    @PostMapping("/add/all")
    public ResponseEntity<List<EmployeesDTO>> addEmployees(@RequestBody List<EmployeesDTO> employeesDtos) {
        List<EmployeesDTO> createdEmployees = employeeService.createEmployees(employeesDtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployees);
    }

    // Get a single employee by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeesDTO> getEmployeeById(@PathVariable Long id) {
        EmployeesDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // Get all employees
    @GetMapping("/get/all")
    public ResponseEntity<List<EmployeesDTO>> getAllEmployees() {
        List<EmployeesDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // Update employee details
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeesDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeesDTO employeeDto) {
        EmployeesDTO updatedEmployee = employeeService.updateEmployee(id, employeeDto);
        return ResponseEntity.ok(updatedEmployee);
    }

    // Update multiple employees
    @PutMapping("/update/all")
    public ResponseEntity<List<EmployeesDTO>> updateEmployees(@RequestBody List<EmployeesDTO> employeesDtos) {
        List<EmployeesDTO> updatedEmployees = employeeService.updateEmployees(employeesDtos);
        return ResponseEntity.ok(updatedEmployees);
    }

    // Delete an employee by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    // Delete multiple employees
    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteEmployees(@RequestBody List<Long> ids) {
        employeeService.deleteEmployees(ids);
        return ResponseEntity.noContent().build();
    }

    // Get employees by organization and facility
    @GetMapping("/details/get/{organizationId}/facility/{facilityId}")
    public ResponseEntity<List<EmployeeDetailsDto>> getEmployeesByOrganizationIdAndFacilityId(
            @PathVariable Long organizationId,
            @PathVariable Long facilityId) {
        List<EmployeeDetailsDto> employees = employeeService.getEmployeesByOrganizationIdAndFacilityId(organizationId,
                facilityId);
        return ResponseEntity.ok(employees);
    }

    // Get employees with organization and facility names
    @GetMapping("/details/get/{organizationId}")
    public ResponseEntity<List<EmployeeDetailsDto>> getEmployeesWithOrganizationAndFacilityNames(
            @PathVariable Long organizationId) {
        List<EmployeeDetailsDto> employees = employeeService
                .getEmployeesWithOrganizationAndFacilityNames(organizationId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/details/get/all")
    public ResponseEntity<List<EmployeeDetailsDto>> getAllEmployeesWithOrganization() {
        List<EmployeeDetailsDto> employees = employeeService.getEmployeesWithOrganization();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDto> getUserInfo(@PathVariable Long userId) {
        UserInfoDto userInfo = employeeService.getUserInfo(userId);
        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/info/all")
    public ResponseEntity<List<UserInfoDto>> getUsersInfo() {
        List<UserInfoDto> usersInfo = employeeService.getUsersInfo();
        return ResponseEntity.ok(usersInfo);
    }

    @GetMapping("/permissions")
    public ResponseEntity<Page<UserPermissionsDto>> getUsersPermissions(Pageable pageable) {
        Page<UserPermissionsDto> usersPermissions = employeeService.getUsersPermissions(pageable);
        return ResponseEntity.ok(usersPermissions);
    }
}
