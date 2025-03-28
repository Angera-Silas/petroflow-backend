package com.angerasilas.petroflow_backend.service;

import java.util.List;
import java.util.Optional;

import com.angerasilas.petroflow_backend.dto.IncidentDto;

public interface IncidentService {
    IncidentDto createIncident(IncidentDto incidentDto);
    IncidentDto getIncidentById(Long id);
    List<IncidentDto> getAllIncidents();
    IncidentDto updateIncident(Long id, IncidentDto incidentDto);
    void deleteIncident(Long id);
    Optional<IncidentDto> getByOrganizationEmployees_EmployeeNo(String employeeNo);
}