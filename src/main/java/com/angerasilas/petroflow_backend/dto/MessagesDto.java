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
public class MessagesDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String message;
    private Date dateSent;
    private String status;
}