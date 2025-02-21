package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.FacilityDetailsDto;
import com.angerasilas.petroflow_backend.dto.FacilityDto;

public interface FacilityService{
    //add facility
    FacilityDto createFacility(FacilityDto facilityDTO);

    //update facility
    FacilityDto updateFacility(Long facilityId, FacilityDto facilityDTO);

    //get all facilities
    List<FacilityDto> getAllFacilities();

    //get facility by id
    FacilityDto getFacilityById(Long id);

    //delete facility
    void deleteFacility(Long id);

    //add facilities
    List<FacilityDto> addFacilities(List<FacilityDto> facilityDTOs);

    List<FacilityDto> getFacilitiesByOrganization(Long organizationId);

    List<FacilityDetailsDto> getAllFacilitiesDetails();
    
}
