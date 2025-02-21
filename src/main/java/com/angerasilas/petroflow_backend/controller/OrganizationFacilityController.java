package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.dto.OrganizationFacilityDTO;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;
import com.angerasilas.petroflow_backend.service.OrganizationFacilityService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/organization-facilities")
public class OrganizationFacilityController {

    @Autowired
    private OrganizationFacilityService service;

    // Create OrganizationFacility
    @PostMapping("/add")
    public OrganizationFacilityDTO createOrganizationFacility(@RequestBody OrganizationFacilityDTO dto) {
        return service.createOrganizationFacility(dto);
    }

    // Update OrganizationFacility
    @PutMapping("/update/{organizationId}/{facilityId}")
    public OrganizationFacilityDTO updateOrganizationFacility(@PathVariable Long organizationId, @PathVariable Long facilityId, @RequestBody OrganizationFacilityDTO dto) {
        OrganizationFacilityId id = new OrganizationFacilityId(organizationId, facilityId);
        return service.updateOrganizationFacility(id, dto);
    }

    // Get all OrganizationFacilities
    @GetMapping("/all")
    public Iterable<OrganizationFacilityDTO> getAllOrganizationFacilities() {
        return service.getAllOrganizationFacilities();
    }

    // Get OrganizationFacility by id
    @GetMapping("/get/{organizationId}/{facilityId}")
    public OrganizationFacilityDTO getOrganizationFacilityById(@PathVariable Long organizationId, @PathVariable Long facilityId) {
        OrganizationFacilityId id = new OrganizationFacilityId(organizationId, facilityId);
        return service.getOrganizationFacilityById(id);
    }

    // Delete OrganizationFacility
    @DeleteMapping("/delete/{organizationId}/{facilityId}")
    public void deleteOrganizationFacility(@PathVariable Long organizationId, @PathVariable Long facilityId) {
        OrganizationFacilityId id = new OrganizationFacilityId(organizationId, facilityId);
        service.deleteOrganizationFacility(id);
    }

    // add 
    @PostMapping("/add/all")
    public ResponseEntity<List<OrganizationFacilityDTO>> saveAllOrganizationFacilities(@RequestBody List<OrganizationFacilityDTO> organizationFacilitiesDTOs) {
        List<OrganizationFacilityDTO> savedFacility = service.addNewFacilities(organizationFacilitiesDTOs);
        return new ResponseEntity<>(savedFacility, HttpStatus.CREATED);
    }
}
