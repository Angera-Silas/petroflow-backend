package com.angerasilas.petroflow_backend.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date readingDate;

    private String createdAt;

    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String status;
    private double totalVolume;

    // Constructor for updates
    public PumpMeterReadingDto(Long id, double endReading, String updatedBy, String updatedAt, String status,
            double totalVolume) {
        this.id = id;
        this.endReading = endReading;
        this.updatedBy = updatedBy;
        this.updatedAt = updatedAt;
        this.status = status;
        this.totalVolume = totalVolume;
    }

}