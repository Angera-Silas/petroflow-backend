package com.angerasilas.petroflow_backend.security.tenant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter to extract and set tenant context from incoming requests.
 * Attempts extraction in this order:
 * 1. JWT claims (tenant_id)
 * 2. X-Tenant-Id header
 * 3. Subdomain (org-123.petrolflow.app)
 */
@Slf4j
@Component
public class TenantFilter extends OncePerRequestFilter {

    @Value("${jwt.secret:secret}")
    private String jwtSecret;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws ServletException, IOException {
        String tenantId = extractTenantId(request);

        if (tenantId != null) {
            TenantContext.setCurrentTenant(tenantId);
            log.debug("Tenant context set to: {}", tenantId);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String extractTenantId(HttpServletRequest request) {
        // 1. Try to extract from X-Tenant-Id header
        String tenantFromHeader = request.getHeader("X-Tenant-Id");
        if (tenantFromHeader != null && !tenantFromHeader.isEmpty()) {
            return tenantFromHeader;
        }

        // 2. Try to extract from Authorization header (JWT claims)
        String tenantFromJwt = extractFromJwt(request);
        if (tenantFromJwt != null) {
            return tenantFromJwt;
        }

        // 3. Try to extract from subdomain
        String tenantFromSubdomain = extractFromSubdomain(request);
        if (tenantFromSubdomain != null) {
            return tenantFromSubdomain;
        }

        return null;
    }

    private String extractFromJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();

            return (String) claims.get("tenant_id");
        } catch (Exception e) {
            log.warn("Failed to extract tenant from JWT: {}", e.getMessage());
            return null;
        }
    }

    private String extractFromSubdomain(HttpServletRequest request) {
        String host = request.getServerName();
        // Example: org-123.petrolflow.app -> org_123

        if (!host.contains(".")) {
            return null;
        }

        String[] parts = host.split("\\.");
        if (parts.length >= 2 && !parts[0].equals("www")) {
            // Convert hyphen to underscore
            return parts[0].replace("-", "_");
        }

        return null;
    }
}
