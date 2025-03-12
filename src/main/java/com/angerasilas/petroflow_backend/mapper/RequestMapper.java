package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.RequestDto;
import com.angerasilas.petroflow_backend.entity.Request;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final OrganizationEmployeesRepository organizationRepository;

    public RequestDto toDto(Request request) {
        RequestDto dto = new RequestDto();
        dto.setId(request.getId());
        dto.setEmployeeNo(request.getEmployee().getEmployeeNo());
        dto.setTitle(request.getTitle());
        dto.setDescription(request.getDescription());
        dto.setDateRequested(request.getDateRequested());
        dto.setStatus(request.getStatus());
        dto.setDateResolved(request.getDateResolved());
        return dto;
    }

    public Request toEntity(RequestDto requestDto) {
        Request entity = new Request();
        entity.setId(requestDto.getId());
        entity.setTitle(requestDto.getTitle());
        entity.setDescription(requestDto.getDescription());
        entity.setDateRequested(requestDto.getDateRequested());
        entity.setStatus(requestDto.getStatus());
        entity.setDateResolved(requestDto.getDateResolved());
        entity.setEmployee(organizationRepository.findByEmployeeNo(requestDto.getEmployeeNo()).orElse(null));
        return entity;
    }
}