package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.angerasilas.petroflow_backend.dto.AvailableRolesDto;
import com.angerasilas.petroflow_backend.dto.DepartmentDto;
import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;
import com.angerasilas.petroflow_backend.service.OrganizationEmployeeService;


@RestController
@RequestMapping("/api/organization-employee")
public class OrganizationEmployee {

    @Autowired
    private OrganizationEmployeeService service;

    // add an organization employee
    @PostMapping("/add")
    public ResponseEntity<OrganizationEmployeeDto> addEmployeeToOrganization(@RequestBody OrganizationEmployeeDto organizationEmployeeDto) {
        OrganizationEmployeeDto savedOrganizationEmployee = service.addEmployeeToOrganization(organizationEmployeeDto);
        return new ResponseEntity<>(savedOrganizationEmployee, HttpStatus.CREATED);
    }

    // Update organization employee
    @PutMapping("/update/{organizationId}/{facilityId}/{employeeId}")
    public ResponseEntity<OrganizationEmployeeDto> updateEmployeeToOrganization(@PathVariable Long organizationId,@PathVariable Long facilityId,  @PathVariable Long employeeId, @RequestBody OrganizationEmployeeDto organizationEmployeeDto) {
        OrganizationEmployeeId id = new OrganizationEmployeeId(organizationId,facilityId, employeeId);
        OrganizationEmployeeDto updatedOrganizationEmployee = service.updateEmployeeToOrganization(id, organizationEmployeeDto);
        return new ResponseEntity<>(updatedOrganizationEmployee, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/delete/{organizationId}/{facilityId}/{employeeId}")
    public ResponseEntity<Void> deleteEmployeeFromOrganization(@PathVariable Long organizationId,@PathVariable Long facilityId,  @PathVariable Long employeeId, @RequestBody OrganizationEmployeeDto organizationEmployeeDto){
        OrganizationEmployeeId id = new OrganizationEmployeeId(organizationId,facilityId, employeeId);
        service.deleteEmployeeFromOrganization(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //get


    //add all
    @PostMapping("/add/all")
    public ResponseEntity<List<OrganizationEmployeeDto>> saveAllOrganizationEmployees(@RequestBody List<OrganizationEmployeeDto> organizationEmployeeDtos) {
        List<OrganizationEmployeeDto> savedEmployees = service.saveAllOrganizationEmployees(organizationEmployeeDtos);
        return new ResponseEntity<>(savedEmployees, HttpStatus.CREATED);
    }
    

    //get all
    @GetMapping("/get/all")
    public ResponseEntity<List<OrganizationEmployeeDto>> getAllOrganizationEmployees() {
        List<OrganizationEmployeeDto> organizationEmployees = service.getAllOrganizationEmployees();
        return ResponseEntity.ok(organizationEmployees);
    }


    // Get employees by organization and facility
    @GetMapping("/details/get/{organizationId}/facility/{facilityId}")
    public ResponseEntity<List<EmployeeDetailsDto>> getEmployeesByOrganizationIdAndFacilityId(
            @PathVariable Long organizationId,
            @PathVariable Long facilityId) {
        List<EmployeeDetailsDto> employees = service.getEmployeesByOrganizationIdAndFacilityId(organizationId,
                facilityId);
        return ResponseEntity.ok(employees);
    }

    // Get employees with organization and facility names
    @GetMapping("/details/get/{organizationId}")
    public ResponseEntity<List<EmployeeDetailsDto>> getEmployeesWithOrganizationAndFacilityNames(
            @PathVariable Long organizationId) {
        List<EmployeeDetailsDto> employees = service
                .getEmployeesWithOrganizationAndFacilityNames(organizationId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/details/get/all")
    public ResponseEntity<List<EmployeeDetailsDto>> getAllEmployeesWithOrganization() {
        List<EmployeeDetailsDto> employees = service.getEmployeesWithOrganization();
        return ResponseEntity.ok(employees);
    }

    //get available roles 

    @GetMapping("/get/organization/{organizationId}/roles")
    public ResponseEntity<List<AvailableRolesDto>> getDistinctRolesByOrganizationId(@PathVariable Long organizationId) {
        List<AvailableRolesDto> roles = service.getDistinctRolesByOrganizationId(organizationId);
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/get/organization/{organizationId}/facility/{facilityId}/roles")
    public ResponseEntity<List<AvailableRolesDto>> getDistinctRolesByOrganizationIdAndFacilityId(
            @PathVariable Long organizationId, @PathVariable Long facilityId) {
        List<AvailableRolesDto> roles = service.getDistinctRolesByOrganizationIdAndFacilityId(organizationId, facilityId);
        return ResponseEntity.ok(roles);
    }

    //get users by role
    
    @GetMapping("/get/organization/{organizationId}/employees/{role}")
    public ResponseEntity<List<UserInfoDto>> getEmployeesByOrganizationIdAndRole(
            @PathVariable Long organizationId, @PathVariable String role) {
        List<UserInfoDto> employees = service.getEmployeesByOrganizationIdAndRole(organizationId, role);
        return ResponseEntity.ok(employees);

    }

    
    @GetMapping("/get/organization/{organizationId}/facility/{facilityId}/employees/{role}")
    public ResponseEntity<List<UserInfoDto>> getEmployeesByOrganizationIdAndFacilityIdAndRole(
            @PathVariable Long organizationId, @PathVariable Long facilityId, @PathVariable String role) {
        List<UserInfoDto> employees = service.getEmployeesByOrganizationIdAndFacilityIdAndRole(organizationId, facilityId, role);
        return ResponseEntity.ok(employees);
    }

    //get departments
    @GetMapping("/get/organization/{organizationId}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByOrganizationId(@PathVariable Long organizationId) {
        List<DepartmentDto> departments = service.getDepartmentsByOrganizationId(organizationId);
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/get/organization/{organizationId}/facility/{facilityId}/departments")
    public ResponseEntity<List<DepartmentDto>> getDepartmentsByOrganizationIdAndFacilityId(
            @PathVariable Long organizationId, @PathVariable Long facilityId) {
        List<DepartmentDto> departments = service.getDepartmentsByOrganizationIdAndFacilityId(organizationId, facilityId);
        return ResponseEntity.ok(departments);
    }
    

}
