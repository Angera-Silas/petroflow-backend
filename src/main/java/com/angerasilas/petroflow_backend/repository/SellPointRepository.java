package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.SellPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellPointRepository extends JpaRepository<SellPoint, Long> {
}