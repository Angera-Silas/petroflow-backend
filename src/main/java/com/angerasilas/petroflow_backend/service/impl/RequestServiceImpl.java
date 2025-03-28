package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.RequestDto;
import com.angerasilas.petroflow_backend.entity.Request;
import com.angerasilas.petroflow_backend.mapper.RequestMapper;
import com.angerasilas.petroflow_backend.repository.RequestRepository;
import com.angerasilas.petroflow_backend.service.RequestService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;

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
}
