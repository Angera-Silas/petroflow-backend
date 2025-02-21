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
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // facility name

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
    private String email; // Station email

    @Column(nullable = false)
    private String servicesOffered;

    @OneToMany(mappedBy = "facility")
    private Set<OrganizationFacility> organizationFacilities;

    @OneToMany(mappedBy = "facility")
    private Set<OrganizationEmployees> organizationEmployees;


}
