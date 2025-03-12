package com.angerasilas.petroflow_backend.mapper;

import org.springframework.stereotype.Component;
import com.angerasilas.petroflow_backend.dto.MessagesDto;
import com.angerasilas.petroflow_backend.entity.Messages;
import com.angerasilas.petroflow_backend.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MessagesMapper {

    private final EmployeesRepository employeesRepository;

    public MessagesDto toDto(Messages messages) {
        MessagesDto dto = new MessagesDto();
        dto.setId(messages.getId());
        dto.setSenderId(messages.getSender().getId());
        dto.setReceiverId(messages.getReceiver().getId());
        dto.setMessage(messages.getMessage());
        dto.setDateSent(messages.getDateSent());
        dto.setStatus(messages.getStatus());
        return dto;
    }

    public Messages toEntity(MessagesDto messagesDto) {
        Messages entity = new Messages();
        entity.setId(messagesDto.getId());
        entity.setMessage(messagesDto.getMessage());
        entity.setDateSent(messagesDto.getDateSent());
        entity.setStatus(messagesDto.getStatus());
        entity.setSender(employeesRepository.findById(messagesDto.getSenderId()).orElse(null));
        entity.setReceiver(employeesRepository.findById(messagesDto.getReceiverId()).orElse(null));
        return entity;
    }
}