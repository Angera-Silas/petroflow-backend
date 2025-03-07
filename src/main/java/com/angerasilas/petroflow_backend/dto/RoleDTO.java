package com.angerasilas.petroflow_backend.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private String name;
    private Set<String> permissions;
    
}
