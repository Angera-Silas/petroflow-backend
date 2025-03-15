package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shifts")
public class Shift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "employee_no", nullable = false, referencedColumnName="employeeNo")
    private OrganizationEmployees employee;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;


    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ShiftType type;

    public enum ShiftType {
        DAY_SHIFT,
        NIGHT_SHIFT,
        A24_HOURS_SHIFT
    }

    @OneToMany(mappedBy="shift")
    private Set<PumpMeterReading> pumpMeterReading;

    @OneToMany(mappedBy="shift")
    private Set<Sales> sales;

}