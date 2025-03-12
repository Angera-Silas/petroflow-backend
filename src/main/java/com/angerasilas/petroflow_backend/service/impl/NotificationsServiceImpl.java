package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.NotificationsDto;
import com.angerasilas.petroflow_backend.entity.Notifications;
import com.angerasilas.petroflow_backend.mapper.NotificationsMapper;
import com.angerasilas.petroflow_backend.repository.NotificationsRepository;
import com.angerasilas.petroflow_backend.service.NotificationsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationsServiceImpl implements NotificationsService {

    private final NotificationsRepository notificationsRepository;
    private final NotificationsMapper notificationsMapper;

    @Override
    public NotificationsDto createNotification(NotificationsDto notificationsDto) {
        Notifications notifications = notificationsMapper.toEntity(notificationsDto);
        notifications = notificationsRepository.save(notifications);
        return notificationsMapper.toDto(notifications);
    }

    @Override
    public NotificationsDto getNotificationById(Long id) {
        Notifications notifications = notificationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        return notificationsMapper.toDto(notifications);
    }

    @Override
    public List<NotificationsDto> getAllNotifications() {
        return notificationsRepository.findAll().stream().map(notificationsMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public NotificationsDto updateNotification(Long id, NotificationsDto notificationsDto) {
        Notifications notifications = notificationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Notification not found"));
        notifications.setNotification(notificationsDto.getNotification());
        notifications.setStatus(notificationsDto.getStatus());
        notifications = notificationsRepository.save(notifications);
        return notificationsMapper.toDto(notifications);
    }

    @Override
    public void deleteNotification(Long id) {
        notificationsRepository.deleteById(id);
    }
}
