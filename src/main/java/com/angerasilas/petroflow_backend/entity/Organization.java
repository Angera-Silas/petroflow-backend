package com.angerasilas.petroflow_backend.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Organization name

    @Column(nullable = false)
    private String county; // County

    @Column(nullable = false)
    private String town; // Town

    @Column(nullable = false)
    private String street; // Street

    @Column(nullable = false)
    private String physicalAddress; // Physical Address

    @Column(nullable = false)
    private String postalCode; // Postal Code

    @Column(nullable = false)
    private String phone; // Contact Phone

    @Column(nullable = false, unique = true)
    private String email; // Organization email

    @Column(nullable = false)
    private int numberOfStations; // Total number of stations

    @Column (nullable = false)
    private String registrationDate;

    @Column (nullable = false, unique = true)
    private String lisenceNo;

    @Column(nullable = false) // Organization Type
    private String orgType;

    @Column (nullable = true)
    private String orgWebsite;

    @Column (nullable = true)
    private String orgDescription;

    @OneToMany(mappedBy = "organization")
    private Set<OrganizationFacility> organizationFacilities;

    @OneToMany(mappedBy = "organization")
    private Set<OrganizationOwner> organizationOwners;

    @OneToMany(mappedBy = "organization")
    private Set<OrganizationEmployees> organizationEmployees;

}
