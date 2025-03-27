package com.angerasilas.petroflow_backend.dto;
import java.util.Date;

import com.angerasilas.petroflow_backend.entity.Shift;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesInfo {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateTime;

    private Long productId;
    private String productName;
    private String employeeNo;
    private Long sellPointId;
    private String sellingPoints;
    private Long shiftId;
    private Shift.ShiftType shiftType;
    private String unitsSold;
    private double amountBilled;
    private double discount;
    private double amountPaid;
    private String paymentMethod;
    private String paymentStatus;
    private double balance;
    private String status;
}
