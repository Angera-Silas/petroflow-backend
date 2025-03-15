package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.SellPoint;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellPointRepository extends JpaRepository<SellPoint, Long> {
    Optional<SellPoint> findByName(String name);
    List<SellPoint> findByFacilityId(Long facilityId);
}