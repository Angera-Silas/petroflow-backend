package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.OrganizationFacility;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationFacilityRepository extends JpaRepository<OrganizationFacility, OrganizationFacilityId> {
    // You can add custom queries here if needed

    //save multiple facilities
    
}
