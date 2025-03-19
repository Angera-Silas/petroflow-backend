package com.angerasilas.petroflow_backend.entity;

import java.util.Date;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "sellingPoints", nullable = false)
    private String sellingPoints;

    @OneToMany(mappedBy="shift")
    private Set<PumpMeterReading> pumpMeterReading;

    @OneToMany(mappedBy="shift")
    private Set<Sales> sales;

}