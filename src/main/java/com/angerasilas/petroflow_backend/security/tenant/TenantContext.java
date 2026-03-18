package com.angerasilas.petroflow_backend.security.tenant;

/**
 * ThreadLocal holder for the current tenant context.
 * Used to provide tenant information to all business logic layers.
 */
public class TenantContext {
    private static final ThreadLocal<String> currentTenant = new ThreadLocal<>();

    public static void setCurrentTenant(String tenantId) {
        currentTenant.set(tenantId);
    }

    public static String getCurrentTenant() {
        String tenant = currentTenant.get();
        if (tenant == null) {
            throw new IllegalStateException("No tenant context has been set");
        }
        return tenant;
    }

    public static void clear() {
        currentTenant.remove();
    }

    public static boolean hasContext() {
        return currentTenant.get() != null;
    }
}
