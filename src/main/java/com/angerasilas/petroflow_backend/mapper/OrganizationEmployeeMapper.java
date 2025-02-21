package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;

@Component
public class OrganizationEmployeeMapper {
    
    private OrganizationEmployeeDto organizationEmployeeDto = new OrganizationEmployeeDto();
    private OrganizationEmployeeId organizationEmployeeId = new OrganizationEmployeeId();
    private OrganizationEmployees organizationEmployees = new OrganizationEmployees();

    public OrganizationEmployees toEntity(OrganizationEmployeeDto organizationEmployeeDto) {

        organizationEmployeeId.setOrganizationId(organizationEmployeeDto.getOrganizationId());
        organizationEmployeeId.setEmployeeId(organizationEmployeeDto.getEmployeeId());
        organizationEmployeeId.setFacilityId(organizationEmployeeDto.getFacilityId());

        
        organizationEmployees.setId(organizationEmployeeId);
        organizationEmployees.setTransferDate(organizationEmployeeDto.getTransferDate());
        organizationEmployees.setEmployeeNo(organizationEmployeeDto.getEmployeeNo());
        organizationEmployees.setEmploymentStatus(organizationEmployeeDto.getEmploymentStatus());
        organizationEmployees.setDepartment(organizationEmployeeDto.getDepartment());
        organizationEmployeeDto.setShift(organizationEmployeeDto.getShift());

        return organizationEmployees;
    }

    public OrganizationEmployeeDto toDTO(OrganizationEmployees organizationEmployees) {
        organizationEmployeeDto.setOrganizationId(organizationEmployees.getId().getOrganizationId());
        organizationEmployeeDto.setEmployeeId(organizationEmployees.getId().getEmployeeId());
        organizationEmployeeDto.setFacilityId(organizationEmployees.getId().getFacilityId());
        organizationEmployeeDto.setTransferDate(organizationEmployees.getTransferDate());
        organizationEmployeeDto.setEmployeeNo(organizationEmployees.getEmployeeNo());
        organizationEmployeeDto.setEmploymentStatus(organizationEmployees.getEmploymentStatus());
        organizationEmployeeDto.setDepartment(organizationEmployees.getDepartment());
        organizationEmployeeDto.setDepartment(organizationEmployees.getShift());

        return organizationEmployeeDto;
    }
}
