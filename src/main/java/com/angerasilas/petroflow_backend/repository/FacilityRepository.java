package com.angerasilas.petroflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.FacilityDetailsDto;
import com.angerasilas.petroflow_backend.entity.Facility;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.FacilityDetailsDto( " +
            "f.name, f.county, f.town, f.street, f.physicalAddress, f.postalCode, f.phone, f.email, o.name, f.servicesOffered, " +
            "COUNT(DISTINCT oemp.employee.id)) " +
            "FROM Facility f " +
            "LEFT JOIN f.organizationFacilities ofac " +
            "LEFT JOIN f.organizationEmployees oemp " +
            "JOIN ofac.organization o " +
            "WHERE ofac.organization.id = :organizationId " +
            "GROUP BY f.id, f.name, f.county, f.town, f.street, f.physicalAddress, f.postalCode, f.phone, f.email, o.name, f.servicesOffered")
    List<FacilityDetailsDto> findFacilitiesByOrganizationId(@Param("organizationId") Long organizationId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.FacilityDetailsDto( " +
            "f.name, f.county, f.town, f.street, f.physicalAddress, f.postalCode, f.phone, f.email, o.name, f.servicesOffered, " +
            "COUNT(DISTINCT oemp.employee.id)) " +
            "FROM Facility f " +
            "LEFT JOIN f.organizationFacilities ofac " +
            "LEFT JOIN f.organizationEmployees oemp " +
            "JOIN ofac.organization o " +
            "GROUP BY f.id, f.name, f.county, f.town, f.street, f.physicalAddress, f.postalCode, f.phone, f.email, o.name, f.servicesOffered")
    List<FacilityDetailsDto> getAllFacilitiesWithCounts();
}