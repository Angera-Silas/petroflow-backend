package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_no", nullable = false, referencedColumnName="employeeNo")
    private OrganizationEmployees employee;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date_requested")
    private Date dateRequested;

    @Column(name = "status")
    private String status;

    @Column(name = "date_resolved")
    private Date dateResolved;
}