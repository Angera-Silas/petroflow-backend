package com.angerasilas.petroflow_backend.entity;

import lombok.*;
import java.util.List;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="date_added")
    private String dateAdded;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_sub_category")
    private String productSubCategory;

    @ManyToOne
    @JoinColumn(name = "org_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Facility facility;


    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "product")
    private List<Stock> stocks;

    @OneToMany(mappedBy = "product")
    private List<Sales> sales;
}