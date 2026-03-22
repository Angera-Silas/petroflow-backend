package com.angerasilas.petroflow_backend.config;

import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

/**
 * Hibernate multi-tenancy identifier resolver.
 *
 * Provides the current tenant ID for Hibernate's session partitioning.
 * Required for schema-per-tenant PostgreSQL strategy.
 *
 * CRITICAL: This resolver is called for EVERY Hibernate query.
 * It bridges the HTTP request context (set by TenantContextFilter)
 * to Hibernate's internal multi-tenancy session handler.
 *
 * Flow:
 * 1. HTTP Request arrives → TenantContextFilter extracts tenant → Sets ThreadLocal
 * 2. Business logic calls repository method
 * 3. Hibernate intercepts query → Calls this resolver
 * 4. This resolver gets tenant from ThreadLocal
 * 5. Hibernate adds schema filter to query
 * 6. Query executes in tenant-specific schema
 *
 * Safety:
 * - If tenant context is not set, defaults to "public" schema
 * - This should only happen for exempt routes (health, auth)
 * - Protected routes without tenant context are rejected by TenantContextFilter
 */
@Slf4j
@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        if (TenantContextHolder.hasContext()) {
            String tenant = TenantContextHolder.getCurrentTenant();
            log.trace("Resolved tenant identifier: {}", tenant);
            return tenant;
        }

        // SECURITY WARNING: Returning "public" means Hibernate will query the public schema
        // This should only happen for exempt routes (health, auth)
        // Protected routes without tenant context are rejected by TenantContextFilter
        log.debug("No tenant context available in Hibernate identifier resolver; defaulting to 'public' schema");
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
