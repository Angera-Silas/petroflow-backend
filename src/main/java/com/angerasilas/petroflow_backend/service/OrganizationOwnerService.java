package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.OrganizationOwnerDTO;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationOwnersId;

public interface OrganizationOwnerService {
    //addOrganizationOwner
    OrganizationOwnerDTO addOrganizationOwner(OrganizationOwnerDTO organizationOwnerDTO);

    //updateOrganizationOwner
    OrganizationOwnerDTO updateOrganizationOwner(OrganizationOwnersId organizationOwnersId, OrganizationOwnerDTO organizationOwnerDTO);

    //getOrganizationOwnerById
    OrganizationOwnerDTO getOrganizationOwnerById(OrganizationOwnersId organizationOwnersId);


    //deleteOrganizationOwner
    void deleteOrganizationOwner(OrganizationOwnersId organizationOwnersId);

}
