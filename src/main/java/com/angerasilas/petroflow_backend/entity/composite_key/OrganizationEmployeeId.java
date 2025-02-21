package com.angerasilas.petroflow_backend.entity.composite_key;

import java.io.Serializable;
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
}
