package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.entity.Facility;
import com.angerasilas.petroflow_backend.entity.SellPoint;

@Component
public class SellPointMapper {

    public static SellPointDto mapToSellPointDto(SellPoint sellPoint) {
        if (sellPoint.getFacility() == null) {
            throw new IllegalArgumentException("Facility cannot be null");
        }

        return new SellPointDto(
            sellPoint.getId(),
            sellPoint.getFacility().getId(),
            sellPoint.getName(),
            sellPoint.getDescription(),
            sellPoint.getType().name()
        );
    }

    public static SellPoint mapToSellPoint(SellPointDto sellPointDto, Facility facility) {
        if (facility == null) {
            throw new IllegalArgumentException("Facility cannot be null");
        }

        return new SellPoint(
            sellPointDto.getId(),
            facility,
            sellPointDto.getName(),
            sellPointDto.getDescription(),
            SellPoint.SellPointType.valueOf(sellPointDto.getType()),
            null,
            null
        );
    }
}