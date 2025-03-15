package com.angerasilas.petroflow_backend.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShiftDto {
    private Long id;
    private Long facilityId;
    private String employeeNo;
    private Date startDate;
    private Date endDate;
    private String type;
}