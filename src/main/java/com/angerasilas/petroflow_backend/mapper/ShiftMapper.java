package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.ShiftDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.Shift;

@Component
public class ShiftMapper {

    public static ShiftDto mapToShiftDto(Shift shift) {
        if (shift == null) {
            return null;
        }

        return new ShiftDto(
            shift.getId(),
            shift.getFacility().getId(),
            shift.getEmployee().getEmployeeNo(),
            shift.getStartDate(),
            shift.getEndDate(),
            shift.getType().name(),
            shift.getSellingPoints()
        );
    }

    public static Shift mapToShift(ShiftDto dto, OrganizationEmployees employee, Facility facility) {
        if (dto == null) {
            return null;
        }

        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null");
        }

        return new Shift(
            dto.getId(),
            facility,
            employee,
            dto.getStartDate(),
            dto.getEndDate(),
            Shift.ShiftType.valueOf(dto.getType()),
            dto.getSellingPoints(),
            null,
            null
        );
    }
}