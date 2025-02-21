package com.angerasilas.petroflow_backend.service;

import java.util.List;

import com.angerasilas.petroflow_backend.dto.OrganizationDetailsDto;
import com.angerasilas.petroflow_backend.dto.OrganizationDto;

public interface OrganizationService {
    
    // add Organization

    OrganizationDto createOrganization(OrganizationDto organizationDto);

    // update Organization
    OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto);

    // delete Organization
    void deleteOrganization(Long organizationId);

    // get Organization
    OrganizationDto getOrganizationById(Long organizationId);

    // get all Organizations
    List<OrganizationDto> getAllOrganizations();

     List<OrganizationDetailsDto> getOrganizationsWithCounts();
}
