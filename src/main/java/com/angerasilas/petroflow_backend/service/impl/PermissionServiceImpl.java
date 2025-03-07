package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.angerasilas.petroflow_backend.dto.PermissionDTO;
import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.exception.ResourceNotFoundException;
import com.angerasilas.petroflow_backend.mapper.PermissionMapper;
import com.angerasilas.petroflow_backend.repository.PermissionRepository;
import com.angerasilas.petroflow_backend.service.PermissionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    @Override
    @Transactional
    public PermissionDTO createPermission(PermissionDTO permissionDTO) {
        PermissionEntity permissionEntity = PermissionMapper.toEntity(permissionDTO);
        PermissionEntity savedPermission = permissionRepository.save(permissionEntity);
        return PermissionMapper.toDTO(savedPermission);
    }

    @Override
    public List<PermissionDTO> getAllPermissions() {
        List<PermissionEntity> permissions = permissionRepository.findAll();
        return permissions.stream().map(PermissionMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));
        return PermissionMapper.toDTO(permission);
    }

    @Override
    @Transactional
    public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));

        permission.setName(permissionDTO.getName());

        PermissionEntity updatedPermission = permissionRepository.save(permission);
        return PermissionMapper.toDTO(updatedPermission);
    }

    @Override
    public void deletePermission(Long id) {
        PermissionEntity permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id " + id));
        permissionRepository.delete(permission);
    }
}