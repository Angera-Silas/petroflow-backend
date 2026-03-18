package com.angerasilas.petroflow_backend.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

import com.angerasilas.petroflow_backend.security.tenant.TenantContext;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String> {

    @Override
    public String resolveCurrentTenantIdentifier() {
        if (TenantContext.hasContext()) {
            return TenantContext.getCurrentTenant();
        }
        return "public";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
