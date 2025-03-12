package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.SellPointDto;
import com.angerasilas.petroflow_backend.entity.SellPoint;
import org.springframework.stereotype.Component;

@Component
public class SellPointMapper {

    public SellPointDto toDto(SellPoint sellPoint) {
        if (sellPoint == null) {
            return null;
        }

        SellPointDto dto = new SellPointDto();
        dto.setId(sellPoint.getId());
        dto.setName(sellPoint.getName());
        dto.setDescription(sellPoint.getDescription());
        dto.setType(sellPoint.getType().name());

        return dto;
    }

    public SellPoint toEntity(SellPointDto dto) {
        if (dto == null) {
            return null;
        }

        SellPoint entity = new SellPoint();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setType(SellPoint.SellPointType.valueOf(dto.getType()));

        return entity;
    }
}