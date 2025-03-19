package com.angerasilas.petroflow_backend.dto;

import java.time.LocalDateTime;

import com.angerasilas.petroflow_backend.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MeterReadingDto {
    private Long id;
    private String organizationName;
    private String facilityName;
    private Long sellPointId;
    private String sellPointName;
    private Long shiftId;
    private Shift.ShiftType shiftType;
    private double startReading;
    private double endReading;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime readingDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private String createdBy;
    private String updatedBy;
    private String status;
    private double totalVolume;

    
}