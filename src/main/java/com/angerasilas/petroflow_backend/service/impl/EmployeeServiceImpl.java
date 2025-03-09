package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.dto.EmployeesDTO;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;
import com.angerasilas.petroflow_backend.entity.Employees;
import com.angerasilas.petroflow_backend.entity.User;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.EmployeeMapper;
import com.angerasilas.petroflow_backend.repository.EmployeesRepository;
import com.angerasilas.petroflow_backend.repository.PermissionRepository;
import com.angerasilas.petroflow_backend.repository.UserRepository;
import com.angerasilas.petroflow_backend.service.EmployeeService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private final EmployeesRepository employeesRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public EmployeesDTO createEmployee(EmployeesDTO employeesDTO) {
        User user = userRepository.findById(employeesDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employees employees = EmployeeMapper.mapToEmployees(employeesDTO, user);
        return EmployeeMapper.mapToEmployeesDTO(employeesRepository.save(employees));
    }

    @Override
    public EmployeesDTO updateEmployee(Long employeeId, EmployeesDTO employeesDTO) {
        Employees employee = employeesRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeesDTO.getId()));

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
    public List<EmployeesDTO> updateEmployees(List<EmployeesDTO> employeesDTOs) {
        List<Employees> employees = employeesDTOs.stream().map(employeesDTO -> {
            Employees employee = employeesRepository.findById(employeesDTO.getId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Employee not found with id " + employeesDTO.getId()));

            employee.setAccountNo(employeesDTO.getAccountNo());
            employee.setEmploymentType(employeesDTO.getEmploymentType());
            employee.setJobTitle(employeesDTO.getJobTitle());
            employee.setSalary(employeesDTO.getSalary());
            employee.setBankName(employeesDTO.getBankName());

            return employee;
        }).collect(Collectors.toList());

        List<Employees> updatedEmployees = employeesRepository.saveAll(employees);

        return updatedEmployees.stream().map(EmployeeMapper::mapToEmployeesDTO).collect(Collectors.toList());
    }

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        return employeesRepository.findUserInfoByUserId(userId);
    }

    @Override
    public void deleteEmployees(List<Long> ids) {
        employeesRepository.deleteAllById(ids);
    }

    @Override
    public List<UserInfoDto> getUsersInfo() {
        return employeesRepository.findUsersInfo();
    }

    @Override
    public Page<UserPermissionsDto> getUsersPermissions(Pageable pageable) {
        Page<UserPermissionsDto> usersPage = employeesRepository.findUsersPermissions(pageable);

        // Convert Page to List to modify permissions
        List<UserPermissionsDto> users = usersPage.getContent();

        users.forEach(user -> {
            List<String> permissions = permissionRepository.findPermissionsByUserId(user.getUserId());
            user.setPermissions(permissions);
        });

        return new PageImpl<>(users, pageable, usersPage.getTotalElements());
    }
}