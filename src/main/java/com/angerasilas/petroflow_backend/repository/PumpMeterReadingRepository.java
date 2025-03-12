package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.PumpMeterReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PumpMeterReadingRepository extends JpaRepository<PumpMeterReading, Long> {
}