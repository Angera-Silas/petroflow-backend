package com.angerasilas.petroflow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pump_meter_readings")
public class PumpMeterReading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id" , nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false )
    private Facility facility;

    @ManyToOne
    @JoinColumn(name = "sell_point_id", nullable = false)
    private SellPoint sellPoint;

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "start_reading", nullable = false)
    private double startReading;

    @Column(name = "end_reading")
    private double endReading;

    
    @Column(name = "reading_date", nullable = false)
    private Date readingDate;

    @Column(name= "createdAt")
    private String createdAt;

    @Column(name = "updatedAt")
    private String updatedAt;


    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "status", nullable = false)
    private String status;

   @Transient
    private double totalVolume;

    @PostLoad
    public void calculateTotalVolume() {
        totalVolume = endReading - startReading;
    }
}