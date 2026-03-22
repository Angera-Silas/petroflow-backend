package com.angerasilas.petroflow_backend.tenancy.context;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validates tenant context to ensure no requests proceed without proper tenant identification.
 * This is a security safeguard for the multi-tenant system.
 *
 * Usage in service layer:
 * {@code
 * @Service
 * @RequiredArgsConstructor
 * public class EmployeeService {
 *     private final TenantContextValidator validator;
 *
 *     public Employee getEmployee(Long id) {
 *         validator.validateTenantContext();  // Will throw if tenant not set
 *         // ... rest of logic
 *     }
 * }
 * }
 */
@Slf4j
@Component
public class TenantContextValidator {

    /**
     * Validates that a tenant context has been set.
     * Call this from critical service methods to ensure tenant isolation.
     *
     * @return true if tenant context is valid
     * @throws IllegalStateException if no tenant context is set
     */
    public boolean validateTenantContext() {
        if (!TenantContextHolder.hasContext()) {
            log.error("SECURITY VIOLATION: Attempted operation without tenant context!");
            throw new IllegalStateException(
                "Tenant context required for this operation; " +
                "ensure request was properly routed through TenantContextFilter"
            );
        }
        return true;
    }

    /**
     * Get current tenant ID with validation.
     * Will throw exception if tenant not set.
     *
     * @return Current tenant ID
     * @throws IllegalStateException if no tenant context is set
     */
    public String getCurrentTenantId() {
        if (!TenantContextHolder.hasContext()) {
            throw new IllegalStateException("No tenant context available");
        }
        return TenantContextHolder.getCurrentTenant();
    }

    /**
     * Optional: get current tenant without throwing (for logging).
     * Returns null if tenant not set.
     *
     * @return Current tenant ID or null
     */
    public String getCurrentTenantIdOrNull() {
        return TenantContextHolder.hasContext() ? TenantContextHolder.getCurrentTenant() : null;
    }

    /**
     * Assert that the provided tenant ID matches the current context.
     * Useful for validating cross-tenant requests.
     *
     * @param tenantId The expected tenant ID
     * @return true if matches
     * @throws IllegalStateException if tenants don't match or context not set
     */
    public boolean assertTenantMatch(String tenantId) {
        String currentTenant = getCurrentTenantId();
        if (!currentTenant.equals(tenantId)) {
            log.error("SECURITY VIOLATION: Tenant mismatch! Expected: {}, Got: {}", tenantId, currentTenant);
            throw new IllegalStateException(
                String.format("Tenant context mismatch: expected '%s' but current is '%s'", tenantId, currentTenant)
            );
        }
        return true;
    }
}
