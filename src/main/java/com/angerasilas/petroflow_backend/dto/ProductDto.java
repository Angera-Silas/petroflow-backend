package com.angerasilas.petroflow_backend.dto;

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
public class ProductDto {
    private Long id;
    private String dateAdded;
    private String productName;
    private String productDescription;
    private String productCategory;
    private String productSubCategory;
    private Long orgId;
    private Long facilityId;
    private String department;
}