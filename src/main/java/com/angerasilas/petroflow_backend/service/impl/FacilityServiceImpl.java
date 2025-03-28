package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.FacilityDetailsDto;
import com.angerasilas.petroflow_backend.dto.FacilityDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.FacilityMapper;
import com.angerasilas.petroflow_backend.repository.FacilityRepository;
import com.angerasilas.petroflow_backend.service.FacilityService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class FacilityServiceImpl implements FacilityService {
    @Autowired
    private FacilityRepository facilityRepository;

    // add facility
    @Override
    public FacilityDto createFacility(FacilityDto facilityDTO) {
        Facility facility = FacilityMapper.mapToFacility(facilityDTO);

        Facility savedFacility = facilityRepository.save(facility);
        
        return FacilityMapper.mapTOFacilityDto(savedFacility);
    }

    // update facility
    @Override
    public FacilityDto updateFacility(Long facilityId, FacilityDto facilityDTO) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id " + facilityId));

        facility.setCounty(facilityDTO.getFacilityCounty());
        facility.setTown(facilityDTO.getFacilityTown());
        facility.setStreet(facilityDTO.getFacilityStreet());
        facility.setPhysicalAddress(facilityDTO.getPhysicalAddress());
        facility.setPostalCode(facilityDTO.getFacilityPostalCode());
        facility.setPhone(facilityDTO.getFacilityPhone());
        facility.setEmail(facilityDTO.getFacilityEmail());
        facility.setServicesOffered(facilityDTO.getServicesOffered());
        

        Facility savedFacility = facilityRepository.save(facility);
        return FacilityMapper.mapTOFacilityDto(savedFacility);
    }

    // delete facility
    @Override
    public void deleteFacility(Long id) {
        facilityRepository.deleteById(id);
    }

    // get facility by id
    @Override
    public FacilityDto getFacilityById(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found with id " + id));
        return FacilityMapper.mapTOFacilityDto(facility);
    }

    // get all facilities
    @Override
    public List<FacilityDto> getAllFacilities() {
        return facilityRepository.findAll().stream().map(FacilityMapper::mapTOFacilityDto).collect(Collectors.toList());
    }

    // add facilities
    @Override
    public List<FacilityDto> addFacilities(List<FacilityDto> facilityDTOs) {
        List<Facility> facilities = facilityDTOs.stream().map(FacilityMapper::mapToFacility).collect(Collectors.toList());

        List<Facility> savedFacilities = facilityRepository.saveAll(facilities);

        return savedFacilities.stream().map(FacilityMapper::mapTOFacilityDto).collect(Collectors.toList());
    }

    public List<FacilityDetailsDto> getFacilitiesByOrganization(Long organizationId) {
        return facilityRepository.findFacilitiesByOrganizationId(organizationId);
    }

    @Override
    public List<FacilityDetailsDto> getAllFacilitiesDetails() {
        return facilityRepository.getAllFacilitiesWithCounts();
    }
}