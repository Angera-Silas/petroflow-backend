package com.angerasilas.petroflow_backend.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.angerasilas.petroflow_backend.service.SchemaService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class ShemaServiceImpl implements SchemaService {
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${app.schema.init.enabled:true}")
    private boolean schemaInitEnabled;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        if (!schemaInitEnabled) {
            log.info("Schema initialization is disabled by configuration.");
            return;
        }

        try {
            initializeGlobalSchemas();
        } catch (Exception ex) {
            log.error("Global schema initialization failed: {}", ex.getMessage(), ex);
        }
    }
    
    @Override
    public void initializeGlobalSchemas() {
        createSchemaIfNotExists("public");
        createSchemaIfNotExists("communication");
        createSchemaIfNotExists("incidents");
        createSchemaIfNotExists("organizations");
        createSchemaIfNotExists("users");
        log.info("Global schemas initialized.");
    }

    @Override
    public void createOrganizationSchema(String orgName, String packageTier) {
        String schemaName = buildTenantSchemaName(orgName);
        createSchemaIfNotExists(schemaName);
        createTenantTables(schemaName, packageTier);
        log.info("Organization schema provisioned: {} with package: {}", schemaName, normalizePackageTier(packageTier));
    }

    private void createSchemaIfNotExists(String schemaName) {
        String safeSchema = sanitizeIdentifier(schemaName);
        execute("CREATE SCHEMA IF NOT EXISTS \"" + safeSchema + "\"");
    }

    private void createTenantTables(String schemaName, String packageTier) {
        String safeSchema = sanitizeIdentifier(schemaName);
        String normalizedTier = normalizePackageTier(packageTier);

        execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".employees ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "user_id BIGINT,"
            + "job_title VARCHAR(255),"
            + "employment_type VARCHAR(255),"
            + "salary DOUBLE PRECISION,"
            + "nssf_no VARCHAR(255),"
            + "nhif_no VARCHAR(255),"
            + "kra_pin VARCHAR(255),"
            + "hire_date VARCHAR(255),"
            + "bank_name VARCHAR(255),"
            + "account_no VARCHAR(255)"
            + ")");

        execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".products ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "date_added VARCHAR(255),"
            + "product_name VARCHAR(255),"
            + "product_description VARCHAR(255),"
            + "product_category VARCHAR(255),"
            + "product_sub_category VARCHAR(255),"
            + "org_id BIGINT NOT NULL,"
            + "facility_id BIGINT NOT NULL,"
            + "department VARCHAR(255)"
            + ")");

        execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".stocks ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "date_stocked TIMESTAMP,"
            + "product_id BIGINT,"
            + "org_id BIGINT,"
            + "facility_id BIGINT,"
            + "units_available DOUBLE PRECISION,"
            + "units_sold DOUBLE PRECISION,"
            + "units_bought DOUBLE PRECISION,"
            + "units_returned DOUBLE PRECISION,"
            + "units_damaged DOUBLE PRECISION,"
            + "units_lost DOUBLE PRECISION,"
            + "buying_price_per_unit DOUBLE PRECISION,"
            + "selling_price_per_unit DOUBLE PRECISION"
            + ")");

        execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".inventory ("
            + "id BIGSERIAL PRIMARY KEY,"
            + "product_id BIGINT,"
            + "facility_id BIGINT,"
            + "quantity DOUBLE PRECISION,"
            + "updated_at TIMESTAMP"
            + ")");

        if ("standard".equals(normalizedTier) || "enterprise".equals(normalizedTier)) {
            execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".sales ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "date_time TIMESTAMP,"
                + "product_id BIGINT,"
                + "employee_no VARCHAR(255),"
                + "sell_point_id BIGINT,"
                + "shift_id BIGINT,"
                + "units_sold VARCHAR(255),"
                + "amount_billed DOUBLE PRECISION,"
                + "discount DOUBLE PRECISION,"
                + "amount_paid DOUBLE PRECISION,"
                + "payment_method VARCHAR(255),"
                + "payment_status VARCHAR(255),"
                + "sale_status VARCHAR(255)"
                + ")");
        }

        if ("enterprise".equals(normalizedTier)) {
            execute("CREATE TABLE IF NOT EXISTS \"" + safeSchema + "\".pump_meter_readings ("
                + "id BIGSERIAL PRIMARY KEY,"
                + "org_id BIGINT NOT NULL,"
                + "facility_id BIGINT NOT NULL,"
                + "sell_point_id BIGINT NOT NULL,"
                + "shift_id BIGINT NOT NULL,"
                + "start_reading DOUBLE PRECISION NOT NULL,"
                + "end_reading DOUBLE PRECISION,"
                + "reading_date TIMESTAMP NOT NULL,"
                + "created_at VARCHAR(255),"
                + "updated_at VARCHAR(255),"
                + "created_by VARCHAR(255) NOT NULL,"
                + "updated_by VARCHAR(255),"
                + "status VARCHAR(255) NOT NULL"
                + ")");
        }
    }

    private String buildTenantSchemaName(String orgName) {
        String source = orgName == null ? "organization" : orgName;
        String normalized = source.toLowerCase(Locale.ROOT)
            .replaceAll("[^a-z0-9]+", "_")
            .replaceAll("_+", "_")
            .replaceAll("^_", "")
            .replaceAll("_$", "");

        if (normalized.isBlank()) {
            normalized = "organization";
        }

        return normalized + "_org";
    }

    private String sanitizeIdentifier(String identifier) {
        if (identifier == null || identifier.isBlank()) {
            throw new IllegalArgumentException("Schema identifier cannot be blank");
        }

        String cleaned = identifier.toLowerCase(Locale.ROOT)
            .replaceAll("[^a-z0-9_]", "_")
            .replaceAll("_+", "_")
            .replaceAll("^_", "")
            .replaceAll("_$", "");

        if (cleaned.isBlank()) {
            throw new IllegalArgumentException("Schema identifier is invalid");
        }

        return cleaned;
    }

    private String normalizePackageTier(String packageTier) {
        if (packageTier == null || packageTier.isBlank()) {
            return "basic";
        }

        String normalized = packageTier.trim().toLowerCase(Locale.ROOT);
        if (normalized.contains("enterprise")) {
            return "enterprise";
        }
        if (normalized.contains("standard") || normalized.contains("premium") || normalized.contains("pro")) {
            return "standard";
        }
        return "basic";
    }

    private void execute(String sql) {
        entityManager.createNativeQuery(sql).executeUpdate();
    }
}
