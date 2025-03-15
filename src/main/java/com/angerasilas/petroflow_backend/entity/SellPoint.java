package com.angerasilas.petroflow_backend.entity;

import java.util.Set;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sell_points")
public class SellPoint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    private Facility facility;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private SellPointType type;

    public enum SellPointType {
        GARAGE,
        PUMP_SUPER_DIESEL_PETROL,
        GAS_STATION,
        OIL_SHOP,
        CAR_WASH,
        FOOD_AND_BEVARAGES,
        PRESSURE_REFILLING,
        OTHER
    }

    @OneToMany(mappedBy="sellPoint")
    private Set<PumpMeterReading> pumpMeterReading;
    
    @OneToMany(mappedBy="sellPoint")
    private Set<Sales> sales;
}