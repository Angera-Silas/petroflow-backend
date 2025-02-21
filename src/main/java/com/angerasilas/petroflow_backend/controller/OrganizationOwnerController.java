package com.angerasilas.petroflow_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angerasilas.petroflow_backend.dto.OrganizationOwnerDTO;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationOwnersId;
import com.angerasilas.petroflow_backend.service.OrganizationOwnerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/organization-owner")
public class OrganizationOwnerController {
    @Autowired
    private OrganizationOwnerService service;

    //add organization owner
    @PostMapping("/add")
    public ResponseEntity<OrganizationOwnerDTO> addOrganizationOwner(@RequestBody OrganizationOwnerDTO organizationOwnerDto){
        return new ResponseEntity<>(service.addOrganizationOwner(organizationOwnerDto), HttpStatus.CREATED);
    }

    //update organization owner
    @PostMapping("/update/{organizationId}/{userId}")
    public ResponseEntity<OrganizationOwnerDTO> updateOrganizationOwner(@PathVariable Long organizationId, @PathVariable Long userId, @RequestBody OrganizationOwnerDTO organizationOwnerDto){
        OrganizationOwnersId id =  new OrganizationOwnersId(organizationId, userId);
        return new ResponseEntity<>(service.updateOrganizationOwner(id, organizationOwnerDto), HttpStatus.OK);
    }  

    //delete organization owner 
    @PostMapping("/delete/{organizationId}/{userId}")
    public ResponseEntity<?> deleteOrganizationOwner(@PathVariable Long organizationId, @PathVariable Long userId){
        OrganizationOwnersId id =  new OrganizationOwnersId(organizationId, userId);
        service.deleteOrganizationOwner(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //get organization owner by id
    @PostMapping("/get/{organizationId}/{userId}")
    public ResponseEntity<OrganizationOwnerDTO> getOrganizationOwnerById(@PathVariable Long organizationId, @PathVariable Long userId){
        OrganizationOwnersId id =  new OrganizationOwnersId(organizationId, userId);
        return new ResponseEntity<>(service.getOrganizationOwnerById(id), HttpStatus.OK);
    }
}
