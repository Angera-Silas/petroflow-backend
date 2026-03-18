package com.angerasilas.petroflow_backend.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Long id;
    private String name;
    private Set<String> permissions;

    public RoleDTO(String name, Set<String> permissions) {
        this.name = name;
        this.permissions = permissions;
    }
    
}
