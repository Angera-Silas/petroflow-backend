package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {
    private Long id;
    private Date dateTime;
    private Long productId;
    private String employeeNo;
    private Long sellPointId;
    private Long shiftId;
    private String unitsSold;
    private double amountBilled;
    private double discount;
    private double amountPaid;
    private String paymentMethod;
    private String paymentStatus;
    private double balance;
    private String status;
}