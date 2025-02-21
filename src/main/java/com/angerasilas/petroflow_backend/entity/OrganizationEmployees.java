package com.angerasilas.petroflow_backend.entity;

import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationEmployeeId;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organization_employees")
public class OrganizationEmployees {

    @EmbeddedId
    private OrganizationEmployeeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", insertable = false, updatable = false, referencedColumnName = "id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeId", insertable = false, updatable = false, referencedColumnName = "id")
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facilityId", insertable = false, updatable = false, referencedColumnName = "id")
    private Facility facility;

    @Column(nullable = true)
    private String shift;

    @Column(nullable = true)
    private String department;

     @Column(nullable = false, unique = true)
    private String employeeNo; // Now unique per organization

    @Column(nullable = false)
    private String employmentStatus; // ACTIVE, TRANSFERRED, RESIGNED


    @Column(nullable = true)
    private String transferDate;
}