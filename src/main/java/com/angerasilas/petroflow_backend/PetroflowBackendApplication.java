package com.angerasilas.petroflow_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


/**
 * Main class for the Petroflow Backend application.
 * This class serves as the entry point for the Spring Boot application.
 * It excludes certain auto-configurations related to JVM and system metrics.
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.angerasilas.petroflow_backend")

public class PetroflowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetroflowBackendApplication.class, args);
    }
}