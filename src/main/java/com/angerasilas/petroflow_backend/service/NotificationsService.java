package com.angerasilas.petroflow_backend.service;

import java.util.List;
import com.angerasilas.petroflow_backend.dto.NotificationsDto;

public interface NotificationsService {
    NotificationsDto createNotification(NotificationsDto notificationsDto);
    NotificationsDto getNotificationById(Long id);
    List<NotificationsDto> getAllNotifications();
    NotificationsDto updateNotification(Long id, NotificationsDto notificationsDto);
    void deleteNotification(Long id);
}