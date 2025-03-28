package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.OrganizationFacility;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;

@Repository
public interface OrganizationFacilityRepository extends JpaRepository<OrganizationFacility, OrganizationFacilityId> {
    
}
