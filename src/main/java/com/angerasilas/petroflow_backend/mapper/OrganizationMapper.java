package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.OrganizationDto;
import com.angerasilas.petroflow_backend.entity.Organization;

public class OrganizationMapper {
    public static OrganizationDto mapToOrganizationDto(Organization organization) {
        return new OrganizationDto(
            organization.getId(), 
            organization.getName(), 
            organization.getCounty(),
            organization.getTown(),
            organization.getStreet(),
            organization.getPhone(),
            organization.getEmail(),
            organization.getPhysicalAddress(),
            organization.getPostalCode(),
            organization.getNumberOfStations(),
            organization.getRegistrationDate(),
            organization.getLisenceNo(),
            organization.getOrgType(),
            organization.getOrgWebsite(),
            organization.getOrgDescription()
            );
    }

    public static Organization mapToOrganization(OrganizationDto organizationDto){
        return new Organization(
            organizationDto.getOrgId(),
            organizationDto.getOrgName(),
            organizationDto.getOrgCounty(),
            organizationDto.getOrgTown(),
            organizationDto.getOrgStreet(),
            organizationDto.getOrgPhone(),
            organizationDto.getOrgEmail(),
            organizationDto.getPhysicalAddress(),
            organizationDto.getOrgPostalCode(),
            organizationDto.getNumberOfStations(),
            organizationDto.getRegistrationDate(),
            organizationDto.getLisenceNo(),
            organizationDto.getOrgType(),
            organizationDto.getOrgWebsite(),
            organizationDto.getOrgDescription(),
            null,
            null,
            null,
            null,
            null,
            null
            );
    }
}
