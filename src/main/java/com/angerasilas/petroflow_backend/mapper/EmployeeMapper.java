package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.EmployeesDTO;
import com.angerasilas.petroflow_backend.entity.Employees;
import com.angerasilas.petroflow_backend.entity.User;

public class EmployeeMapper {
    public static EmployeesDTO mapToEmployeesDTO(Employees employees) {
        return new EmployeesDTO(
                employees.getId(),
                employees.getUser().getId(),
                employees.getJobTitle(),
                employees.getEmploymentType(),
                employees.getSalary(),
                employees.getNssfNo(),
                employees.getNhifNo(),
                employees.getKraPin(),
                employees.getHireDate(),
                employees.getBankName(),
                employees.getAccountNo()
        );
    }

    public static Employees mapToEmployees(EmployeesDTO employeesDTO, User user) {
        return new Employees(
                employeesDTO.getId(),
                user,
                employeesDTO.getJobTitle(),
                employeesDTO.getEmploymentType(),
                employeesDTO.getSalary(),
                employeesDTO.getNssfNo(),
                employeesDTO.getNhifNo(),
                employeesDTO.getKraPin(),
                employeesDTO.getHireDate(),
                employeesDTO.getBankName(),
                employeesDTO.getAccountNo(),
                null
        );
    }
}
