package com.angerasilas.petroflow_backend.entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "employees")
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String employmentType;

    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false, unique = true)
    private String nssfNo;

    @Column(nullable = false, unique = true)
    private String nhifNo;

    @Column(nullable = false, unique = true)
    private String kraPin;

    @Column(nullable = false)
    private String hireDate;

    @Column(nullable = true)
    private String bankName;

    @Column(nullable = true)
    private String accountNo;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrganizationEmployees> organizationEmployees;

    

}