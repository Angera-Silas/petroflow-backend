package com.angerasilas.petroflow_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.Incident;


@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {

     // Find by employeeNo in the related OrganizationEmployees entity
     Optional<Incident> findByOrganizationEmployees_EmployeeNo(String employeeNo);
}