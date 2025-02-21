package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.OrganizationOwnerDTO;
import com.angerasilas.petroflow_backend.entity.OrganizationOwner;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationOwnersId;

@Component
public class OrganizationOwnerMapper {

    public OrganizationOwner toEntity(OrganizationOwnerDTO organizationOwnerDTO) {
        OrganizationOwnersId organizationOwnersId = new OrganizationOwnersId();
        organizationOwnersId.setOrganizationId(Long.parseLong(organizationOwnerDTO.getOrganizationId()));
        organizationOwnersId.setUserId(Long.parseLong(organizationOwnerDTO.getUserId()));

        OrganizationOwner organizationOwner = new OrganizationOwner();
        organizationOwner.setId(organizationOwnersId);

        return organizationOwner;
    }

    public OrganizationOwnerDTO toDTO(OrganizationOwner organizationOwner) {
        OrganizationOwnerDTO organizationOwnerDTO = new OrganizationOwnerDTO();
        organizationOwnerDTO.setOrganizationId(String.valueOf(organizationOwner.getId().getOrganizationId()));
        organizationOwnerDTO.setUserId(String.valueOf(organizationOwner.getId().getUserId()));

        return organizationOwnerDTO;
    }
}