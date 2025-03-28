package com.angerasilas.petroflow_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.angerasilas.petroflow_backend.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
     // Find by employeeNo in the related OrganizationEmployees entity
     Optional<Request> findByEmployee_EmployeeNo(String employeeNo);
}