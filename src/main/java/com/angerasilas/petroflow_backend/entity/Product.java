package com.angerasilas.petroflow_backend.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @JoinColumn(name = "org_id", referencedColumnName = "id" , nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false )
    private Facility facility;


    @Column(name = "department")
    private String department;

    @OneToMany(mappedBy = "product")
    private Set<Stock> stocks;

    @OneToMany(mappedBy = "product")
    private Set<Sales> sales;
}