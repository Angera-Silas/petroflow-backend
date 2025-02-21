package com.angerasilas.petroflow_backend.dto;


import com.angerasilas.petroflow_backend.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private boolean isActive;
}
