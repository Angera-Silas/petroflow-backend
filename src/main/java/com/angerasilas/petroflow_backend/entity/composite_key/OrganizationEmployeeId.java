package com.angerasilas.petroflow_backend.entity.composite_key;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class OrganizationEmployeeId implements Serializable {
    private Long organizationId;
    private Long facilityId;
    private Long employeeId; // Unique employee reference


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationEmployeeId that = (OrganizationEmployeeId) o;
        return Objects.equals(organizationId, that.organizationId) &&
                Objects.equals(employeeId, that.employeeId) &&
                Objects.equals(facilityId, that.facilityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationId, employeeId, facilityId);
    }
}
