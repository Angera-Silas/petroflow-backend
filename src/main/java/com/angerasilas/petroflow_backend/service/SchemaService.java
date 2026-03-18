package com.angerasilas.petroflow_backend.service;

public interface SchemaService {
    void initializeGlobalSchemas();
    void createOrganizationSchema(String orgName, String packageTier);
} 