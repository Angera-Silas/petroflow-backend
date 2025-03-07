package com.angerasilas.petroflow_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;
import com.angerasilas.petroflow_backend.dto.UserPermissionsDto;
import com.angerasilas.petroflow_backend.entity.Employees;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    /**
     * Get employees at the PERSONAL level.
     * Retrieves employee details at the personal level.
     */
    @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserInfoDto( " +
            "o.id, f.id, e.id, " +
            "COALESCE(f.name, 'No Facility'), oe.employeeNo, " +
            "ud.firstname, ud.lastname, oe.department, e.jobTitle, " +
            "e.employmentType, ud.email, ud.phoneNumber, o.name, ud.city, " +
            "ud.postalCode, ud.physicalAddress, " +
            "ud.dateOfBirth, e.hireDate, u.id ) " +
            "FROM User u " +
            "LEFT JOIN u.employees e " +
            "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
            "LEFT JOIN u.userDetails ud " +
            "LEFT JOIN oe.organization o " +
            "LEFT JOIN oe.facility f " +
            "WHERE u.id = :userId")
    UserInfoDto findUserInfoByUserId(@Param("userId") Long userId);

    /**
     * Get employees at the PERSONAL level.
     * Retrieves employee details at the personal level for update roles, etc.
     */
    @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserInfoDto( " +
            "o.id, f.id, e.id, " +
            "COALESCE(f.name, 'No Facility'), oe.employeeNo, " +
            "ud.firstname, ud.lastname, oe.department, e.jobTitle, " +
            "e.employmentType, ud.email, ud.phoneNumber, o.name, ud.city, " +
            "ud.postalCode, ud.physicalAddress, " +
            "ud.dateOfBirth, e.hireDate, u.id) " +
            "FROM User u " +
            "LEFT JOIN u.employees e " +
            "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
            "LEFT JOIN u.userDetails ud " +
            "LEFT JOIN oe.organization o " +
            "LEFT JOIN oe.facility f ")
    List<UserInfoDto> findUsersInfo();

    /**
     * Get employees at the Admin level.
     * Retrieves employee details for permissions management.
     */
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
    Page<UserPermissionsDto> findUsersPermissions(Pageable pageable);
}