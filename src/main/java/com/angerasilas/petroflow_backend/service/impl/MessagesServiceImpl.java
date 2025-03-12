package com.angerasilas.petroflow_backend.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.angerasilas.petroflow_backend.dto.MessagesDto;
import com.angerasilas.petroflow_backend.entity.Messages;
import com.angerasilas.petroflow_backend.mapper.MessagesMapper;
import com.angerasilas.petroflow_backend.repository.MessagesRepository;
import com.angerasilas.petroflow_backend.service.MessagesService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MessagesServiceImpl implements MessagesService {

    private final MessagesRepository messagesRepository;
    private final MessagesMapper messagesMapper;

    @Override
    public MessagesDto createMessage(MessagesDto messagesDto) {
        Messages messages = messagesMapper.toEntity(messagesDto);
        messages = messagesRepository.save(messages);
        return messagesMapper.toDto(messages);
    }

    @Override
    public MessagesDto getMessageById(Long id) {
        Messages messages = messagesRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        return messagesMapper.toDto(messages);
    }

    @Override
    public List<MessagesDto> getAllMessages() {
        return messagesRepository.findAll().stream().map(messagesMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public MessagesDto updateMessage(Long id, MessagesDto messagesDto) {
        Messages messages = messagesRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        messages.setMessage(messagesDto.getMessage());
        messages.setStatus(messagesDto.getStatus());
        messages = messagesRepository.save(messages);
        return messagesMapper.toDto(messages);
    }

    @Override
    public void deleteMessage(Long id) {
        messagesRepository.deleteById(id);
    }
}
