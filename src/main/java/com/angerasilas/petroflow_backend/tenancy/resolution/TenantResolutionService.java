package com.angerasilas.petroflow_backend.tenancy.resolution;

import com.angerasilas.petroflow_backend.common.constants.ClaimNames;
import com.angerasilas.petroflow_backend.common.constants.HeaderNames;
import com.angerasilas.petroflow_backend.common.constants.SecurityConstants;
import com.angerasilas.petroflow_backend.common.constants.TenantConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.angerasilas.petroflow_backend.security.jwt.JwtUtil;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantResolutionService {

    public static final String TENANT_HEADER = HeaderNames.TENANT_ID;
    public static final String TENANT_REQUEST_ATTRIBUTE = TenantConstants.TENANT_REQUEST_ATTRIBUTE;

    private final JwtUtil jwtUtil;

    public Optional<String> resolveTenant(HttpServletRequest request) {
        String fromHeader = request.getHeader(TENANT_HEADER);
        if (hasText(fromHeader)) {
            return Optional.of(fromHeader.trim());
        }

        String fromJwt = resolveFromJwt(request);
        if (hasText(fromJwt)) {
            return Optional.of(fromJwt);
        }

        String fromSubdomain = resolveFromSubdomain(request.getServerName());
        if (hasText(fromSubdomain)) {
            return Optional.of(fromSubdomain);
        }

        return Optional.empty();
    }

    private String resolveFromJwt(HttpServletRequest request) {
        String authHeader = request.getHeader(HeaderNames.AUTHORIZATION);
        if (!hasText(authHeader) || !authHeader.startsWith(SecurityConstants.BEARER_PREFIX)) {
            return null;
        }

        String token = authHeader.substring(SecurityConstants.BEARER_PREFIX.length());
        String tenantId = jwtUtil.extractStringClaim(token, ClaimNames.TENANT_ID).orElse(null);
        if (!hasText(tenantId)) {
            log.debug("No tenant claim was found in JWT token");
        }
        return tenantId;
    }

    private String resolveFromSubdomain(String host) {
        if (!hasText(host) || !host.contains(".")) {
            return null;
        }

        String[] parts = host.split("\\.");
        if (parts.length < 2 || "www".equalsIgnoreCase(parts[0])) {
            return null;
        }

        return parts[0].replace('-', '_');
    }

    private boolean hasText(String value) {
        return StringUtils.hasText(value);
    }
}
