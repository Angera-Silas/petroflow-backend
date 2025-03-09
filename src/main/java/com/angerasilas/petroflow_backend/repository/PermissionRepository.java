package com.angerasilas.petroflow_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.PermissionEntity;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {
    Optional<PermissionEntity> findByName(String name);
    @Query("SELECT p.name FROM PermissionEntity p JOIN p.users u WHERE u.id = :userId")
    List<String> findPermissionsByUserId(@Param("userId") Long userId);

    @Query("SELECT p.name FROM PermissionEntity p JOIN p.roles r WHERE r.id = :roleId")
    List<String> findPermissionsByRoleId(@Param("roleId") Long roleId);

}
