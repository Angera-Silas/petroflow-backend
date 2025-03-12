package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.PumpMeterReadingDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.Organization;
import com.angerasilas.petroflow_backend.entity.PumpMeterReading;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import com.angerasilas.petroflow_backend.entity.Shift;

import org.springframework.stereotype.Component;

@Component
public class PumpMeterReadingMapper {

    public PumpMeterReadingDto toDto(PumpMeterReading pumpMeterReading) {
        if (pumpMeterReading == null) {
            return null;
        }

        PumpMeterReadingDto dto = new PumpMeterReadingDto();
        dto.setId(pumpMeterReading.getId());
        dto.setOrganizationId(pumpMeterReading.getOrganization() != null ? pumpMeterReading.getOrganization().getId() : null);
        dto.setFacilityId(pumpMeterReading.getFacility() != null ? pumpMeterReading.getFacility().getId() : null);
        dto.setSellPointId(pumpMeterReading.getSellPoint() != null ? pumpMeterReading.getSellPoint().getId() : null);
        dto.setShiftId(pumpMeterReading.getShift() != null ? pumpMeterReading.getShift().getId() : null);
        dto.setStartReading(pumpMeterReading.getStartReading());
        dto.setEndReading(pumpMeterReading.getEndReading());
        dto.setReadingDate(pumpMeterReading.getReadingDate());
        dto.setCreatedAt(pumpMeterReading.getCreatedAt());
        dto.setUpdatedAt(pumpMeterReading.getUpdatedAt());
        dto.setCreatedBy(pumpMeterReading.getCreatedBy());
        dto.setUpdatedBy(pumpMeterReading.getUpdatedBy());
        dto.setStatus(pumpMeterReading.getStatus());
        dto.setTotalVolume(pumpMeterReading.getTotalVolume());

        return dto;
    }

    public PumpMeterReading toEntity(PumpMeterReadingDto dto) {
        if (dto == null) {
            return null;
        }

        PumpMeterReading entity = new PumpMeterReading();
        entity.setId(dto.getId());
        entity.setStartReading(dto.getStartReading());
        entity.setEndReading(dto.getEndReading());
        entity.setReadingDate(dto.getReadingDate());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setUpdatedAt(dto.getUpdatedAt());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setStatus(dto.getStatus());
        entity.setTotalVolume(dto.getTotalVolume());

        // Set relationships
        if (dto.getOrganizationId() != null) {
            Organization organization = new Organization();
            organization.setId(dto.getOrganizationId());
            entity.setOrganization(organization);
        }

        if (dto.getFacilityId() != null) {
            Facility facility = new Facility();
            facility.setId(dto.getFacilityId());
            entity.setFacility(facility);
        }

        if (dto.getSellPointId() != null) {
            SellPoint sellPoint = new SellPoint();
            sellPoint.setId(dto.getSellPointId());
            entity.setSellPoint(sellPoint);
        }

        if (dto.getShiftId() != null) {
            Shift shift = new Shift();
            shift.setId(dto.getShiftId());
            entity.setShift(shift);
        }

        return entity;
    }
}