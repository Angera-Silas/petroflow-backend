package com.angerasilas.petroflow_backend.dto;

import java.util.Set;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String role; // Role will be sent from frontend as a string
    private boolean isActive;
    private Set<String> permissions;
}