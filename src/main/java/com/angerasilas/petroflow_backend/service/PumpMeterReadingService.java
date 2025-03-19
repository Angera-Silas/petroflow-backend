package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.MeterReadingDto;
import com.angerasilas.petroflow_backend.dto.PumpMeterReadingDto;
import java.util.List;

public interface PumpMeterReadingService {
    PumpMeterReadingDto createPumpMeterReading(PumpMeterReadingDto pumpMeterReadingDto);
    PumpMeterReadingDto updatePumpMeterReading(Long id, PumpMeterReadingDto pumpMeterReadingDto);
    void deletePumpMeterReading(Long id);
    PumpMeterReadingDto getPumpMeterReadingById(Long id);
    List<PumpMeterReadingDto> getAllPumpMeterReadings();

    List<MeterReadingDto> getMeterReading();
    MeterReadingDto getMeterReadingById(Long id);
    List<MeterReadingDto> getMeterReadingByOrgId(Long orgId);
    List<MeterReadingDto> getMeterReadingByFacilityId(Long facilityId);
    List<MeterReadingDto> getMeterReadingBySellPointId(Long sellPointId);
    List<MeterReadingDto> getMeterReadingByShiftId(Long shiftId);
}