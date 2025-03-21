package com.angerasilas.petroflow_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                        "COALESCE(oe.department, 'No Department'), NULL ) " + // No permissions in JPQL
                        "FROM User u " +
                        "JOIN u.userDetails ud " +
                        "LEFT JOIN u.employees e " +
                        "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
                        "LEFT JOIN oe.organization o " +
                        "LEFT JOIN oe.facility f " +
                        "GROUP BY u.id, o.id, f.id, u.roleEntity.name, u.username, ud.firstname, ud.lastname, f.name, o.name, oe.department")
        Page<UserPermissionsDto> findUsersWithoutPermissions(Pageable pageable);

        @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserPermissionsDto( " +
       "u.id, o.id, f.id, u.roleEntity.name, u.username, ud.firstname, ud.lastname, " +
       "COALESCE(f.name, 'No Facility'), COALESCE(o.name, 'No Organization'), " +
       "COALESCE(oe.department, 'No Department'), NULL ) " +  // No permissions in JPQL
       "FROM User u " +
       "JOIN u.userDetails ud " +
       "LEFT JOIN u.employees e " +
       "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
       "LEFT JOIN oe.organization o " +
       "LEFT JOIN oe.facility f " +
       "WHERE u.id = :userId " +
       "GROUP BY u.id, o.id, f.id, u.roleEntity.name, u.username, ud.firstname, ud.lastname, f.name, o.name, oe.department")
        Optional<UserPermissionsDto> findUserPermissionsByUserId(@Param("userId") Long userId);
}
