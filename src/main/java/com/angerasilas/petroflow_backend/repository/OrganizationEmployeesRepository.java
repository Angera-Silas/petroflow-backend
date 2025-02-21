package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;

@Repository
public interface OrganizationEmployeesRepository extends JpaRepository<OrganizationEmployees, OrganizationEmployeeId> {
    // You can add custom queries here if needed

    //save multiple employees
    
}
