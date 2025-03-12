package com.angerasilas.petroflow_backend.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.angerasilas.petroflow_backend.dto.NotificationsDto;
import com.angerasilas.petroflow_backend.service.NotificationsService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/notifications")
public class NotificationsController {

    private final NotificationsService notificationsService;

    @PostMapping("/add")
    public ResponseEntity<NotificationsDto> addNotification(@RequestBody NotificationsDto notificationsDto) {
        NotificationsDto createdNotification = notificationsService.createNotification(notificationsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNotification);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<NotificationsDto> getNotificationById(@PathVariable Long id) {
        NotificationsDto notification = notificationsService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<NotificationsDto>> getAllNotifications() {
        List<NotificationsDto> notifications = notificationsService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<NotificationsDto> updateNotification(@PathVariable Long id, @RequestBody NotificationsDto notificationsDto) {
        NotificationsDto updatedNotification = notificationsService.updateNotification(id, notificationsDto);
        return ResponseEntity.ok(updatedNotification);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationsService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}