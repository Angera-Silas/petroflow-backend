package com.angerasilas.petroflow_backend.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PumpMeterReadingDto {
    private Long id;
    private Long organizationId;
    private Long facilityId;
    private Long sellPointId;
    private Long shiftId;
    private double startReading;
    private double endReading;
    private LocalDateTime readingDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private String status;
    private double totalVolume;
}