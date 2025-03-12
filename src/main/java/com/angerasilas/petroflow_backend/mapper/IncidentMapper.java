package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.IncidentDto;
import com.angerasilas.petroflow_backend.entity.Incident;
import com.angerasilas.petroflow_backend.repository.OrganizationEmployeesRepository;


@Component
public class IncidentMapper {

   private OrganizationEmployeesRepository organizationEmployeesRepository;

    public IncidentDto toDto(Incident incident) {
        // Mapping logic here
        IncidentDto dto = new IncidentDto();
        dto.setId(incident.getId());
        dto.setEmployeeNo(incident.getOrganizationEmployees().getEmployeeNo());
        dto.setTitle(incident.getTitle());
        dto.setReceiver(incident.getReceiver());
        dto.setDescription(incident.getDescription());
        dto.setDateReported(incident.getDateReported());
        dto.setStatus(incident.getStatus());
        dto.setDateResolved(incident.getDateResolved());
        return dto;
    }

    public Incident toEntity(IncidentDto incidentDto) {
        // Mapping logic here
        Incident entity = new Incident();
        entity.setId(incidentDto.getId());
        entity.setOrganizationEmployees(organizationEmployeesRepository.findByEmployeeNo(incidentDto.getEmployeeNo()).orElse(null));
        entity.setTitle(incidentDto.getTitle());
        entity.setReceiver(incidentDto.getReceiver());
        entity.setDescription(incidentDto.getDescription());
        entity.setDateReported(incidentDto.getDateReported());
        entity.setStatus(incidentDto.getStatus());
        entity.setDateResolved(incidentDto.getDateResolved());
        return entity;
    }
}