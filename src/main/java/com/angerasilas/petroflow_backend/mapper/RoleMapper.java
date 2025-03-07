package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.RoleDTO;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public static RoleDTO toDTO(RoleEntity roleEntity) {
        return new RoleDTO(
            roleEntity.getName(),
            roleEntity.getPermissions().stream().map(PermissionEntity::getName).collect(Collectors.toSet())
        );
    }

    public static RoleEntity toEntity(RoleDTO roleDTO, Set<PermissionEntity> permissions) {
        return new RoleEntity(
            null,
            roleDTO.getName(), // Accept role as a string
            permissions,
            null
        );
    }
}