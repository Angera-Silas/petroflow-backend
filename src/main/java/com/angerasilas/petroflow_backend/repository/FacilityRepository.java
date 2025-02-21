package com.angerasilas.petroflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.FacilityDetailsDto;
import com.angerasilas.petroflow_backend.dto.FacilityDto;
import com.angerasilas.petroflow_backend.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.FacilityDto(f.id, f.name, f.county, " +
            "f.town, f.street, f.physicalAddress, f.postalCode, f.phone, " +
            "f.email, f.servicesOffered) " +
            "FROM Facility f " +
            "JOIN OrganizationFacility of ON f.id = of.facility.id " +
            "WHERE of.organization.id = :organizationId")
    List<FacilityDto> findFacilitiesByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.FacilityDetailsDto( " +
           "f.name, f.county, f.town, f.street, f.physicalAddress, f.postalCode, f.phone, f.email, f.servicesOffered, " +
           "COUNT(DISTINCT oemp.employee.id)) " +
           "FROM Facility f " +
           "LEFT JOIN f.organizationFacilities ofac " +
           "LEFT JOIN f.organizationEmployees oemp " +
           "GROUP BY f.id")
    List<FacilityDetailsDto> getAllFacilitiesWithCounts();
}