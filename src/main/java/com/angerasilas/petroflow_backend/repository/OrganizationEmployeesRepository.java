package com.angerasilas.petroflow_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;
import com.angerasilas.petroflow_backend.dto.AvailableRolesDto;
import com.angerasilas.petroflow_backend.dto.DepartmentDto;
import com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto;
import com.angerasilas.petroflow_backend.dto.UserInfoDto;

@Repository
public interface OrganizationEmployeesRepository extends JpaRepository<OrganizationEmployees, OrganizationEmployeeId> {

        List<OrganizationEmployees> findByIdOrganizationIdAndIdFacilityId(Long organizationId, Long facilityId);

        //find by employeeNo
        Optional<OrganizationEmployees> findByEmployeeNo(String employeeNo);

        /**
         * Get employees at the facility level.
         * Retrieves employees working in a specific facility within an organization.
         */
        @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto( " +
                        "ud.firstname, ud.lastname, u.role, oe.department, e.jobTitle, e.employmentType, " +
                        "ud.email, ud.phoneNumber, null, null, oe.employeeNo, ud.city, ud.postalCode, " +
                        "ud.physicalAddress, null, ud.dateOfBirth, e.hireDate, oe.employmentStatus) " +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.employee e " +
                        "JOIN e.user u " +
                        "JOIN u.userDetails ud " +
                        "JOIN oe.organization o " +
                        "JOIN oe.facility f " +
                        "WHERE o.id = :organizationId AND f.id = :facilityId")
        List<EmployeeDetailsDto> findEmployeesByOrganizationIdAndFacilityId(
                        @Param("organizationId") Long organizationId,
                        @Param("facilityId") Long facilityId);

        /**
         * Get employees at the organization level.
         * Retrieves all employees in a given organization, including their facility
         * details if available.
         */
        @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto( " +
                        "ud.firstname, ud.lastname, u.role, oe.department, e.jobTitle, e.employmentType, " +
                        "ud.email, ud.phoneNumber, null, COALESCE(f.name, 'No Facility'), " +
                        "oe.employeeNo, ud.city, ud.postalCode, ud.physicalAddress, null, ud.dateOfBirth, " +
                        "e.hireDate, oe.employmentStatus )" +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.employee e " +
                        "JOIN e.user u " +
                        "JOIN u.userDetails ud " +
                        "JOIN oe.organization o " +
                        "LEFT JOIN oe.facility f " +
                        "WHERE o.id = :organizationId")
        List<EmployeeDetailsDto> findEmployeesWithOrganizationAndFacilityNames(
                        @Param("organizationId") Long organizationId);

        /**
         * Get all employees across all organizations.
         * Retrieves employees from all organizations, including facility details if
         * available.
         */
        @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeDetailsDto( " +
                        "ud.firstname, ud.lastname, u.role, oe.department, e.jobTitle, e.employmentType, " +
                        "ud.email, ud.phoneNumber, o.name, COALESCE(f.name, 'No Facility'), " +
                        "oe.employeeNo, ud.city, ud.postalCode, ud.physicalAddress, null, ud.dateOfBirth, " +
                        "e.hireDate, oe.employmentStatus )" +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.employee e " +
                        "JOIN e.user u " +
                        "JOIN u.userDetails ud " +
                        "JOIN oe.organization o " +
                        "LEFT JOIN oe.facility f")
        List<EmployeeDetailsDto> findEmployeesWithOrganization();

        /**
         * Get distinct user roles available in each organization.
         */
        @Query("SELECT DISTINCT new com.angerasilas.petroflow_backend.dto.AvailableRolesDto(u.role) " +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.employee e " +
                        "JOIN e.user u " +
                        "JOIN oe.organization o " +
                        "WHERE o.id = :organizationId")
        List<AvailableRolesDto> findDistinctRolesByOrganizationId(@Param("organizationId") Long organizationId);

        

        /**
         * Get distinct user roles available in each facility.
         */
        @Query("SELECT DISTINCT new com.angerasilas.petroflow_backend.dto.AvailableRolesDto(u.role) " +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.employee e " +
                        "JOIN e.user u " +
                        "JOIN oe.organization o " +
                        "JOIN oe.facility f " +
                        "WHERE o.id = :organizationId AND f.id = :facilityId")
        List<AvailableRolesDto> findDistinctRolesByOrganizationIdAndFacilityId(
                        @Param("organizationId") Long organizationId,
                        @Param("facilityId") Long facilityId);

        /**
         * Get employees by role in an organization.
         */
        @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserInfoDto( " +
                        "null, null, null, " +
                        "COALESCE(f.name, 'No Facility'), oe.employeeNo, " +
                        "ud.firstname, ud.lastname, oe.department, e.jobTitle, " +
                        "e.employmentType, ud.email, ud.phoneNumber, o.name, ud.city, " +
                        "ud.postalCode, ud.physicalAddress, " +
                        "ud.dateOfBirth, null, u.id) " +
                        "FROM User u " +
                        "LEFT JOIN u.employees e " +
                        "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
                        "LEFT JOIN u.userDetails ud " +
                        "LEFT JOIN oe.organization o " +
                        "LEFT JOIN oe.facility f " +
                        "WHERE o.id = :organizationId AND u.role = :role")
        List<UserInfoDto> findEmployeesByOrganizationIdAndRole(
                        @Param("organizationId") Long organizationId,
                        @Param("role") String role);

        /**
         * Get employees by role in a facility.
         */
        @Query("SELECT new com.angerasilas.petroflow_backend.dto.UserInfoDto( " +
                        "null, null, null, " +
                        "COALESCE(f.name, 'No Facility'), oe.employeeNo, " +
                        "ud.firstname, ud.lastname, oe.department, e.jobTitle, " +
                        "e.employmentType, ud.email, ud.phoneNumber, o.name, ud.city, " +
                        "ud.postalCode, ud.physicalAddress, " +
                        "ud.dateOfBirth, null, u.id) " +
                        "FROM User u " +
                        "LEFT JOIN u.employees e " +
                        "LEFT JOIN OrganizationEmployees oe ON oe.employee = e " +
                        "LEFT JOIN u.userDetails ud " +
                        "LEFT JOIN oe.organization o " +
                        "LEFT JOIN oe.facility f " +
                        "WHERE o.id = :organizationId AND f.id = :facilityId AND u.role = :role")
        List<UserInfoDto> findEmployeesByOrganizationIdAndFacilityIdAndRole(
                        @Param("organizationId") Long organizationId,
                        @Param("facilityId") Long facilityId,
                        @Param("role") String role);


        /**
         * Get distinct departments in each organization
         * */

         @Query("SELECT DISTINCT new com.angerasilas.petroflow_backend.dto.DepartmentDto(o.id, f.id, oe.department) " +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.organization o " +
                        "JOIN oe.facility f " +
                        "WHERE o.id = :organizationId")
        List<DepartmentDto> findDepartmentsByOrganizationId(@Param("organizationId") Long organizationId);


        /**
         * Get distinct departments in each facility
         * */

         @Query("SELECT DISTINCT new com.angerasilas.petroflow_backend.dto.DepartmentDto(o.id, f.id, oe.department) " +
                        "FROM OrganizationEmployees oe " +
                        "JOIN oe.organization o " +
                        "JOIN oe.facility f " +
                        "WHERE o.id = :organizationId AND f.id = :facilityId")
        List<DepartmentDto> findDepartmentsByOrganizationIdAndFacilityId(
                        @Param("organizationId") Long organizationId,
                        @Param("facilityId") Long facilityId);
}