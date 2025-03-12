package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.Incident;


@Repository
public interface IncidentRepository extends JpaRepository<Incident, Long> {
}