package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.dto.OrganizationDetailsDto;
import com.angerasilas.petroflow_backend.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.OrganizationDetailsDto( " +
           "o.name, o.physicalAddress, o.town, o.street, o.postalCode, o.orgType, o.email, o.phone, " +
           "COUNT(DISTINCT ofac.facility.id), COUNT(DISTINCT oemp.employee.id)) " +
           "FROM Organization o " +
           "LEFT JOIN o.organizationFacilities ofac " +
           "LEFT JOIN o.organizationEmployees oemp " +
           "GROUP BY o.id")
    List<OrganizationDetailsDto> getAllOrganizationsWithCounts();
}