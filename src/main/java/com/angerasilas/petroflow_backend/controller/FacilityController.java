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

import com.angerasilas.petroflow_backend.dto.FacilityDetailsDto;
import com.angerasilas.petroflow_backend.dto.FacilityDto;
import com.angerasilas.petroflow_backend.service.FacilityService;
//import com.angerasilas.petroflow_backend.service.OrganizationFacilityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/facilities")
public class FacilityController {
    
    private final FacilityService facilityService;
    //private final OrganizationFacilityService organizationFacilityService;
    
    @PostMapping("/add")
    public ResponseEntity<FacilityDto> addFacility(@RequestBody FacilityDto facilityDto){
         return new ResponseEntity<>(facilityService.createFacility(facilityDto), HttpStatus.CREATED);
     }
    
    @GetMapping("/getFacility/{id}")
    public ResponseEntity<FacilityDto> getFacility(@PathVariable Long id){
        return new ResponseEntity<>(facilityService.getFacilityById(id), HttpStatus.OK);
    }
    
    
    @GetMapping("/getAllFacilities")
    public ResponseEntity<List<FacilityDto>> getAllFacilities(){
        return new ResponseEntity<>(facilityService.getAllFacilities(), HttpStatus.OK);
    }
    
    
    @DeleteMapping("/deleteFacility/{id}")
    public ResponseEntity<?> deleteFacility(@PathVariable Long id){
        facilityService.deleteFacility(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @PutMapping("/updateFacility/{id}")
    public ResponseEntity<FacilityDto> updateFacility(@PathVariable("id") Long id, @RequestBody FacilityDto facilityDto){
        FacilityDto updatedFacility = facilityService.updateFacility(id, facilityDto);

        return new ResponseEntity<>(updatedFacility, HttpStatus.OK);
    }

    @PostMapping("/add/all")
    public ResponseEntity<List<FacilityDto>> addFacilities(@RequestBody List<FacilityDto> facilityDtos){
        return new ResponseEntity<>(facilityService.addFacilities(facilityDtos), HttpStatus.CREATED);
    }


    @GetMapping("/organization/{organizationId}")
    public List<FacilityDto> getFacilitiesByOrganization(@PathVariable("organizationId") Long organizationId) {
        return facilityService.getFacilitiesByOrganization(organizationId);
    }

     @GetMapping("/get/all/details")
    public List<FacilityDetailsDto> getFacilityDetails() {
        return facilityService.getAllFacilitiesDetails();
    }
}
