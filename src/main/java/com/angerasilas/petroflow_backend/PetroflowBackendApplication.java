package com.angerasilas.petroflow_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.angerasilas.petroflow_backend")
public class PetroflowBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetroflowBackendApplication.class, args);
    }
}