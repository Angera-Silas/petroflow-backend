package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.EmployeesDTO;
import com.angerasilas.petroflow_backend.entity.Employees;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.EmployeeMapper;
import com.angerasilas.petroflow_backend.repository.EmployeesRepository;
import com.angerasilas.petroflow_backend.repository.UserRepository;
import com.angerasilas.petroflow_backend.service.EmployeeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeesRepository employeesRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public EmployeesDTO createEmployee(EmployeesDTO employeesDTO) {
        User user = userRepository.findById(employeesDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Employees employees = EmployeeMapper.mapToEmployees(employeesDTO,user);
        return EmployeeMapper.mapToEmployeesDTO(employeesRepository.save(employees));
    }

    @Override
    public EmployeesDTO updateEmployee(Long employeeId, EmployeesDTO employeesDTO) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeesDTO));
        ;

        employee.setAccountNo(employeesDTO.getAccountNo());
        employee.setEmploymentType(employeesDTO.getEmploymentType());
        employee.setJobTitle(employeesDTO.getJobTitle());
        employee.setSalary(employeesDTO.getSalary());
        employee.setBankName(employeesDTO.getBankName());

        return EmployeeMapper.mapToEmployeesDTO(employeesRepository.save(employee));
    }

    @Override
    public List<EmployeesDTO> getAllEmployees() {
        return employeesRepository.findAll().stream().map(EmployeeMapper::mapToEmployeesDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeesDTO getEmployeeById(Long id) {
        return employeesRepository.findById(id).map(EmployeeMapper::mapToEmployeesDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Override
    public void deleteEmployee(Long id) {
        employeesRepository.deleteById(id);
    }

    @Override
    public List<EmployeesDTO> createEmployees(List<EmployeesDTO> employeesDTOs) {
        List<Employees> employees = employeesDTOs.stream().map(employeesDTO -> {
            User user = userRepository.findById(employeesDTO.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return EmployeeMapper.mapToEmployees(employeesDTO, user);
        }).collect(Collectors.toList());
    
        List<Employees> savedEmployees = employeesRepository.saveAll(employees);
    
        return savedEmployees.stream().map(EmployeeMapper::mapToEmployeesDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeDetailsDto> getEmployeesByOrganizationIdAndFacilityId(Long organizationId, Long facilityId) {
        return employeesRepository.findEmployeesByOrganizationIdAndFacilityId(organizationId, facilityId);
    }

    @Override
    public List<EmployeeDetailsDto> getEmployeesWithOrganizationAndFacilityNames(Long organizationId) {
        return employeesRepository.findEmployeesWithOrganizationAndFacilityNames(organizationId);
    }

    @Override
    public List<EmployeeDetailsDto> getEmployeesWithOrganization() {
        return employeesRepository.findEmployeesWithOrganization();
    }

}
