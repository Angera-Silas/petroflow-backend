package com.angerasilas.petroflow_backend.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.event.EventListener;

import com.angerasilas.petroflow_backend.entity.PermissionEntity;
import com.angerasilas.petroflow_backend.entity.Role;
import com.angerasilas.petroflow_backend.entity.RoleEntity;
import com.angerasilas.petroflow_backend.repository.PermissionRepository;
import com.angerasilas.petroflow_backend.repository.RoleRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Component
@ConditionalOnProperty(name = "app.data.init.enabled", havingValue = "true")
public class DataInitializer {

        private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

        @PersistenceContext
        private EntityManager entityManager;

        private final RoleRepository roleRepository;
        private final PermissionRepository permissionRepository;

        @EventListener(ApplicationReadyEvent.class)
        public void initialize() {
                try {
                        initSchemas();
                        initData();
                        log.info("Roles & Permissions Initialized Successfully!");
                } catch (Exception e) {
                        log.warn("Skipping startup data initialization: {}", e.getMessage());
                }
        }

        private void initSchemas() {
                                List<String> schemas = List.of("incidents", "communication", "dashboard", "organizations");
                for (String schema : schemas) {
                        entityManager.createNativeQuery("CREATE SCHEMA IF NOT EXISTS " + schema).executeUpdate();
                }

                                // Create roles & permissions tables in public schema if not exists
                entityManager.createNativeQuery("""
                                                                CREATE TABLE IF NOT EXISTS roles (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) UNIQUE NOT NULL
                                );
                                """).executeUpdate();

                entityManager.createNativeQuery("""
                                                                CREATE TABLE IF NOT EXISTS permissions (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) UNIQUE NOT NULL
                                );
                                """).executeUpdate();

                entityManager.createNativeQuery("""
                                                                CREATE TABLE IF NOT EXISTS role_permissions (
                                    role_id BIGINT NOT NULL,
                                    permission_id BIGINT NOT NULL,
                                    PRIMARY KEY (role_id, permission_id),
                                                                        FOREIGN KEY (role_id) REFERENCES roles(id),
                                                                        FOREIGN KEY (permission_id) REFERENCES permissions(id)
                                );
                                """).executeUpdate();
        }

        private void initData() {
                // Define all permissions
                List<String> permissionNames = Arrays.asList(
                                "SYSTEM_MANAGE", "SYSTEM_VIEW_LOGS", "SYSTEM_MANAGE_USERS", "SYSTEM_MANAGE_PERMISSIONS",
                                "ADD_NEW_COMPANY", "MANAGE_COMPANIES", "VIEW_COMPANY",
                                "ADD_USER", "MANAGE_USERS", "VIEW_USER", "USER_ROLES", "MANAGE_PERMISSIONS",
                                "ADD_FACILITY", "MANAGE_FACILITIES", "VIEW_FACILITY",
                                "ADD_EMPLOYEE", "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "VIEW_PERFORMANCE_SUMMARY",
                                "MANAGE_EMPLOYEE_SHIFTS",
                                "RECORD_SALE", "APPROVE_SALES", "TRACK_DEFAULTERS", "VIEW_SALES_SUMMARY",
                                "SEARCH_SALES_RECORDS",
                                "EDIT_SALE_RECORD", "PROCESS_REFUND", "VIEW_PAYMENTS_HISTORY",
                                "REPORT_INCIDENT", "APPROVE_INCIDENT", "VIEW_SUBMITTED_INCIDENTS", "RESOLVE_INCIDENT",
                                "SUBMIT_REQUEST", "APPROVE_REQUEST", "REVIEW_REQUEST", "VIEW_REQUEST_STATUS",
                                "VIEW_SUBMITTED_REQUESTS",
                                "ADD_INVENTORY_ITEM", "REMOVE_INVENTORY_ITEM", "UPDATE_INVENTORY_ITEM",
                                "VIEW_INVENTORY_ITEMS",
                                "VIEW_INVENTORY_STATUS",
                                "ADD_PRODUCT", "REMOVE_PRODUCT", "UPDATE_PRODUCT", "VIEW_PRODUCTS",
                                "VIEW_PRODUCT_STATUS",
                                "VIEW_SALES_REPORT", "VIEW_INCIDENT_REPORT", "VIEW_REQUEST_REPORT",
                                "VIEW_INVENTORY_REPORT",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "VIEW_ORGANIZATION_PERFORMANCE_REPORT", "VIEW_PRODUCT_SALES_REPORT",
                                "VIEW_EXPENDITURE_REPORT",
                                "VIEW_SUMMARY_REPORT");

                // Save permissions in the database if they don’t exist
                Map<String, PermissionEntity> permissionMap = new HashMap<>();
                for (String permissionName : permissionNames) {
                        PermissionEntity permission = permissionRepository.findByName(permissionName)
                                        .orElseGet(() -> permissionRepository
                                                        .save(new PermissionEntity(null, permissionName, null, null)));
                        permissionMap.put(permissionName, permission);
                }
                // Define roles and assign default permissions
                Map<Role, List<String>> rolePermissions = new HashMap<>();
                rolePermissions.put(Role.SYSTEM_ADMIN, permissionNames); // All permissions
                rolePermissions.put(Role.ORGANIZATION_ADMIN, List.of(
                                "VIEW_COMPANY",
                                "ADD_EMPLOYEE", "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "VIEW_PERFORMANCE_SUMMARY",
                                "MANAGE_EMPLOYEE_SHIFTS",
                                "ADD_FACILITY", "MANAGE_FACILITIES", "VIEW_FACILITY",
                                "VIEW_SALES_REPORT", "VIEW_INCIDENT_REPORT", "VIEW_REQUEST_REPORT",
                                "VIEW_INVENTORY_REPORT",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "VIEW_ORGANIZATION_PERFORMANCE_REPORT", "VIEW_PRODUCT_SALES_REPORT",
                                "VIEW_EXPENDITURE_REPORT",
                                "VIEW_SUMMARY_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.STATION_ADMIN, List.of(
                                "ADD_EMPLOYEE", "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "MANAGE_EMPLOYEE_SHIFTS",
                                "RECORD_SALE", "APPROVE_SALES", "TRACK_DEFAULTERS", "SEARCH_SALES_RECORDS",
                                "EDIT_SALE_RECORD",
                                "PROCESS_REFUND",
                                "REPORT_INCIDENT", "VIEW_SUBMITTED_INCIDENTS", "RESOLVE_INCIDENT",
                                "SUBMIT_REQUEST", "APPROVE_REQUEST", "REVIEW_REQUEST", "VIEW_REQUEST_STATUS",
                                "VIEW_SUBMITTED_REQUESTS",
                                "ADD_INVENTORY_ITEM", "REMOVE_INVENTORY_ITEM", "UPDATE_INVENTORY_ITEM",
                                "VIEW_INVENTORY_ITEMS",
                                "VIEW_INVENTORY_STATUS",
                                "ADD_PRODUCT", "REMOVE_PRODUCT", "UPDATE_PRODUCT", "VIEW_PRODUCTS",
                                "VIEW_PRODUCT_STATUS",
                                "VIEW_SALES_REPORT", "VIEW_INCIDENT_REPORT", "VIEW_REQUEST_REPORT",
                                "VIEW_INVENTORY_REPORT",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "VIEW_PRODUCT_SALES_REPORT",
                                "VIEW_EXPENDITURE_REPORT", "VIEW_SUMMARY_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.CUSTOMER_ATTENDANT, List.of(
                                "VIEW_SALES", "RECORD_SALE", "TRACK_DEFAULTERS", "SEARCH_SALES_RECORDS",
                                "EDIT_SALE_RECORD",
                                "PROCESS_REFUND", "VIEW_PAYMENTS_HISTORY", "VIEW_PRODUCT_STATUS", "VIEW_PRODUCTS",
                                "VIEW_INVENTORY_ITEMS", "REPORT_INCIDENT", "VIEW_SUBMITTED_INCIDENTS", "SUBMIT_REQUEST",
                                "VIEW_SUBMITTED_REQUESTS", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.OIL_SPECIALIST, List.of(
                                "VIEW_INVENTORY_ITEMS", "UPDATE_INVENTORY_ITEM", "VIEW_PRODUCT_STATUS", "VIEW_PRODUCTS",
                                "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.RETAILER, List.of(
                                "VIEW_INVENTORY_ITEMS", "UPDATE_INVENTORY_ITEM", "VIEW_PRODUCT_STATUS", "VIEW_PRODUCTS",
                                "VIEW_SALES_REPORT", "VIEW_INCIDENT_REPORT", "VIEW_REQUEST_REPORT",
                                "VIEW_INVENTORY_REPORT",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "VIEW_PRODUCT_SALES_REPORT",
                                "VIEW_EXPENDITURE_REPORT", "VIEW_SUMMARY_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.ACCOUNTANT, List.of(
                                "VIEW_SALES", "APPROVE_SALES", "VIEW_EXPENDITURE_REPORT", "VIEW_SUMMARY_REPORT",
                                "UPDATE_PROFILE",
                                "VIEW_PROFILE"));
                rolePermissions.put(Role.STATION_MANAGER, List.of(
                                "ADD_EMPLOYEE", "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "VIEW_PERFORMANCE_SUMMARY",
                                "MANAGE_EMPLOYEE_SHIFTS",
                                "RECORD_SALE", "APPROVE_SALES", "TRACK_DEFAULTERS", "VIEW_SALES_SUMMARY",
                                "SEARCH_SALES_RECORDS",
                                "EDIT_SALE_RECORD", "PROCESS_REFUND", "VIEW_PAYMENTS_HISTORY",
                                "REPORT_INCIDENT", "APPROVE_INCIDENT", "VIEW_SUBMITTED_INCIDENTS", "RESOLVE_INCIDENT",
                                "SUBMIT_REQUEST", "APPROVE_REQUEST", "REVIEW_REQUEST", "VIEW_REQUEST_STATUS",
                                "VIEW_SUBMITTED_REQUESTS",
                                "ADD_INVENTORY_ITEM", "REMOVE_INVENTORY_ITEM", "UPDATE_INVENTORY_ITEM",
                                "VIEW_INVENTORY_ITEMS",
                                "VIEW_INVENTORY_STATUS",
                                "ADD_PRODUCT", "REMOVE_PRODUCT", "UPDATE_PRODUCT", "VIEW_PRODUCTS",
                                "VIEW_PRODUCT_STATUS",
                                "VIEW_SALES_REPORT", "VIEW_INCIDENT_REPORT", "VIEW_REQUEST_REPORT",
                                "VIEW_INVENTORY_REPORT",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "VIEW_PRODUCT_SALES_REPORT",
                                "VIEW_EXPENDITURE_REPORT", "VIEW_SUMMARY_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.DEPARTMENT_MANAGER, List.of(
                                "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "VIEW_PERFORMANCE_SUMMARY",
                                "MANAGE_EMPLOYEE_SHIFTS",
                                "VIEW_SALES_SUMMARY", "VIEW_INVENTORY_ITEMS", "VIEW_PRODUCT_STATUS", "VIEW_PRODUCTS",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "VIEW_FACILITY_PERFORMANCE_REPORT",
                                "UPDATE_PROFILE",
                                "VIEW_PROFILE"));
                rolePermissions.put(Role.HR_MANAGER, List.of(
                                "ADD_EMPLOYEE", "MANAGE_EMPLOYEES", "VIEW_EMPLOYEE", "VIEW_PERFORMANCE_SUMMARY",
                                "MANAGE_EMPLOYEE_SHIFTS",
                                "VIEW_EMPLOYEE_PERFORMANCE_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));
                rolePermissions.put(Role.QUALITY_MARSHAL, List.of(
                                "VIEW_INVENTORY_ITEMS", "VIEW_PRODUCT_STATUS", "VIEW_PRODUCTS",
                                "REPORT_INCIDENT", "VIEW_SUBMITTED_INCIDENTS", "RESOLVE_INCIDENT",
                                "VIEW_INCIDENT_REPORT", "UPDATE_PROFILE", "VIEW_PROFILE"));

                // Save roles with default permissions
                for (Map.Entry<Role, List<String>> entry : rolePermissions.entrySet()) {
                        String roleName = entry.getKey().name(); // Convert enum to string
                        List<String> rolePerms = entry.getValue();

                        // Convert permission names to PermissionEntity objects
                        Set<PermissionEntity> rolePermissionEntities = new HashSet<>();
                        for (String permName : rolePerms) {
                                rolePermissionEntities.add(permissionMap.get(permName));
                        }

                        // Check if role exists, otherwise create it
                        roleRepository.findByName(roleName)
                                        .orElseGet(() -> roleRepository
                                                        .save(new RoleEntity(roleName, rolePermissionEntities)));
                }
        }
}