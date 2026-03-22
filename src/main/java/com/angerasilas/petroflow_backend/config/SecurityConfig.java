package com.angerasilas.petroflow_backend.config;

import com.angerasilas.petroflow_backend.tenancy.web.TenantContextFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

/**
 * Security configuration for PetroFlow backend.
 *
 * Responsibilities:
 * 1. CORS configuration for frontend integrations
 * 2. HTTP security: CSRF disabled (JWT-based), auth enforcement
 * 3. Tenant context filter: Extracts tenant from request before authentication
 * 4. Method-level security: @PreAuthorize, @PostAuthorize annotations
 *
 * Filter Chain:
 * TenantContextFilter (extract tenant) → Spring Security (authenticate) → Authorization (RBAC)
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TenantContextFilter tenantContextFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // CSRF: Disabled (using JWT instead)
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())

            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                    // Public endpoints (no auth required)
                    .requestMatchers("/api/health/**").permitAll()
                    .requestMatchers("/api/auth/**").permitAll()

                    // Protected endpoints (authentication + tenant context required)
                    .anyRequest().authenticated()
            )

            // Tenant context extraction (before Spring Security authentication)
            // This ensures tenant is available in the security context
            .addFilterBefore(tenantContextFilter, SecurityContextHolderFilter.class);

        log.debug("Security filter chain configured");
        return http.build();
    }
}
