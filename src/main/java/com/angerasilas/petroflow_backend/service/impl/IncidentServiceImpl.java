package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.IncidentDto;
import com.angerasilas.petroflow_backend.entity.Incident;
import com.angerasilas.petroflow_backend.mapper.IncidentMapper;
import com.angerasilas.petroflow_backend.repository.IncidentRepository;
import com.angerasilas.petroflow_backend.service.IncidentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class IncidentServiceImpl implements IncidentService {

    private final IncidentRepository incidentRepository;
    private final IncidentMapper incidentMapper;

    @Override
    public IncidentDto createIncident(IncidentDto incidentDto) {
        Incident incident = incidentMapper.toEntity(incidentDto);
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    @Override
    public IncidentDto getIncidentById(Long id) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
        return incidentMapper.toDto(incident);
    }

    @Override
    public List<IncidentDto> getAllIncidents() {
        return incidentRepository.findAll().stream().map(incidentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public IncidentDto updateIncident(Long id, IncidentDto incidentDto) {
        Incident incident = incidentRepository.findById(id).orElseThrow(() -> new RuntimeException("Incident not found"));
        incident.setTitle(incidentDto.getTitle());
        incident.setReceiver(incidentDto.getReceiver());
        incident.setDescription(incidentDto.getDescription());
        incident.setDateReported(incidentDto.getDateReported());
        incident.setStatus(incidentDto.getStatus());
        incident.setDateResolved(incidentDto.getDateResolved());
        incident = incidentRepository.save(incident);
        return incidentMapper.toDto(incident);
    }

    @Override
    public void deleteIncident(Long id) {
        incidentRepository.deleteById(id);
    }

    @Override
    public Optional<IncidentDto> getByOrganizationEmployees_EmployeeNo(String employeeNo) {
        return incidentRepository.findByOrganizationEmployees_EmployeeNo(employeeNo).map(incidentMapper::toDto);
    }
}