package com.angerasilas.petroflow_backend.tenancy.migration;

import com.angerasilas.petroflow_backend.config.FlywayRuntimeProperties;
import java.util.List;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlywayMultiTenantMigrator {

    private final DataSource dataSource;
    private final FlywayRuntimeProperties properties;

    public void migrateSharedSchema() {
        migrate(List.of(properties.getSharedSchema()), properties.getSharedLocations());
    }

    public void migrateTenantSchema(String schemaName) {
        migrate(List.of(normalizeSchemaName(schemaName)), properties.getTenantLocations());
    }

    public Flyway createFlywayForSharedSchema() {
        return createFlyway(List.of(properties.getSharedSchema()), properties.getSharedLocations());
    }

    public Flyway createFlywayForTenantSchema(String schemaName) {
        return createFlyway(List.of(normalizeSchemaName(schemaName)), properties.getTenantLocations());
    }

    private void migrate(List<String> schemas, List<String> locations) {
        Flyway flyway = createFlyway(schemas, locations);
        log.info("Executing Flyway migrations for schema(s) {} using locations {}", schemas, locations);
        flyway.migrate();
    }

    private Flyway createFlyway(List<String> schemas, List<String> locations) {
        return Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemas.toArray(String[]::new))
                .locations(locations.toArray(String[]::new))
                .baselineOnMigrate(properties.isBaselineOnMigrate())
                .validateOnMigrate(properties.isValidateOnMigrate())
                .load();
    }

    private String normalizeSchemaName(String schemaName) {
        if (!StringUtils.hasText(schemaName)) {
            throw new IllegalArgumentException("Tenant schema name must not be blank");
        }
        return schemaName.trim();
    }
}
