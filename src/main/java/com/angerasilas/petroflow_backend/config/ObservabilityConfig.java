package com.angerasilas.petroflow_backend.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Observability Configuration
 * From LLM_CONTEXT section 5.B: "Observability: structured logging, metrics, tracing"
 * 
 * Configures:
 * - Structured JSON logging for easy aggregation
 * - Application metrics (Sales, Inventory, Users)
 * - Request tracing for debugging
 * - Error tracking
 */
@Configuration
public class ObservabilityConfig {

    private static final Logger logger = LoggerFactory.getLogger(ObservabilityConfig.class);

    /**
     * Request logging filter for debugging HTTP traffic
     */
    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(10000);
        loggingFilter.setIncludeHeaders(true);
        loggingFilter.setAfterMessagePrefix("REQUEST DATA : ");
        return loggingFilter;
    }

    /**
     * Application metrics bean for tracking key operations
     */
    @Bean
    public ApplicationMetrics applicationMetrics(MeterRegistry registry) {
        return new ApplicationMetrics(registry);
    }

    /**
     * Application metrics tracking (sales, inventory, users, approvals)
     */
    public static class ApplicationMetrics {
        private final MeterRegistry registry;

        public ApplicationMetrics(MeterRegistry registry) {
            this.registry = registry;
        }

        /**
         * Track sales transactions
         */
        public void recordSaleTransaction(long amountChargedCents) {
            try {
                registry.counter("sales.transactions.total").increment();
                registry.timer("sales.transactions.amount").record(amountChargedCents, java.util.concurrent.TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                LoggerFactory.getLogger(ApplicationMetrics.class).error("Error recording sale metric", e);
            }
        }

        /**
         * Track sales approvals
         */
        public void recordSalesApproval(boolean approved) {
            registry.counter("sales.approvals." + (approved ? "approved" : "rejected")).increment();
        }

        /**
         * Track inventory variance alerts
         */
        public void recordInventoryVariance(double variance) {
            registry.gauge("inventory.variance.percentage", variance);
        }

        /**
         * Track user logins
         */
        public void recordUserLogin(String role) {
            registry.counter("auth.logins.by_role", "role", role).increment();
        }

        /**
         * Track API errors by endpoint
         */
        public void recordApiError(String endpoint, int statusCode) {
            registry.counter("api.errors", "endpoint", endpoint, "status", String.valueOf(statusCode)).increment();
        }
    }

    /**
     * Structured logging context helper (MDC - Mapped Diagnostic Context)
     * Usage: StructuredLogger.setTenant(orgId); StructuredLogger.setUser(userId);
     */
    public static class StructuredLogger {
        private static final String TENANT_MDC = "tenant_id";
        private static final String USER_MDC = "user_id";
        private static final String REQUEST_ID_MDC = "request_id";

        public static void setTenant(Long tenantId) {
            org.slf4j.MDC.put(TENANT_MDC, String.valueOf(tenantId));
        }

        public static void setUser(String userId) {
            org.slf4j.MDC.put(USER_MDC, userId);
        }

        public static void setRequestId(String requestId) {
            org.slf4j.MDC.put(REQUEST_ID_MDC, requestId);
        }

        public static void clear() {
            org.slf4j.MDC.clear();
        }

        /**
         * Log business event with structured context
         */
        public static void logEvent(String eventName, String details) {
            Logger logger = LoggerFactory.getLogger("PetroFlow.Events");
            logger.info("EVENT: {} - {}", eventName, details);
        }

        /**
         * Log security event (for audit)
         */
        public static void logSecurityEvent(String action, String resource, String details) {
            Logger logger = LoggerFactory.getLogger("PetroFlow.Security");
            logger.warn("SECURITY_EVENT: {} on {} - {}", action, resource, details);
        }

        /**
         * Log error with context
         */
        public static void logError(String component, String error, Throwable ex) {
            Logger logger = LoggerFactory.getLogger("PetroFlow.Errors");
            logger.error("ERROR in {}: {} - {}", component, error, ex.getMessage(), ex);
        }
    }
}
