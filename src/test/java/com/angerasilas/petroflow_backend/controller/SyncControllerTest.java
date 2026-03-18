package com.angerasilas.petroflow_backend.controller;

import com.angerasilas.petroflow_backend.BaseIntegrationTest;
import com.angerasilas.petroflow_backend.dto.SyncRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Sync Controller API Tests")
public class SyncControllerTest extends BaseIntegrationTest {

    @LocalServerPort
    private int port;

   

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Should synchronize client changes successfully")
    public void testSyncEndpoint_WithValidRequest_ShouldReturnSuccess() {
        // Arrange
        SyncRequest syncRequest = buildSyncRequest();

        // Act & Assert
        given()
            .contentType(ContentType.JSON)
            .header("X-Tenant-Id", "org_123")
            .header("Authorization", "Bearer valid-jwt-token")
            .body(syncRequest)
        .when()
            .post("/sync")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("success", is(true))
            .body("appliedChanges", greaterThanOrEqualTo(0))
            .body("syncedAt", notNullValue());
    }

    @Test
    @DisplayName("Should return health status for sync service")
    public void testSyncHealth_ShouldReturnOk() {
        given()
        .when()
            .get("/sync/health")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("status", is("UP"));
    }

    @Test
    @DisplayName("Should reject sync without tenant context")
    public void testSync_WithoutTenant_ShouldReturnUnauthorized() {
        // Arrange
        SyncRequest syncRequest = buildSyncRequest();

        // Act & Assert
        given()
            .contentType(ContentType.JSON)
            .body(syncRequest)
        .when()
            .post("/sync")
        .then()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    @DisplayName("Should process multiple changes in single sync request")
    public void testSync_WithMultipleChanges_ShouldApplyAll() {
        // Arrange
        SyncRequest syncRequest = buildSyncRequestWithMultipleChanges();

        // Act & Assert
        given()
            .contentType(ContentType.JSON)
            .header("X-Tenant-Id", "org_123")
            .header("Authorization", "Bearer valid-jwt-token")
            .body(syncRequest)
        .when()
            .post("/sync")
        .then()
            .statusCode(HttpStatus.OK.value())
            .body("success", is(true))
            .body("appliedChanges", greaterThan(0));
    }

    // Helper methods
    private SyncRequest buildSyncRequest() {
        SyncRequest request = new SyncRequest();
        request.setDeviceId("device_123");
        request.setChanges(new ArrayList<>());
        request.setLastSyncTimestamp(LocalDateTime.now().minusHours(1));
        return request;
    }

    private SyncRequest buildSyncRequestWithMultipleChanges() {
        SyncRequest request = new SyncRequest();
        request.setDeviceId("device_123");
        request.setChanges(new ArrayList<>());
        // Add multiple changes
        for (int i = 0; i < 3; i++) {
            SyncRequest.Change change = new SyncRequest.Change();
            change.setEntityId("entity_" + i);
            change.setEntityType("sales");
            change.setAction("create");
            request.getChanges().add(change);
        }
        request.setLastSyncTimestamp(LocalDateTime.now().minusHours(1));
        return request;
    }
}
