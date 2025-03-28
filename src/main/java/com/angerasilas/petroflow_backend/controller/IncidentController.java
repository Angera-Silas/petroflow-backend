package com.angerasilas.petroflow_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.angerasilas.petroflow_backend.dto.IncidentDto;
import com.angerasilas.petroflow_backend.service.IncidentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    @PostMapping("/add")
    public ResponseEntity<IncidentDto> addIncident(@RequestBody IncidentDto incidentDto) {
        IncidentDto createdIncident = incidentService.createIncident(incidentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIncident);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<IncidentDto> getIncidentById(@PathVariable Long id) {
        IncidentDto incident = incidentService.getIncidentById(id);
        return ResponseEntity.ok(incident);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<IncidentDto>> getAllIncidents() {
        List<IncidentDto> incidents = incidentService.getAllIncidents();
        return ResponseEntity.ok(incidents);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<IncidentDto> updateIncident(@PathVariable Long id, @RequestBody IncidentDto incidentDto) {
        IncidentDto updatedIncident = incidentService.updateIncident(id, incidentDto);
        return ResponseEntity.ok(updatedIncident);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        incidentService.deleteIncident(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/employee/{employeeNo}")
    public ResponseEntity<IncidentDto> getIncidentByEmployeeNo(@PathVariable String employeeNo) {
        IncidentDto incident = incidentService.getByOrganizationEmployees_EmployeeNo(employeeNo).orElse(null);
        return ResponseEntity.ok(incident);
    }
}