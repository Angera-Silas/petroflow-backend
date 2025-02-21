package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.OrganizationEmployeeDto;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.OrganizationEmployeeMapper;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.service.OrganizationEmployeeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class OrganizationEmployeeServiceImpl implements OrganizationEmployeeService {

    @Autowired
    private OrganizationEmployeesRepository organizationEmployeesRepository;

    @Autowired
    private OrganizationEmployeeMapper organizationEmployeeMapper;

    @Override
    public OrganizationEmployeeDto addEmployeeToOrganization(OrganizationEmployeeDto organizationEmployeeDto) {
        
        OrganizationEmployees organizationEmployee = organizationEmployeeMapper.toEntity(organizationEmployeeDto);
        OrganizationEmployees savedOrganizationEmployee = organizationEmployeesRepository.save(organizationEmployee);

        return organizationEmployeeMapper.toDTO(savedOrganizationEmployee);
    }

    @Override
    public OrganizationEmployeeDto updateEmployeeToOrganization(OrganizationEmployeeId organizationEmployeeId, OrganizationEmployeeDto organizationEmployeeDto) {
        OrganizationEmployees organizationEmployee = organizationEmployeesRepository.findById(organizationEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Employee not found with id " + organizationEmployeeId));

        // Update all fields from DTO
        organizationEmployee.setId(organizationEmployeeId);
        organizationEmployee.setDepartment(organizationEmployeeDto.getDepartment());
        organizationEmployee.setShift(organizationEmployeeDto.getShift());
        organizationEmployee.setTransferDate(organizationEmployeeDto.getTransferDate());

        OrganizationEmployees savedOrganizationEmployee = organizationEmployeesRepository.save(organizationEmployee);

        return organizationEmployeeMapper.toDTO(savedOrganizationEmployee);
    }

    @Override
    public OrganizationEmployeeDto getEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId) {
        OrganizationEmployees organizationEmployee = organizationEmployeesRepository.findById(organizationEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Employee not found with id " + organizationEmployeeId));

        return organizationEmployeeMapper.toDTO(organizationEmployee);
    }

    @Override
    public void deleteEmployeeFromOrganization(OrganizationEmployeeId organizationEmployeeId) {
        OrganizationEmployees organizationEmployee = organizationEmployeesRepository.findById(organizationEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization Employee not found with id " + organizationEmployeeId));

        organizationEmployeesRepository.delete(organizationEmployee);
    }

    @Override
    public List<OrganizationEmployeeDto> saveAllOrganizationEmployees(List<OrganizationEmployeeDto> organizationEmployeeDtos) {
        List<OrganizationEmployees> organizationEmployees = organizationEmployeeDtos.stream().map(organizationEmployeeMapper::toEntity).collect(Collectors.toList());

        List<OrganizationEmployees> savedEmployees = organizationEmployeesRepository.saveAll(organizationEmployees);

        return savedEmployees.stream().map(organizationEmployeeMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrganizationEmployeeDto> getAllOrganizationEmployees() {
        List<OrganizationEmployees> organizationEmployees = organizationEmployeesRepository.findAll();

        return organizationEmployees.stream().map(organizationEmployeeMapper::toDTO).collect(Collectors.toList());
    }
}
