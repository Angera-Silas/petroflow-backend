package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
}