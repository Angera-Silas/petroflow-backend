package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.RequestDto;

public interface RequestService {
    RequestDto createRequest(RequestDto requestDto);
    RequestDto getRequestById(Long id);
    List<RequestDto> getAllRequests();
    RequestDto updateRequest(Long id, RequestDto requestDto);
    void deleteRequest(Long id);
}