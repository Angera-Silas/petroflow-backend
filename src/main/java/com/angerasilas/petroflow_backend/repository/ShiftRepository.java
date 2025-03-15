package com.angerasilas.petroflow_backend.repository;

import com.angerasilas.petroflow_backend.entity.Shift;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, Long> {
    
    List<Shift> findByEmployee_EmployeeNo(String employeeNo);

    List<Shift> findByFacility_Id(Long facilityId); 

    List<Shift> findByType(Shift.ShiftType type);

    List<Shift> findByEmployee_EmployeeNoAndFacility_Id(String employeeNo, Long facilityId); 

    List<Shift> findByFacility_IdAndType(Long facilityId, Shift.ShiftType type); 

    List<Shift> findByFacility_IdAndTypeAndEmployee_EmployeeNo(Long facilityId, Shift.ShiftType type, String employeeNo); 
}
