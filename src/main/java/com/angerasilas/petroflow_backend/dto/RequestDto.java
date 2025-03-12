package com.angerasilas.petroflow_backend.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long id;
    private String employeeNo;
    private String title;
    private String description;
    private Date dateRequested;
    private String status;
    private Date dateResolved;
    private String resolvedBy;
}