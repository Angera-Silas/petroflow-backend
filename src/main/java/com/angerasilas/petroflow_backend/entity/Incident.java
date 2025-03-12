package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Data
@Entity
@Table(name = "incidents")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_no", nullable = false, insertable = false, updatable = false, referencedColumnName="employeeNo")
    private OrganizationEmployees organizationEmployees;

    @Column(name = "title")
    private String title;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "description")
    private String description;

    @Column(name = "date_reported")
    private Date dateReported;

    @Column(name = "status")
    private String status;

    @Column(name = "date_resolved")
    private Date dateResolved;
}