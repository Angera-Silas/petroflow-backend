package com.angerasilas.petroflow_backend.mapper;

import com.angerasilas.petroflow_backend.dto.PermissionDTO;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;

public class PermissionMapper {
    public static PermissionDTO toDTO(PermissionEntity permissionEntity) {
        return new PermissionDTO(
            permissionEntity.getId(),
            permissionEntity.getName()
        );
    }

    public static PermissionEntity toEntity(PermissionDTO permissionDTO) {
        return new PermissionEntity(
            permissionDTO.getId(),
            permissionDTO.getName()
        );
    }
}