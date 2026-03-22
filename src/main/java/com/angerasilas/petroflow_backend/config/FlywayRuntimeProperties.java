package com.angerasilas.petroflow_backend.config;

import com.angerasilas.petroflow_backend.common.constants.TenantConstants;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.flyway")
public class FlywayRuntimeProperties {

    private boolean migrateOnStartup = false;
    private String sharedSchema = TenantConstants.PUBLIC_SCHEMA;
    private List<String> sharedLocations = new ArrayList<>(List.of("classpath:db/migration/shared"));
    private List<String> tenantLocations = new ArrayList<>(List.of("classpath:db/migration/tenant"));
    private boolean baselineOnMigrate = true;
    private boolean validateOnMigrate = true;
}
