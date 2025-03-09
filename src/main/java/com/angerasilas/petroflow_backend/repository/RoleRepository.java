package com.angerasilas.petroflow_backend.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.RolePermissionsDto;
import com.angerasilas.petroflow_backend.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.RolePermissionsDto( " +
            "r.id, r.name) " +
            "FROM RoleEntity r "
            )
    Page<RolePermissionsDto> findRolePermissions(Pageable pagable);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.RolePermissionsDto( " +
            "r.id, r.name) " +
            "FROM RoleEntity r "+
            "WHERE r.id = :roleId")
    Optional<RolePermissionsDto> findRolePermissionsByRoleId(@Param("roleId") Long roleId);
}