package com.angerasilas.petroflow_backend.entity.composite_key;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data  // This generates getters, setters, equals(), hashCode(), and toString()
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrganizationFacilityId implements Serializable {

    private Long organizationId;

    private Long facilityId;

}
