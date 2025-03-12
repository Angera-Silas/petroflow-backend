package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Shift;
import org.springframework.stereotype.Component;

@Component
public class ShiftMapper {

    public ShiftDto toDto(Shift shift) {
        if (shift == null) {
            return null;
        }

        ShiftDto dto = new ShiftDto();
        dto.setId(shift.getId());
        dto.setEmployeeNo(shift.getEmployee() != null ? shift.getEmployee().getEmployeeNo() : null);
        dto.setStartDate(shift.getStartDate());
        dto.setEndDate(shift.getEndDate());
        dto.setType(shift.getType().name());

        return dto;
    }

    public Shift toEntity(ShiftDto dto) {
        if (dto == null) {
            return null;
        }

        Shift entity = new Shift();
        entity.setId(dto.getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setType(Shift.ShiftType.valueOf(dto.getType()));

        // Set relationships
        if (dto.getEmployeeNo() != null) {
            OrganizationEmployees employee = new OrganizationEmployees();
            employee.setEmployeeNo(dto.getEmployeeNo());
            entity.setEmployee(employee);
        }

        return entity;
    }
}