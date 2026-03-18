package com.angerasilas.petroflow_backend.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.RequestDto;
import com.angerasilas.petroflow_backend.entity.Request;
import com.angerasilas.petroflow_backend.entity.SyncChange;
import com.angerasilas.petroflow_backend.entity.OrganizationEmployees;
import com.angerasilas.petroflow_backend.mapper.RequestMapper;
import com.angerasilas.petroflow_backend.service.AuditLogService;
import com.angerasilas.petroflow_backend.repository.RequestRepository;
import com.angerasilas.petroflow_backend.repository.SyncChangeRepository;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import com.angerasilas.petroflow_backend.security.tenant.TenantContext;
import com.angerasilas.petroflow_backend.service.RequestService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

    @Autowired
    private SyncChangeRepository syncChangeRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private OrganizationEmployeesRepository organizationEmployeesRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public RequestDto createRequest(RequestDto requestDto) {
        Request request = requestMapper.toEntity(requestDto);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public RequestDto getRequestById(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        return requestMapper.toDto(request);
    }

    @Override
    public List<RequestDto> getAllRequests() {
        return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public RequestDto updateRequest(Long id, RequestDto requestDto) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RuntimeException("Request not found"));
        request.setTitle(requestDto.getTitle());
        request.setDescription(requestDto.getDescription());
        request.setStatus(requestDto.getStatus());
        request.setDateResolved(requestDto.getDateResolved());
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public void deleteRequest(Long id) {
        requestRepository.deleteById(id);
    }

    @Override
    public Optional<RequestDto> getByEmployee_EmployeeNo(String employeeNo) {
        return requestRepository.findByEmployee_EmployeeNo(employeeNo).map(requestMapper::toDto);
    }

    /**
     * Create request from sync request
     */
    @Override
    public boolean createFromSync(JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            String employeeNo = data.get("employeeNo").asText();
            OrganizationEmployees employee = organizationEmployeesRepository.findByEmployeeNo(employeeNo)
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            Request request = new Request();
            request.setEmployee(employee);
            request.setTitle(data.get("title").asText());
            request.setDescription(data.get("description").asText());
            request.setDateRequested(new Date());
            request.setStatus(data.get("status") != null ? data.get("status").asText() : "PENDING");

            Request savedRequest = requestRepository.save(request);

            // Record in SyncChange
            String requestId = String.valueOf(savedRequest.getId());
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(requestId)
                    .entityType("REQUEST")
                    .action("create")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .newValue(objectMapper.writeValueAsString(savedRequest))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "CREATE_REQUEST",
                    "REQUEST", requestId, savedRequest, "Request created from sync");

            log.info("Request created from sync: {} by device {}", requestId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to create request from sync", e);
            auditLogService.logFailure(tenantId, userId, "CREATE_REQUEST", "REQUEST",
                    "unknown", e.getMessage(), "Failed to create request from sync");
            return false;
        }
    }

    /**
     * Update request from sync request
     */
    @Override
    public boolean updateFromSync(Long id, JsonNode data, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Request existingRequest = requestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            // Save old value for audit
            String oldValueJson = objectMapper.writeValueAsString(existingRequest);

            // Update fields
            if (data.has("title")) {
                existingRequest.setTitle(data.get("title").asText());
            }
            if (data.has("description")) {
                existingRequest.setDescription(data.get("description").asText());
            }
            if (data.has("status")) {
                existingRequest.setStatus(data.get("status").asText());
            }
            if (data.has("dateResolved")) {
                existingRequest.setDateResolved(new Date());
            }

            Request updatedRequest = requestRepository.save(existingRequest);

            // Record in SyncChange
            String requestId = String.valueOf(id);
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(requestId)
                    .entityType("REQUEST")
                    .action("update")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .newValue(objectMapper.writeValueAsString(updatedRequest))
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logUpdate(tenantId, userId, "UPDATE_REQUEST", "REQUEST", requestId,
                    oldValueJson, updatedRequest, "Request updated from sync");

            log.info("Request updated from sync: {} by device {}", requestId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to update request from sync", e);
            auditLogService.logFailure(tenantId, userId, "UPDATE_REQUEST", "REQUEST",
                    String.valueOf(id), e.getMessage(), "Failed to update request from sync");
            return false;
        }
    }

    /**
     * Delete request from sync request
     */
    @Override
    public boolean deleteFromSync(Long id, String userId, String deviceId) {
        String tenantId = TenantContext.getCurrentTenant();

        try {
            Request existingRequest = requestRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Request not found"));

            String oldValueJson = objectMapper.writeValueAsString(existingRequest);
            String requestId = String.valueOf(id);

            // Delete the request
            requestRepository.deleteById(id);

            // Record in SyncChange
            syncChangeRepository.save(SyncChange.builder()
                    .id("sync_" + UUID.randomUUID().toString())
                    .tenantId(tenantId)
                    .entityId(requestId)
                    .entityType("REQUEST")
                    .action("delete")
                    .changedBy(userId)
                    .changedFromDevice(deviceId)
                    .oldValue(oldValueJson)
                    .timestamp(LocalDateTime.now())
                    .build());

            auditLogService.logOperation(tenantId, userId, "DELETE_REQUEST", "REQUEST",
                    requestId, null, "Request deleted from sync");

            log.info("Request deleted from sync: {} by device {}", requestId, deviceId);
            return true;

        } catch (Exception e) {
            log.error("Failed to delete request from sync", e);
            auditLogService.logFailure(tenantId, userId, "DELETE_REQUEST", "REQUEST",
                    String.valueOf(id), e.getMessage(), "Failed to delete request from sync");
            return false;
        }
    }
}
