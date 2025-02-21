package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.OrganizationOwner;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationOwnersId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationOwnerRepository extends JpaRepository<OrganizationOwner, OrganizationOwnersId> {
    // You can add custom queries here if needed

    
}
