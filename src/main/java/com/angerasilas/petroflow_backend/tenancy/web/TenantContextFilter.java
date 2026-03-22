package com.angerasilas.petroflow_backend.tenancy.web;

import com.angerasilas.petroflow_backend.tenancy.context.TenantContextHolder;
import com.angerasilas.petroflow_backend.tenancy.resolution.TenantResolutionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Tenant context filter for multi-tenant request handling.
 *
 * Responsibilities:
 * 1. Extract tenant ID from HTTP request (header, JWT, subdomain)
 * 2. Set ThreadLocal tenant context for downstream processing
 * 3. Enforce tenant context on protected routes
 * 4. Clean up ThreadLocal on request completion
 *
 * Filter Chain Execution Order:
 * Request → TenantResolutionService.resolveTenant() → TenantContextHolder.setCurrentTenant() → Pass to next filter
 *
 * Security:
 * - Protected routes without tenant context will return 401 Unauthorized
 * - Public routes (health, auth) are exempt
 * - ThreadLocal is always cleaned up in finally block (prevents context leaks)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TenantContextFilter extends OncePerRequestFilter {

    private final TenantResolutionService tenantResolutionService;

    /**
     * Routes that don't require tenant context
     * (e.g., auth, health checks, public endpoints)
     */
    private static final List<String> EXEMPT_PATHS = Arrays.asList(
        "/api/auth/",
        "/api/health/",
        "/health"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getRequestURI();
        boolean isExemptPath = EXEMPT_PATHS.stream().anyMatch(requestPath::startsWith);

        try {
            // Attempt to resolve tenant from request (header, JWT, subdomain)
            var tenantId = tenantResolutionService.resolveTenant(request);

            if (tenantId.isPresent()) {
                // Tenant successfully resolved
                String tenant = tenantId.get();
                TenantContextHolder.setCurrentTenant(tenant);
                request.setAttribute(TenantResolutionService.TENANT_REQUEST_ATTRIBUTE, tenant);
                log.debug("Tenant context set to: {}", tenant);
            } else if (!isExemptPath) {
                // Tenant NOT resolved and route is NOT exempt → REJECT
                log.warn("Tenant resolution failed for protected route: {} from IP: {}",
                    requestPath, request.getRemoteAddr());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Tenant context could not be resolved from request");
                return;
            } else {
                // Tenant not resolved but route is exempt (this is OK for health/auth)
                log.debug("Tenant resolution skipped for exempt path: {}", requestPath);
            }

            // Continue with the request
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error in tenant context filter for path: {}", requestPath, e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Error processing tenant context");
        } finally {
            // CRITICAL: Always clean up ThreadLocal to prevent context leaks
            TenantContextHolder.clear();
        }
    }
}
