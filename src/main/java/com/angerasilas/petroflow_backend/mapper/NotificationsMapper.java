package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.NotificationsDto;
import com.angerasilas.petroflow_backend.entity.Notifications;
import com.angerasilas.petroflow_backend.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationsMapper {

    private final EmployeesRepository employeesRepository;

    public NotificationsDto toDto(Notifications notifications) {
        NotificationsDto dto = new NotificationsDto();
        dto.setId(notifications.getId());
        dto.setEmployeeId(notifications.getEmployee().getId());
        dto.setNotification(notifications.getNotification());
        dto.setDateSent(notifications.getDateSent());
        dto.setStatus(notifications.getStatus());
        return dto;
    }

    public Notifications toEntity(NotificationsDto notificationsDto) {
        Notifications entity = new Notifications();
        entity.setId(notificationsDto.getId());
        entity.setNotification(notificationsDto.getNotification());
        entity.setDateSent(notificationsDto.getDateSent());
        entity.setStatus(notificationsDto.getStatus());
        entity.setEmployee(employeesRepository.findById(notificationsDto.getEmployeeId()).orElse(null));
        return entity;
    }
}