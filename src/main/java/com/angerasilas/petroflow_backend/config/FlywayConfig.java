package com.angerasilas.petroflow_backend.config;

import lombok.extern.slf4j.Slf4j;
import com.angerasilas.petroflow_backend.tenancy.migration.FlywayMultiTenantMigrator;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@EnableConfigurationProperties(FlywayRuntimeProperties.class)
public class FlywayConfig {

    @Bean
    public ApplicationRunner flywayApplicationRunner(
            FlywayRuntimeProperties properties,
            FlywayMultiTenantMigrator migrator) {
        return args -> {
            if (!properties.isMigrateOnStartup()) {
                log.info("Flyway startup migration is disabled. Shared migrations can be executed explicitly later.");
                return;
            }

            log.info("Running Flyway shared-schema migrations on schema {} using locations {}",
                    properties.getSharedSchema(),
                    properties.getSharedLocations());
            migrator.migrateSharedSchema();
        };
    }
}
