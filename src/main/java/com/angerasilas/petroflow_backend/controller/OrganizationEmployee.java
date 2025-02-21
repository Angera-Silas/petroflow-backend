package com.angerasilas.petroflow_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;
import com.angerasilas.petroflow_backend.service.OrganizationEmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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


}
