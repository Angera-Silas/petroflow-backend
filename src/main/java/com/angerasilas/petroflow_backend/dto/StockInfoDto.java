package com.angerasilas.petroflow_backend.dto;

import java.util.Date;

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
public class StockInfoDto {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateStocked;
    
    private Long productId;
    private String productName;
    private Long orgId;
    private String orgName;
    private Long facilityId;
    private String facilityName;
    private double unitsAvailable;
    private double unitsSold;
    private double unitsBought;
    private double unitsReturned;
    private double unitsDamaged;
    private double unitsLost;
    private double buyingPricePerUnit;
    private double sellingPricePerUnit;
}
