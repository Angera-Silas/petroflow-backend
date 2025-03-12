package com.angerasilas.petroflow_backend.entity;

import lombok.*;
import java.util.Date;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Employees employee;

    @Column(name = "notification")
    private String notification;

    @Column(name = "date_sent")
    private Date dateSent;

    @Column(name = "status")
    private String status;
}