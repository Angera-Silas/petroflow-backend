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

import com.angerasilas.petroflow_backend.dto.OrganizationDetailsDto;
import com.angerasilas.petroflow_backend.dto.OrganizationDto;
import com.angerasilas.petroflow_backend.service.OrganizationService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private OrganizationService organizationService;

    //Build Add Organization REST API - Endpoint /api/organizations/add
    @PostMapping("/add")
    public ResponseEntity<OrganizationDto> createOrganization(@RequestBody OrganizationDto organizationDto){
        OrganizationDto savedOrganization = organizationService.createOrganization(organizationDto);

        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }

    //Build Get Organization By Id REST API - Endpoint /api/organizations/get/{organizationId}
    @GetMapping("/get/{organizationId}")
    public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable("organizationId") Long organizationId){
        OrganizationDto organizationDto = organizationService.getOrganizationById(organizationId);
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    //Build Get All Organizations REST API - Endpoint /api/organizations/get/all
    @GetMapping("/get/all")
    public ResponseEntity<List<OrganizationDto>> getAllOrganizations(){
        List<OrganizationDto> organizationsData = organizationService.getAllOrganizations();
        return ResponseEntity.ok(organizationsData);
    }

    //Build Update Organization REST API - Endpoint /api/organizations/update/{organizationId}
    @PutMapping("/update/{organizationId}")
    public ResponseEntity<OrganizationDto> updateOrganization(@PathVariable("organizationId") Long organizationId, @RequestBody OrganizationDto organizationDto){
        OrganizationDto updatedOrganization = organizationService.updateOrganization(organizationId, organizationDto);
        return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
    }

    //Build Delete Organization REST API - Endpoint /api/organizations/delete/{organizationId}
    @DeleteMapping("/delete/{organizationId}")
    public ResponseEntity<Void> deleteOrganization(@PathVariable("organizationId") Long organizationId){
        organizationService.deleteOrganization(organizationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get/details/all")
    public List<OrganizationDetailsDto> getOrganizationDetails() {
        return organizationService.getOrganizationsWithCounts();
    }
    
}
