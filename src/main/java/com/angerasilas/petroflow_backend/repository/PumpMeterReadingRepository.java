package com.angerasilas.petroflow_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.MeterReadingDto;
import com.angerasilas.petroflow_backend.entity.PumpMeterReading;

@Repository
public interface PumpMeterReadingRepository extends JpaRepository<PumpMeterReading, Long> {
    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "ORDER BY pmr.createdAt DESC")
    List<MeterReadingDto> findMeterReading();

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "WHERE pmr.id = :id")
    MeterReadingDto findMeterReadingById(@Param("id") Long id);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "WHERE pmr.organization.id = :orgId")
    List<MeterReadingDto> findMeterReadingByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "WHERE pmr.facility.id = :facilityId")
    List<MeterReadingDto> findMeterReadingByFacilityId(@Param("facilityId") Long facilityId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "WHERE pmr.sellPoint.id = :sellPointId")
    List<MeterReadingDto> findMeterReadingBySellPointId(@Param("sellPointId") Long sellPointId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.MeterReadingDto(" +
           "pmr.id, org.name, fac.name, sp.id, sp.name, sh.id, sh.type, " +
           "pmr.startReading, pmr.endReading, pmr.readingDate, pmr.createdAt, pmr.updatedAt, " +
           "pmr.createdBy, pmr.updatedBy, pmr.status, (pmr.endReading - pmr.startReading)) " +
           "FROM PumpMeterReading pmr " +
           "JOIN pmr.organization org " +
           "JOIN pmr.facility fac " +
           "JOIN pmr.sellPoint sp " +
           "JOIN pmr.shift sh " +
           "WHERE pmr.shift.id = :shiftId")
    List<MeterReadingDto> findMeterReadingByShiftId(@Param("shiftId") Long shiftId);


}