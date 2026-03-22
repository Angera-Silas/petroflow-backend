package com.angerasilas.petroflow_backend.tenancy.context;

/**
 * Thread-local holder for current tenant context.
 */
public final class TenantContextHolder {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContextHolder() {
    }

    public static void setCurrentTenant(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static String getCurrentTenant() {
        String tenant = CURRENT_TENANT.get();
        if (tenant == null) {
            throw new IllegalStateException("No tenant context has been set");
        }
        return tenant;
    }

    public static boolean hasContext() {
        return CURRENT_TENANT.get() != null;
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
