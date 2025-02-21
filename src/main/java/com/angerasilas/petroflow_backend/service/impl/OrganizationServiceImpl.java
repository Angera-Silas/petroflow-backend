package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.OrganizationDetailsDto;
import com.angerasilas.petroflow_backend.dto.OrganizationDto;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.OrganizationMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationRepository;
import com.angerasilas.petroflow_backend.service.OrganizationService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl  implements OrganizationService{

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto createOrganization(OrganizationDto organizationDto){
        //map the Organization object to an Organization Entity

        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);

        Organization savedOrganization = organizationRepository.save(organization);
        
        return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto updateOrganization(Long organizationId, OrganizationDto organizationDto){
        //get the organization by id
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + organizationId));

        //update the organization
        organization.setName(organizationDto.getOrgName());
        organization.setEmail(organizationDto.getOrgEmail());
        organization.setPhone(organizationDto.getOrgPhone());
        organization.setCounty(organizationDto.getOrgCounty());
        organization.setTown(organizationDto.getOrgTown());
        organization.setPostalCode(organizationDto.getOrgPostalCode());
        organization.setPhysicalAddress(organizationDto.getPhysicalAddress());
        

        //save the updated organization
        Organization updatedOrganization = organizationRepository.save(organization);

        return OrganizationMapper.mapToOrganizationDto(updatedOrganization);
    }

    @Override
    public void deleteOrganization(Long organizationId){
        //get the organization by id
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + organizationId));

        //delete the organization
        organizationRepository.delete(organization);
    }

    @Override
    public OrganizationDto getOrganizationById(Long organizationId){
        //get the organization by id
        Organization organization = organizationRepository.findById(organizationId).orElseThrow(() -> new ResourceNotFoundException("Organization not found with id " + organizationId));

        return OrganizationMapper.mapToOrganizationDto(organization);
    }

    @Override
    public List<OrganizationDto> getAllOrganizations(){
        //get all organizations
        List<Organization> organizations = organizationRepository.findAll();

        return organizations.stream().map(OrganizationMapper::mapToOrganizationDto).collect(Collectors.toList());
    }


     @Override
    public List<OrganizationDetailsDto> getOrganizationsWithCounts() {
        return organizationRepository.getAllOrganizationsWithCounts();
    }

    
    
}
