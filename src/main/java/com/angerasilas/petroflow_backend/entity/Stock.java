package com.angerasilas.petroflow_backend.entity;

import lombok.*;
import java.util.Date;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_stocked")
    private Date dateStocked;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Facility facility;

    @Column(name = "units_available")
    private double unitsAvailable;

    @Column(name = "units_sold")
    private double unitsSold;

    @Column(name = "units_bought")
    private double unitsBought;

    @Column(name = "units_returned")
    private double unitsReturned;

    @Column(name = "units_damaged")
    private double unitsDamaged;

    @Column(name = "units_lost")
    private double unitsLost;

    @Column(name = "buying_price_per_unit")
    private double buyingPricePerUnit;

    @Column(name = "selling_price_per_unit")
    private double sellingPricePerUnit;
}