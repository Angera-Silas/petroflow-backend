package com.angerasilas.petroflow_backend.dto;

import lombok.Data;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IncidentDto {
    private Long id;
    private String employeeNo;
    private String title;
    private String receiver;
    private String description;
    private Date dateReported;
    private String status;
    private Date dateResolved;
}