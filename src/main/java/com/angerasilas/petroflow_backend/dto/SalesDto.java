package com.angerasilas.petroflow_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
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