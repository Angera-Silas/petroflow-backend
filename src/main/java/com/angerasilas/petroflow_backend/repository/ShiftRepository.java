package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.dto.EmployeeShift;
import com.angerasilas.petroflow_backend.entity.Shift;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {

    List<Shift> findByEmployee_EmployeeNo(String employeeNo);

    List<Shift> findByFacility_Id(Long facilityId);

    List<Shift> findByType(Shift.ShiftType type);

    List<Shift> findByEmployee_EmployeeNoAndFacility_Id(String employeeNo, Long facilityId);

    List<Shift> findByFacility_IdAndType(Long facilityId, Shift.ShiftType type);

    List<Shift> findByFacility_IdAndTypeAndEmployee_EmployeeNo(Long facilityId, Shift.ShiftType type,
            String employeeNo);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeShift(s.id, f.id, oe.employeeNo, CONCAT(ud.firstname, ' ', ud.lastname), s.startDate, s.endDate, s.type, s.sellingPoints) " +
           "FROM Shift s " +
           "JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo " +
           "JOIN oe.employee e " +
           "JOIN e.user u " +
           "JOIN u.userDetails ud " +
           "JOIN oe.facility f")
    List<EmployeeShift> findAllEmployeeShifts();


    @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeShift(s.id, f.id, oe.employeeNo, CONCAT(ud.firstname, ' ', ud.lastname), s.startDate, s.endDate, s.type, s.sellingPoints) " +
           "FROM Shift s " +
           "JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo " +
           "JOIN oe.employee e " +
           "JOIN e.user u " +
           "JOIN u.userDetails ud " +
           "JOIN oe.facility f " +
           "JOIN oe.organization o "+
           "WHERE o.id = :orgId")
    List<EmployeeShift> findEmployeeShiftsByOrgId(@Param("orgId") Long orgId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeShift(s.id, f.id, oe.employeeNo, CONCAT(ud.firstname, ' ', ud.lastname), s.startDate, s.endDate, s.type, s.sellingPoints) " +
           "FROM Shift s " +
           "JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo " +
           "JOIN oe.employee e " +
           "JOIN e.user u " +
           "JOIN u.userDetails ud " +
           "JOIN oe.facility f " +
           "WHERE f.id = :facilityId")
    List<EmployeeShift> findEmployeeShiftsByFacilityId(@Param("facilityId") Long facilityId);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeShift(s.id, f.id, oe.employeeNo, CONCAT(ud.firstname, ' ', ud.lastname), s.startDate, s.endDate, s.type, s.sellingPoints) " +
           "FROM Shift s " +
           "JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo " +
           "JOIN oe.employee e " +
           "JOIN e.user u " +
           "JOIN u.userDetails ud " +
           "JOIN oe.facility f " +
           "WHERE s.type = :type")
    List<EmployeeShift> findEmployeeShiftsByType(@Param("type") Shift.ShiftType type);

    @Query("SELECT new com.angerasilas.petroflow_backend.dto.EmployeeShift(s.id, f.id, oe.employeeNo, CONCAT(ud.firstname, ' ', ud.lastname), s.startDate, s.endDate, s.type, s.sellingPoints) " +
           "FROM Shift s " +
           "JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo " +
           "JOIN oe.employee e " +
           "JOIN e.user u " +
           "JOIN u.userDetails ud " +
           "JOIN oe.facility f " +
           "WHERE oe.employeeNo = :employeeNo")
    List<EmployeeShift> findEmployeeShiftsByEmployeeNo(@Param("employeeNo") String employeeNo);


    //get shift where enddate or time is greater than current date and time
       @Query("SELECT s FROM Shift s WHERE s.endDate > CURRENT_TIMESTAMP")
       List<Shift> findActiveShifts();

       //get shift where enddate or time is greater than current date and time and employeeNo
       @Query("SELECT s FROM Shift s WHERE s.endDate > CURRENT_TIMESTAMP AND s.employee.employeeNo = :employeeNo")
       List<Shift> findActiveShiftsByEmployeeNo(@Param("employeeNo") String employeeNo);
    
}