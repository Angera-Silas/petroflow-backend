package com.angerasilas.petroflow_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.OrganizationOwnerDTO;
import com.angerasilas.petroflow_backend.entity.OrganizationOwner;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationOwnersId;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.OrganizationOwnerMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationOwnerRepository;
import com.angerasilas.petroflow_backend.service.OrganizationOwnerService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrganizationOwnerServiceImpl implements OrganizationOwnerService {

    @Autowired
    private final OrganizationOwnerRepository organizationOwnerRepository;

    @Autowired
    private final OrganizationOwnerMapper organizationOwnerMapper;
    

    //add organization owner
    @Override
    public OrganizationOwnerDTO addOrganizationOwner(OrganizationOwnerDTO organizationOwnerDTO) {
        OrganizationOwner organizationOwner = organizationOwnerMapper.toEntity(organizationOwnerDTO);
        OrganizationOwner savedOrganizationOwner = organizationOwnerRepository.save(organizationOwner);

        return organizationOwnerMapper.toDTO(savedOrganizationOwner);
    }

    //update organization owner
    @Override
    public OrganizationOwnerDTO updateOrganizationOwner(OrganizationOwnersId organizationOwnerId, OrganizationOwnerDTO organizationOwnerDTO) {
        OrganizationOwner organizationOwner = organizationOwnerRepository.findById(organizationOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Owner not found with id " + organizationOwnerId));
        ;

        organizationOwner.setId(organizationOwnerId);

        return organizationOwnerMapper.toDTO(organizationOwnerRepository.save(organizationOwner));
    }

    //delete organization owner

    @Override
    public void deleteOrganizationOwner(OrganizationOwnersId organizationOwnerId) {
        organizationOwnerRepository.deleteById(organizationOwnerId);
    }

    //get organization owner by id
    @Override
    public OrganizationOwnerDTO getOrganizationOwnerById(OrganizationOwnersId organizationOwnerId) {
        OrganizationOwner organizationOwner = organizationOwnerRepository.findById(organizationOwnerId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Owner not found with id " + organizationOwnerId));
        ;

        return organizationOwnerMapper.toDTO(organizationOwner);
    }

}
