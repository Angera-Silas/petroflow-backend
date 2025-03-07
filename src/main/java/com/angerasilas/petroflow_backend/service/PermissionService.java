package com.angerasilas.petroflow_backend.service;

import com.angerasilas.petroflow_backend.dto.PermissionDTO;

import java.util.List;

public interface PermissionService {
    PermissionDTO createPermission(PermissionDTO permissionDTO);
    List<PermissionDTO> getAllPermissions();
    PermissionDTO getPermissionById(Long id);
    PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO);
    void deletePermission(Long id);
}