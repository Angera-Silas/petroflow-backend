package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.OrganizationFacilityDTO;
import com.angerasilas.petroflow_backend.entity.OrganizationFacility;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;

@Component
public class OrganizationFacilityMapper {

    public static OrganizationFacility toEntity(OrganizationFacilityDTO dto) {
        OrganizationFacility organizationFacility = new OrganizationFacility();
        OrganizationFacilityId organizationFacilityId = new OrganizationFacilityId();
        organizationFacilityId.setFacilityId(Long.parseLong(dto.getFacilityId()));
        organizationFacilityId.setOrganizationId(Long.parseLong(dto.getOrganizationId()));
        organizationFacility.setOrganizationFacilityId(organizationFacilityId);
        return organizationFacility;
    }

    public OrganizationFacilityDTO toDTO(OrganizationFacility organizationFacility) {
        OrganizationFacilityDTO organizationFacilityDTO = new OrganizationFacilityDTO();
        organizationFacilityDTO.setOrganizationId(String.valueOf(organizationFacility.getOrganizationFacilityId().getOrganizationId()));
        organizationFacilityDTO.setFacilityId(String.valueOf(organizationFacility.getOrganizationFacilityId().getFacilityId()));
        return organizationFacilityDTO;
    }
}