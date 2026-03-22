package com.angerasilas.petroflow_backend.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RateLimitFilterConfig {

    private static final int REQUESTS_PER_SECOND = 100;
    private static final Duration TIMEOUT_DURATION = Duration.ofSeconds(5);

    private final RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.ofDefaults();
    private final Map<String, RateLimiter> rateLimiters = new HashMap<>();

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(REQUESTS_PER_SECOND)
                .timeoutDuration(TIMEOUT_DURATION)
                .build();

        return RateLimiterRegistry.of(config);
    }

    @Bean
    public OncePerRequestFilter rateLimitingFilter(RateLimiterRegistry registry) {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String tenantId = extractTenantId(request);
                String endpoint = request.getRequestURI();
                String rateLimitKey = tenantId + ":" + endpoint;

                RateLimiter rateLimiter = rateLimiters.computeIfAbsent(rateLimitKey, key ->
                        registry.rateLimiter(key));

                try {
                    if (rateLimiter.acquirePermission()) {
                        filterChain.doFilter(request, response);
                    } else {
                        response.setStatus(429); // Too Many Requests
                        response.getWriter().write("{\"error\": \"Rate limit exceeded\"}");
                        response.setContentType("application/json");
                    }
                } catch (Exception e) {
                    response.setStatus(500);
                    response.getWriter().write("{\"error\": \"Rate limiting error\"}");
                    response.setContentType("application/json");
                }
            }

            private String extractTenantId(HttpServletRequest request) {
                String tenantId = request.getHeader("X-Tenant-Id");
                if (tenantId == null || tenantId.isEmpty()) {
                    tenantId = "default";
                }
                return tenantId;
            }
        };
    }
}
