package com.angerasilas.petroflow_backend.dto;

import lombok.Data;
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
@Data
public class StockDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateStocked;
    
    private Long productId;
    private Long orgId;
    private Long facilityId;
    private double unitsAvailable;
    private double unitsSold;
    private double unitsBought;
    private double unitsReturned;
    private double unitsDamaged;
    private double unitsLost;
    private double buyingPricePerUnit;
    private double sellingPricePerUnit;
}