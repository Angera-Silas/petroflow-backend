package com.angerasilas.petroflow_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.angerasilas.petroflow_backend.dto.RoleDTO;
import com.angerasilas.petroflow_backend.dto.RolePermissionsDto;

public interface RoleService {
    RoleDTO createRole(RoleDTO roleDTO);
    List<RoleDTO> getAllRoles();
    RoleDTO getRoleById(Long id);
    RoleDTO updateRole(Long id, RoleDTO roleDTO);
    void deleteRole(Long id);

    Optional<RolePermissionsDto> getRolePermissionsByRoleId(Long roleId);

    Page<RolePermissionsDto> getRolePermissions(Pageable pageable);
}