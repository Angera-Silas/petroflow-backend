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
public class SalesDto {
    private Long id;
    private Date dateTime;
    private Long productId;
    private String employeeNo;
    private String unitsSold;
    private double amountBilled;
    private double discount;
    private double amountPaid;
    private String paymentMethod;
    private String paymentStatus;
    private double balance;
    private String status;
}