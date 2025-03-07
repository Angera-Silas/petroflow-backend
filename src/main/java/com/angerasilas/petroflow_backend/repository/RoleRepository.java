package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.dto.RolePermissionsDto;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.RolePermissionsDto( " +
            "r.id, r.name, p) " +
            "FROM RoleEntity r " +
            "JOIN r.permissions p " +
            "WHERE r.id = :roleId")
    Optional<RolePermissionsDto> findRolePermissionsByRoleId(@Param("roleId") Long roleId);
}