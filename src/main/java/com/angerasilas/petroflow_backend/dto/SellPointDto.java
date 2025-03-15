package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SellPointDto {
    private Long id;
    private Long facilityId;
    private String name;
    private String description;
    private String type;
}