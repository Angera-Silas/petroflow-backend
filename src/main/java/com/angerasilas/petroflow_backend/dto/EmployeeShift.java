package com.angerasilas.petroflow_backend.dto;


import java.util.Date;

import com.angerasilas.petroflow_backend.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeShift {
    private Long shiftId;
    private Long facilityId;
    private String employeeNo;
    private String employeeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
    
    private Shift.ShiftType type;
    private String sellingPoints;
}
