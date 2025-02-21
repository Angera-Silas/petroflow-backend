package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.FacilityDto;
import com.angerasilas.petroflow_backend.entity.Facility;

public class FacilityMapper {
    public static FacilityDto mapTOFacilityDto(Facility facility) {
        return new FacilityDto(
                facility.getId(),
                facility.getName(),
                facility.getCounty(),
                facility.getTown(),
                facility.getStreet(),
                facility.getPhysicalAddress(),
                facility.getPostalCode(),
                facility.getPhone(),
                facility.getEmail(),
                facility.getServicesOffered()
                
        );
    }

    public static Facility mapToFacility(FacilityDto facilityDto) {
        Facility facility = new Facility();
        facility.setId(facilityDto.getId());
        facility.setName(facilityDto.getFacilityName());
        facility.setCounty(facilityDto.getFacilityCounty());
        facility.setTown(facilityDto.getFacilityTown());
        facility.setStreet(facilityDto.getFacilityStreet());
        facility.setPhysicalAddress(facilityDto.getPhysicalAddress());
        facility.setPostalCode(facilityDto.getFacilityPostalCode());
        facility.setPhone(facilityDto.getFacilityPhone());
        facility.setEmail(facilityDto.getFacilityEmail());
        facility.setServicesOffered(facilityDto.getServicesOffered());
        
        return facility;
    }
}