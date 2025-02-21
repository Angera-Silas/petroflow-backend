package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.OrganizationFacilityDTO;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;

public interface OrganizationFacilityService {
    //add organization facility
    OrganizationFacilityDTO createOrganizationFacility(OrganizationFacilityDTO organizationFacilityDTO);

    //update organization facility
    OrganizationFacilityDTO updateOrganizationFacility(OrganizationFacilityId organizationFacilityId, OrganizationFacilityDTO organizationFacilityDTO);

    //get all organization facilities
    List<OrganizationFacilityDTO> getAllOrganizationFacilities();

    //get organization facility by id
    OrganizationFacilityDTO getOrganizationFacilityById(OrganizationFacilityId id);

    //delete organization facility
    void deleteOrganizationFacility(OrganizationFacilityId id);

    //add multiple
    List<OrganizationFacilityDTO> addNewFacilities(List<OrganizationFacilityDTO> organizationFacilityDTO);

}
