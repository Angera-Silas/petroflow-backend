package com.angerasilas.petroflow_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.angerasilas.petroflow_backend.dto.RequestDto;
import com.angerasilas.petroflow_backend.service.RequestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    @PostMapping("/add")
    public ResponseEntity<RequestDto> addRequest(@RequestBody RequestDto requestDto) {
        RequestDto createdRequest = requestService.createRequest(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable Long id) {
        RequestDto request = requestService.getRequestById(id);
        return ResponseEntity.ok(request);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<RequestDto>> getAllRequests() {
        List<RequestDto> requests = requestService.getAllRequests();
        return ResponseEntity.ok(requests);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RequestDto> updateRequest(@PathVariable Long id, @RequestBody RequestDto requestDto) {
        RequestDto updatedRequest = requestService.updateRequest(id, requestDto);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }
}