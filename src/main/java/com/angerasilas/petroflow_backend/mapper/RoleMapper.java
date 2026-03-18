package com.angerasilas.petroflow_backend.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import com.angerasilas.petroflow_backend.dto.RoleDTO;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.entity.RoleEntity;

public class RoleMapper {
    public static RoleDTO toDTO(RoleEntity roleEntity) {
        return new RoleDTO(
            roleEntity.getName(),
            roleEntity.getPermissions().stream().map(PermissionEntity::getName).collect(Collectors.toSet())
        );
    }

    public static RoleDTO mapToDTO(RoleEntity roleEntity) {
        return new RoleDTO(
            roleEntity.getId(),
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

    public static RoleEntity mapToEntity(RoleDTO roleDTO, Set<PermissionEntity> permissions) {
        return new RoleEntity(
            roleDTO.getId(),
            roleDTO.getName(), // Accept role as a string
            permissions,
            null
        );
    }
}