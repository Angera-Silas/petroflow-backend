package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.OrganizationFacilityDTO;
import com.angerasilas.petroflow_backend.entity.OrganizationFacility;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.OrganizationFacilityMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationFacilityRepository;
import com.angerasilas.petroflow_backend.service.OrganizationFacilityService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationFacilityServiceImpl implements OrganizationFacilityService {

    @Autowired
    private OrganizationFacilityRepository repository;

    @Autowired
    private OrganizationFacilityMapper mapper;

    // add organization facility
    @Override
    public OrganizationFacilityDTO createOrganizationFacility(OrganizationFacilityDTO organizationFacilityDTO) {
        OrganizationFacility organizationFacility = OrganizationFacilityMapper.toEntity(organizationFacilityDTO);
        OrganizationFacility savedOrganizationFacility = repository.save(organizationFacility);

        return mapper.toDTO(savedOrganizationFacility);
    }

    @Override
    public OrganizationFacilityDTO updateOrganizationFacility(OrganizationFacilityId organizationFacilityId,
            OrganizationFacilityDTO organizationFacilityDTO) {
        OrganizationFacility organizationFacility = repository.findById(organizationFacilityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Organization Facility not found with id " + organizationFacilityId));

        // Update all fields from DTO
        organizationFacility.setOrganizationFacilityId(organizationFacilityId);

        OrganizationFacility savedOrganizationFacility = repository.save(organizationFacility);

        return mapper.toDTO(savedOrganizationFacility);
    }

    // get all organization facilities
    @Override
    public List<OrganizationFacilityDTO> getAllOrganizationFacilities() {
        List<OrganizationFacility> organizationFacilities = repository.findAll();

        return organizationFacilities.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    // get organization facility by id
    @Override
    public OrganizationFacilityDTO getOrganizationFacilityById(OrganizationFacilityId id) {
        OrganizationFacility organizationFacility = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Facility not found with id " + id));

        return mapper.toDTO(organizationFacility);
    }

    // delete organization facility
    @Override
    public void deleteOrganizationFacility(OrganizationFacilityId id) {
        repository.deleteById(id);
    }

    @Override
    public List<OrganizationFacilityDTO> addNewFacilities(List<OrganizationFacilityDTO> organizationFacilityDTOs) {

        List<OrganizationFacility> organizationFacilities = organizationFacilityDTOs.stream()
                .map(OrganizationFacilityMapper::toEntity)
                .collect(Collectors.toList());

        List<OrganizationFacility> savedFacility = repository.saveAll(organizationFacilities);

        return savedFacility.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

}