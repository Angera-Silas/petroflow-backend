package com.angerasilas.petroflow_backend.entity;

import com.angerasilas.petroflow_backend.entity.composite_key.OrganizationFacilityId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "organization_facilities")
public class OrganizationFacility {

    @EmbeddedId
    private OrganizationFacilityId organizationFacilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facilityId", insertable = false, updatable = false)
    private Facility facility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationId", insertable = false, updatable = false)
    private Organization organization;
}