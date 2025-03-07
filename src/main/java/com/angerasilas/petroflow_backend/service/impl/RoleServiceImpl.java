package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angerasilas.petroflow_backend.dto.RoleDTO;
import com.angerasilas.petroflow_backend.dto.RolePermissionsDto;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.RoleMapper;
import com.angerasilas.petroflow_backend.repository.PermissionRepository;
import com.angerasilas.petroflow_backend.repository.RoleRepository;
import com.angerasilas.petroflow_backend.service.RoleService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public RoleDTO createRole(RoleDTO roleDTO) {
        Set<PermissionEntity> permissions = roleDTO.getPermissions().stream()
                .map(permissionRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        RoleEntity roleEntity = RoleMapper.toEntity(roleDTO, permissions);
        RoleEntity savedRole = roleRepository.save(roleEntity);

        return RoleMapper.toDTO(savedRole);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return roles.stream().map(RoleMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        return RoleMapper.toDTO(role);
    }

    @Override
    @Transactional
    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        Set<PermissionEntity> permissions = roleDTO.getPermissions().stream()
                .map(permissionRepository::findByName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        role.setName(roleDTO.getName());
        role.setPermissions(permissions);

        RoleEntity updatedRole = roleRepository.save(role);

        return RoleMapper.toDTO(updatedRole);
    }

    @Override
    public void deleteRole(Long id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    @Override
    public Optional<RolePermissionsDto> getRolePermissionsByRoleId(Long roleId) {
        return roleRepository.findRolePermissionsByRoleId(roleId);
    }
}