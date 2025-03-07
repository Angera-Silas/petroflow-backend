package com.angerasilas.petroflow_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;
import com.angerasilas.petroflow_backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByUsernameIn(List<String> usernames);


    @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserPermissionsDto( " +
            "u.id, o.id, f.id, u.roleEntity.name, u.username, ud.firstname, ud.lastname, " +
            "COALESCE(f.name, 'No Facility'), COALESCE(o.name, 'No Organization'), " +
            "COALESCE(oe.department, 'No Department'), p) " +
            "FROM User u " +
            "JOIN u.userDetails ud " +
            "LEFT JOIN u.employees e " +
            "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
            "LEFT JOIN oe.organization o " +
            "LEFT JOIN oe.facility f " +
            "JOIN u.permissions p")
    List<UserPermissionsDto> findAllUserPermissions();

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserPermissionsDto( " +
            "u.id, o.id, f.id, u.roleEntity.name, u.username, ud.firstname, ud.lastname, " +
            "COALESCE(f.name, 'No Facility'), COALESCE(o.name, 'No Organization'), " +
            "COALESCE(oe.department, 'No Department'), p) " +
            "FROM User u " +
            "JOIN u.userDetails ud " +
            "LEFT JOIN u.employees e " +
            "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
            "LEFT JOIN oe.organization o " +
            "LEFT JOIN oe.facility f " +
            "JOIN u.permissions p " +
            "WHERE u.id = :userId")
    Optional<UserPermissionsDto> findUserPermissionsByUserId(@Param("userId") Long userId);
}


