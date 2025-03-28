package com.angerasilas.petroflow_backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angerasilas.petroflow_backend.dto.SalesInfo;
import com.angerasilas.petroflow_backend.entity.Sales;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

    // Find organizations sales details(for the admin)
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getSalesInfo();

    //get organizational sales
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.product.organization.id = :orgId AND s.status = 'Approved' 
        ORDER BY s.dateTime DESC 
    """)
    List<SalesInfo> getOrganizationSalesInfo(@Param("orgId") Long orgId);


    //get organizzational facility sales
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.product.organization.id = :orgId 
        AND s.sellPoint.facility.id = :facilityId 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getOrganizationFacilitySalesInfo(@Param("orgId") Long orgId ,@Param("facilityId") Long facilityId);

    

    // Find facility sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.sellPoint.facility.id = :facilityId 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getFacilitySalesInfo(@Param("facilityId") Long facilityId);

    //find personal advanced
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.product.organization.id = :orgId  
        AND s.sellPoint.facility.id = :facilityId  
        AND s.employee.employeeNo = :employeeNo 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getPersonalSalesInfoByOrganization(@Param("orgId") Long orgId ,
    @Param("facilityId") Long facilityId, @Param("employeeNo") String employeeNo);


    // Find personal sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.employee.employeeNo = :employeeNo 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getPersonalSalesInfo(@Param("employeeNo") String employeeNo);

    // Find selling point sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.sellPoint.id = :sellPointId 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getSellingPointSalesInfo(@Param("sellPointId") Long sellPointId);

    // Find product sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.product.id = :productId 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getProductSalesInfo(@Param("productId") Long productId);

    // Find shift sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.shift.id = :shiftId 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getShiftSalesInfo(@Param("shiftId") Long shiftId);

    // Find sales by date
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE s.dateTime BETWEEN :startDate AND :endDate 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getSalesByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find yearly sales details
    @Query("""
        SELECT new com.angerasilas.petroflow_backend.dto.SalesInfo(
            s.id, s.dateTime, p.id, p.productName, oe.employeeNo,
            sp.id, sp.name, sh.id, sh.type,
            s.unitsSold, s.amountBilled, s.discount, s.amountPaid,
            s.paymentMethod, s.paymentStatus, (s.amountBilled - (s.amountPaid + s.discount)), s.status
        )
        FROM Sales s
        JOIN Product p ON s.product.id = p.id
        JOIN SellPoint sp ON s.sellPoint.id = sp.id
        JOIN Shift sh ON s.shift.id = sh.id
        JOIN OrganizationEmployees oe ON s.employee.employeeNo = oe.employeeNo
        WHERE FUNCTION('YEAR', s.dateTime) = :year 
        ORDER BY s.dateTime DESC
    """)
    List<SalesInfo> getYearlySalesInfo(@Param("year") int year);
}