package com.angerasilas.petroflow_backend.service;

import java.util.List;
import java.util.Optional;

import com.angerasilas.petroflow_backend.dto.RequestDto;
import com.fasterxml.jackson.databind.JsonNode;

public interface RequestService {
    RequestDto createRequest(RequestDto requestDto);
    RequestDto getRequestById(Long id);
    List<RequestDto> getAllRequests();
    RequestDto updateRequest(Long id, RequestDto requestDto);
    void deleteRequest(Long id);
    Optional<RequestDto> getByEmployee_EmployeeNo(String employeeNo);

    /**
     * Create request from sync request
     */
    boolean createFromSync(JsonNode data, String userId, String deviceId);

    /**
     * Update request from sync request
     */
    boolean updateFromSync(Long id, JsonNode data, String userId, String deviceId);

    /**
     * Delete request from sync request
     */
    boolean deleteFromSync(Long id, String userId, String deviceId);
}