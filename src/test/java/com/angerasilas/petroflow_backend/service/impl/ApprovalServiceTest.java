package com.angerasilas.petroflow_backend.service.impl;

import com.angerasilas.petroflow_backend.entity.ApprovalRequest;
import com.angerasilas.petroflow_backend.repository.ApprovalRequestRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.angerasilas.petroflow_backend.service.ApprovalService;
import com.angerasilas.petroflow_backend.service.AuditLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Approval Service Unit Tests")
@ExtendWith(MockitoExtension.class)
public class ApprovalServiceTest {

    @Mock
    private ApprovalRequestRepository approvalRepository;

    @Mock
    private AuditLogService auditLogService;

    @InjectMocks
    private ApprovalService approvalService;

    @BeforeEach
    public void setUp() {
        new ObjectMapper();
        TenantContext.setCurrentTenant("org_123");
    }

    @Test
    @DisplayName("Should create approval request successfully")
    public void testCreateApprovalRequest_WithValidData_ShouldCreateApproval() {
        // Arrange
        when(approvalRepository.save(any(ApprovalRequest.class)))
            .thenAnswer(invocation -> {
                ApprovalRequest approval = invocation.getArgument(0);
                approval.setId("approval_123");
                return approval;
            });

        // Act
        ApprovalRequest result = approvalService.createApprovalRequest(
            "SALES_EDIT", "sales_1", "user_1", "manager_1",
            "Check sales record", null, null, "HIGH"
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo("PENDING");
        assertThat(result.getPriority()).isEqualTo("HIGH");
    }

    @Test
    @DisplayName("Should approve request successfully")
    public void testApproveRequest_WithValidApproval_ShouldUpdateStatus() {
        // Arrange
        ApprovalRequest approval = buildApprovalRequest("PENDING");
        when(approvalRepository.findById("approval_123"))
            .thenReturn(Optional.of(approval));
        when(approvalRepository.save(any(ApprovalRequest.class)))
            .thenReturn(approval);

        // Act
        ApprovalRequest result = approvalService.approveRequest(
            "approval_123", "manager_1", "Looks good", "manager_1"
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("APPROVED");
        assertThat(result.getApprovalComment()).isEqualTo("Looks good");
        assertThat(result.getApprovedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should reject request successfully")
    public void testRejectRequest_WithValidApproval_ShouldUpdateStatus() {
        // Arrange
        ApprovalRequest approval = buildApprovalRequest("PENDING");
        when(approvalRepository.findById("approval_123"))
            .thenReturn(Optional.of(approval));
        when(approvalRepository.save(any(ApprovalRequest.class)))
            .thenReturn(approval);

        // Act
        ApprovalRequest result = approvalService.rejectRequest(
            "approval_123", "manager_1", "Needs revision", "manager_1"
        );

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("REJECTED");
        assertThat(result.getRejectionReason()).isEqualTo("Needs revision");
        assertThat(result.getRejectedAt()).isNotNull();
    }

    @Test
    @DisplayName("Should get pending approvals for approver")
    public void testGetPendingApprovalsFor_ShouldReturnApprovals() {
        // Arrange
        // Mock page response (simplified)

        // Act
        // ApprovalRequest result = approvalService.getPendingApprovalsFor("manager_1", pageable);

        // Assert
        // Note: Full test depends on Page implementation
    }

    @Test
    @DisplayName("Should check if entity has pending approval")
    public void testHasPendingApproval_WithPendingApproval_ShouldReturnTrue() {
        // Arrange
        ApprovalRequest approval = buildApprovalRequest("PENDING");
        when(approvalRepository.findByTenantIdAndEntityIdAndStatus(
            "org_123", "sales_1", "PENDING"
        )).thenReturn(Optional.of(approval));

        // Act
        boolean result = approvalService.hasPendingApproval("sales_1");

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should get pending approval count")
    public void testGetPendingApprovalCount_ShouldReturnCount() {
        // Arrange
        when(approvalRepository.countByTenantIdAndStatus("org_123", "PENDING"))
            .thenReturn(5L);

        // Act
        long result = approvalService.getPendingApprovalCount();

        // Assert
        assertThat(result).isEqualTo(5L);
    }

    // Helper methods
    private ApprovalRequest buildApprovalRequest(String status) {
        ApprovalRequest approval = new ApprovalRequest();
        approval.setId("approval_123");
        approval.setTenantId("org_123");
        approval.setRequestType("SALES_EDIT");
        approval.setEntityId("sales_1");
        approval.setRequesterId("user_1");
        approval.setApproverId("manager_1");
        approval.setStatus(status);
        approval.setPriority("HIGH");
        approval.setRequestReason("Verification needed");
        approval.setCreatedAt(LocalDateTime.now());
        approval.setExpiresAt(LocalDateTime.now().plusDays(7));
        return approval;
    }
}
